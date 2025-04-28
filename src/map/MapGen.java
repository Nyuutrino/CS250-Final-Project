/**
 * Author: Nyuutrino
 * Date: Apr 12, 2025
 * Description: Generates the layout of the level procedurally.
 */
package map;

import barrier.*;
import barrierNodes.Node;
import direction.Direction;
import game.GameConfig;

import java.awt.*;
import java.util.*;

/**
 * Refer to MapGen.md for details on how map generation is going to work
 */

public class MapGen {
	private final int vertCount, horizCount;
	private final Point tileStart, tileEnd, startPoint, endPoint;
	private Map map;
	private int taxicabDistance;
	private ArrayList<Barrier> barriers = new ArrayList<>();
	//Corresponding barrier points
	private ArrayList<Point> barrierPoints = new ArrayList<>();

	/**
	 * Initializes the map generation class
	 * @param tileXStart The tile x position to start at
	 * @param tileYStart The tile y position to start at
	 * @param vertCount The number of vertical grid points spanning 2 tiles each (e.g: a vertCount of 8 spans 2 * 8 = 16 tiles)
	 * @param horizCount The number of horizontal grid points 2 tiles each (e.g: a vertCount of 10 spans 2 * 10 = 20 tiles)
	 */
	public MapGen(int tileXStart, int tileYStart, int vertCount, int horizCount) {
		//NOTE: Each box will be 2x2 tiles (corner, hallway, etc.). This is to keep consistent with the map. We need to account for that
		this.tileStart = new Point(tileXStart, tileYStart);
		this.tileEnd = new Point(tileXStart + (2 * horizCount), tileYStart + (2 * vertCount));
		this.vertCount = vertCount;
		this.horizCount = horizCount;
		this.map = new Map(vertCount, horizCount);
		//For now, the start will always be at the top right and the end will always be at the bottom left
		startPoint = new Point(tileStart.x, tileStart.y);
		endPoint = new Point(tileEnd.x, tileEnd.y);
		//Calculate taxicab distance
		taxicabDistance = vertCount + horizCount;
		System.out.printf("Taxicab Distance: %d\n", taxicabDistance);
		System.out.printf("Vert count: %d, Horiz count: %d\n", vertCount, horizCount);
	}

	/**
	 * Counts the degree of a point
	 * @param p The point to count the degree of
	 * @return The degree of the point
	 */
	private int countDegree(Point p) {
		int deg = 0;
		//Up
		if (map.getVertexStateNoError(p.x, p.y - 1)) deg++;
		//Down
		if (map.getVertexStateNoError(p.x, p.y + 1)) deg++;
		//Left
		if (map.getVertexStateNoError(p.x - 1, p.y)) deg++;
		//Right
		if (map.getVertexStateNoError(p.x + 1, p.y)) deg++;
		return deg;
	}

	/**
	 * Generates the map
	 * @param seed - The seed for the map
	 */
	public void genMap(long seed){
		//TODO: Maybe just generate random branches, find their intersections & make the corresponding corridors?
		//Random number to determine branches
		Random rand = new Random(seed);
		//Clear preexisting map paths
		map.clearPaths();
		//Starting from the top, loop for the taxicab distance if we want to go across or down (or only one if we're at a boundary).
		//Bias to prevent frequent direction changes. A higher bias means less changes
		double bendBias = 0.8;
		//Find number of branches we want. Right now we'll set it to be 5
		//TODO: high numbers cause issues. Fix if we have time
		int numBranches = 5;
		//We cannot attach to branches with distances shorter than this.
		int distanceThresh = 5;
		Branch[] branches = new Branch[numBranches];
		for (int branchCount = 0; branchCount < numBranches; ++branchCount) {
			Point branchStart, branchEnd;
			int dir = 0;
			//The first branch should be from the start of the map to the end
			if (branchCount == 0) {
				branchStart = new Point(0, 0);
				branchEnd = new Point(horizCount, vertCount);
			}
			else {
				//Determine start & end based on random points from a random branch
				Branch targetBranch = branches[rand.nextInt(branchCount)];
				while (targetBranch.getNumPoints() < distanceThresh)
					targetBranch = branches[rand.nextInt(branchCount)];
				int randomStartingIndex = rand.nextInt(targetBranch.getNumPoints() / 2);
				int randomEndingIndex = rand.nextInt(targetBranch.getNumPoints() / 2, targetBranch.getNumPoints() - 1);
				branchStart = targetBranch.getPoint(randomStartingIndex);
				branchEnd = targetBranch.getPoint(randomEndingIndex);

				//Check direction on the start & endpoints and go in a different direction
				dir = targetBranch.getPoint(randomStartingIndex).x == targetBranch.getPoint(randomStartingIndex + 1).x ? Direction.SOUTH : Direction.EAST;
				if (dir == Direction.SOUTH) {
					branchStart.translate(1, 0);
					dir = Direction.EAST;
				}
				else {
					branchStart.translate(0, 1);
					dir = Direction.SOUTH;
				}

			}

			branches[branchCount] = new Branch(branchStart, branchEnd);
			Branch newBranch = branches[branchCount];
			//Taxicab distance (manhattan geometry)
			int distance = (branchEnd.x - branchStart.x) + (branchEnd.y - branchStart.y);
			System.out.printf("Branch start: (%d, %d), Branch End: (%d, %d)\n", branchStart.x, branchStart.y, branchEnd.x, branchEnd.y);
			System.out.printf("Distance: %d\n", distance);
			System.out.printf("Branch count: %d\n", branchCount);
			int ptX = branchStart.x;
			int ptY = branchStart.y;

			//Generate the path for the branch
			// - 1 since we count the starting point
			for (int i = 0; i < distance - 1; ++i) {
				map.setVertexState(ptX, ptY, true);
				newBranch.addToBranch(new Point(ptX, ptY));

				double r = rand.nextDouble();
				if (dir == 0 && ptX + 1 >= branchEnd.x){
					//Change direction to down by force (otherwise we'll go out of bounds)
					dir = 1;
				}
				else if(dir == 1 && ptY + 1 >= branchEnd.y){
					//Change direction to across by force (otherwise we'll go out of bounds)
					dir = 0;
				}
				else if (r > bendBias) {
					//Change direction by random chance...

					//But first make sure we won't go out of bounds if we do
					if ((dir == 0 && ptY + 1 < branchEnd.y) || (dir == 1 && ptX + 1 < branchEnd.x))
						//OK to change direction
						dir = dir ^ 1;
				}

				ptX += dir ^ 1;
				ptY += dir;
			}
		}

		//Test: print after
		System.out.println("Result:");
		map.print();

		//Make the corridors

		//Array of linked lists with the corridors of each branch
		@SuppressWarnings("unchecked")
		LinkedList<Corridor>[] corridors = new LinkedList[numBranches];
		//Sort branches array by branch size (descending order)
		Arrays.sort(branches, new Comparator<Branch>() {
			public int compare(Branch b1, Branch b2) {
				return Integer.compare( b2.getNumPoints(), b1.getNumPoints());
			}
		});

		//Loop over each branch & generate the corridors
		for (int i = 0; i < numBranches; ++i) {
			corridors[i] = new LinkedList<>();
			Branch b = branches[i];
			for (int j = 0; j < b.getNumPoints(); ++j) {
				System.out.printf("i: %d, j: %d\n", i, j);
				if (b.getNumPoints() == taxicabDistance) {
					//Branch spans the entire map. Much easier to calculate

					//Beginning - we should make the initial hallway/corner
					if (j == 0) {
						//Determine if we will have hallway/corner or three-way (I haven't decided which yet)
						//We will have a hallway at the beginning if the degree is 1
						if (countDegree(new Point(0, 0)) == 1) {
							//Get the direction we are heading
							int dir = map.getVertexState(0, 1) ? Direction.SOUTH : Direction.NORTH;
							//Make sure no other branch has already made a hallway here
							for (int k = 0; k < numBranches; ++k) {
								if (k == i) continue;
								Branch bToCheck = branches[k];
								if (bToCheck.getNumPoints() == taxicabDistance) {
									int bToCheckDir = bToCheck.getPoint(1).x == 1 ? Direction.EAST : Direction.WEST;
//									if (bToCheckDir == dir)

								}
							}
						}
						else {

						}
						continue;
					}
					//End
					else if (j == b.getNumPoints() - 1) {
						//Check if point is the last one in the map. If it is and the degree is 1, then it will be a simple hallway/corner with the door. If the degree is >1, then we'll have a three-way
						if (b.getPoint(j - 1).x == horizCount && b.getPoint(j - 1).y == vertCount) {
							//TODO: Attach to any previous branches
							if (map.getVertexState(horizCount, vertCount - 1) && map.getVertexState(horizCount - 1, vertCount)) {
								//Degree is 2, so a three-way
								//TODO
							}
							else {
								//Degree is 1, so just a simple hallway/corner with a door

								Corridor prevCor = corridors[i].get(j - 1);
								if (map.getVertexState(horizCount - 1, vertCount)) {
									//Direction is east, so make a hallway
									Hallway hall = new Hallway(prevCor, prevCor.getAvailableNodes()[1], 2);
									barriers.add(hall);
									corridors[i].add(hall);
									//TODO: Add door
								}
								else {
									//Direction is south, so make a corner
									Corner corner = new Corner(prevCor, prevCor.getAvailableNodes()[1], Corner.LEFT);
									barriers.add(corner);
									corridors[i].add(corner);
									//TODO: add door
								}

							}
						}
						continue;
					}
				}
				else {

				}
				Point p = b.getPoint(j);
				//Check degree of point
				int deg = 0;
				//Up
				if (map.getVertexStateNoError(p.x, p.y - 1)) deg++;
				//Down
				if (map.getVertexStateNoError(p.x, p.y + 1)) deg++;
				//Left
				if (map.getVertexStateNoError(p.x - 1, p.y)) deg++;
				//Right
				if (map.getVertexStateNoError(p.x + 1, p.y)) deg++;

				if (deg == 2) {
					//Hallway or corner. We need to determine which
					boolean bent = true;
					//Up/down
					if (map.getVertexStateNoError(p.x, p.y - 1) && map.getVertexStateNoError(p.x, p.y + 1))
						bent = false;
					//Left/right
					if (map.getVertexStateNoError(p.x - 1, p.y) && map.getVertexStateNoError(p.x + 1, p.y)) bent = false;
					if (!bent) {
						//Hallway

						//Get previous corridor to attach to
						Corridor prevCor = corridors[i].get(j - 1);
						Hallway hall = new Hallway(prevCor, prevCor.getAvailableNodes()[1], 2);
						prevCor.getAvailableNodes()[1].linkNodes(hall.getAvailableNodes()[0]);
						barriers.add(hall);
						corridors[i].add(hall);
					}
					else {
						//Corner

						//0 for across, 1 for down
						int dir = 0;
						if (map.getVertexStateNoError(p.x, p.y - 1)) {
							dir = 1;
						}
						//We know from the direction whether we should bend left or right.
						//If dir is 0 (across) and bent, we'll bend right. If dir is 1 (down), we'll bend left
						int bendDir = dir == 0 ? Corner.RIGHT : Corner.LEFT;
						//Get previous corridor to attach to
						Corridor prevCor = corridors[i].get(j - 1);
						Corner corner = new Corner(prevCor, prevCor.getAvailableNodes()[1], bendDir);
						prevCor.getAvailableNodes()[1].linkNodes(corner.getAvailableNodes()[0]);
						barriers.add(corner);
						corridors[i].add(corner);
					}
				}
				else if (deg == 3) {
					//Three-way
					int branchDir = 0;
					//Find which way we branch
					Corridor prevCor = corridors[i].get(j - 1);
					int dir = prevCor.getDirection();
					if (dir == Direction.EAST) {
						if (map.getVertexStateNoError(p.x, p.y - 1) && map.getVertexStateNoError(p.x, p.y + 1)) branchDir = ThreeWay.BRANCH_LR;
						else if (map.getVertexStateNoError(p.x, p.y - 1)) branchDir = ThreeWay.BRANCH_SL;
						else if (map.getVertexStateNoError(p.x, p.y + 1)) branchDir = ThreeWay.BRANCH_SR;
					}
					else {
						if (map.getVertexStateNoError(p.x - 1, p.y) && map.getVertexStateNoError(p.x + 1, p.y)) branchDir = ThreeWay.BRANCH_LR;
						else if (map.getVertexStateNoError(p.x - 1, p.y)) branchDir = ThreeWay.BRANCH_SR;
						else if (map.getVertexStateNoError(p.x + 1, p.y)) branchDir = ThreeWay.BRANCH_SL;
					}
					ThreeWay threeWay = new ThreeWay(prevCor, prevCor.getAvailableNodes()[1], branchDir);
					prevCor.getAvailableNodes()[1].linkNodes(threeWay.getAvailableNodes()[0]);
					barriers.add(threeWay);
					corridors[i].add(threeWay);
				}
				else if (deg == 4) {
					//Four-way
					Corridor prevCor = corridors[i].get(j - 1);
					FourWay fourWay = new FourWay(prevCor, prevCor.getAvailableNodes()[1]);
					prevCor.getAvailableNodes()[1].linkNodes(fourWay.getAvailableNodes()[0]);
					barriers.add(fourWay);
					corridors[i].add(fourWay);
				}
			}
		}
	}

	/**
	 * Test function: prints the length of the barrier arraylist
	 */
	public void printBarrierLength() {
		System.out.printf("Barrier arraylist length: %d\n", barriers.size());
	}

	/**
	 * Gets the barriers
	 * @return the barriers that make up the path (as an array)
	 */
	public Barrier[] getBarriers(){
		return barriers.toArray(new Barrier[0]);
	}

}
