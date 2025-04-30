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
	//End-caps (dead-end & door) for the corridors
	private Barrier[] caps = new Barrier[2];
	private ArrayList<Corridor> corridors = new ArrayList<>();
	//Corresponding points marking the corridors
	private ArrayList<Point> corridorPts = new ArrayList<>();

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
		this.tileEnd = new Point(tileXStart + 2 * (horizCount - 1), tileYStart + 2 * (vertCount - 1));
		this.vertCount = vertCount;
		this.horizCount = horizCount;
		this.map = new Map(vertCount, horizCount);
		//For now, the start will always be at the top right and the end will always be at the bottom left
		startPoint = new Point(tileStart.x, tileStart.y);
		endPoint = new Point(tileEnd.x, tileEnd.y);
		//Calculate taxicab distance
		taxicabDistance = vertCount + horizCount;

		if (!GameConfig.debug) return;
		//Debug stuff
		System.out.printf("Taxicab Distance: %d\n", taxicabDistance);
		System.out.printf("Vert count: %d, Horiz count: %d\n", vertCount, horizCount);
	}

	/**
	 * Generates the map
	 * @param seed - The seed for the map
	 */
	public void genMap(long seed) {
		//Clear map
		map.clear();
		//Random number is seed-based so procedural generation is deterministic
		Random rand = new Random(seed);

		//Determines if we will change direction - the higher, the better
		double dirChangeBias = 0.92;
		//Number of branches we'll generate. For now, it will be set to a hard-coded number
		//TODO: Make this number dynamic?
		int numBranches = 8;
		Branch[] branches = new Branch[numBranches];
		//Each branch will span the length of the map
		for (int i = 0; i < numBranches; i++) {
			branches[i] = new Branch(new Point(0, 0), new Point(horizCount, vertCount));
			Point pt = new Point(0, 0);
			Point branchEnd = new Point(horizCount, vertCount);

			//Choose a random direction
			int dir = rand.nextInt(2);

			//Make the path for the branch
			for (int j = 0; j < taxicabDistance - 1; j++) {
				branches[i].addToBranch(new Point(pt));
				double r = rand.nextDouble();
				if (dir == 0 && pt.x + 1 >= branchEnd.x){
					//Change direction to down by force (otherwise we'll go out of bounds)
					dir = 1;
				}
				else if(dir == 1 && pt.y + 1 >= branchEnd.y){
					//Change direction to across by force (otherwise we'll go out of bounds)
					dir = 0;
				}
				else if (r > dirChangeBias) {
					//Change direction by random chance...

					//But first make sure we won't go out of bounds if we do
					if ((dir == 0 && pt.y + 1 < branchEnd.y) || (dir == 1 && pt.x + 1 < branchEnd.x))
						//OK to change direction
						dir = dir ^ 1;
				}

				pt.translate(dir ^ 1, dir);
			}
			map.pushBranch(branches[i]);
		}

		if (GameConfig.debug) {
			System.out.println("Map:");
			map.print();
			System.out.println("\nOverlap:");
			map.printOverlap();
			System.out.println("\nIntersection:");
			map.printIntersection();
		}

		//Make the corridors

		//Make the start & end points
		Corridor start;
		Corridor end;
		if (map.getIntersection(new Point(0, 0)) == 3)
			start = new ThreeWay(this.tileStart.x, this.tileStart.y, new Direction(Direction.EAST), ThreeWay.BRANCH_SR);
		else {
			//All the branches should be starting from 1 direction. Just check the first
			Branch b = branches[0];
			Direction dir;
			if (b.getPoint(1).x == 1) {
				//Direction is east
				dir = new Direction(Direction.EAST);
			}
			else {
				//South
				dir = new Direction(Direction.SOUTH);
			}
			start = new Hallway(this.tileStart.x, this.tileStart.y, 2, dir);
		}
		if (map.getIntersection(new Point(horizCount - 1, vertCount - 1)) == 3) {
			end = new ThreeWay(this.tileEnd.x, this.tileEnd.y, new Direction(Direction.EAST), ThreeWay.BRANCH_SL);
		}
		else {
			//All the branches should be starting from 1 direction. Just check the first
			Branch b = branches[0];
			Direction dir;
			if (b.getPoint(b.getNumPoints() - 1).x == this.horizCount - 1) {
				//Direction is east
				dir = new Direction(Direction.EAST);
			}
			else {
				//South
				dir = new Direction(Direction.SOUTH);
			}
			end = new Hallway(this.tileEnd.x, this.tileEnd.y, 2, dir);
		}

		//Add start to the corridors list.
		corridors.add(start);
		corridorPts.add(new Point(0, 0));
		//Add dead-end to both start & end corridors
		//TODO replace dead-end with door for the end corridor
		caps[0] = new DeadEnd(start, start.getAvailableNodes()[0]);
		caps[1] = new DeadEnd(end, end.getAvailableNodes()[1]);

		//Add the rest of the corridors
		for (int i = 0; i < numBranches; ++i) {
			Branch b = branches[i];
			bLoop: for (int j = 1; j < b.getNumPoints() - 1; ++j) {
				Point pt = b.getPoint(j);
				//Check if this point exists in the corridors list. Skip if it does
				Point loc = new Point(tileStart.x + pt.x, tileStart.y + pt.y);
				for (int k = 0; k < corridors.size(); k++) {
					Corridor c = corridors.get(k);
					Point cPt = corridorPts.get(k);
					if (cPt.equals(pt)) {
						continue bLoop;
					}

				}

				//Make the corridor & attach to any corridors
				Corridor corridor;
				Point ptBefore = b.getPoint(j - 1);
				Point ptAfter = b.getPoint(j + 1);
				//If y changes, then we're going down. Otherwise, x is changing and we're going across
				int ptDir = pt.y == ptBefore.y ? 0 : 1;
				Corridor prevCor = null;
				Node attachmentNode = null;
				for (int k = 0; k < corridorPts.size(); k++) {
					Point attachmentPt = corridorPts.get(k);
					if (attachmentPt.equals(ptBefore)) {
						prevCor = corridors.get(k);
						for (Node n : prevCor.getAvailableNodes()) {
							if (ptDir == 0 && n.getDirectionInt() == Direction.EAST || ptDir == 1 && n.getDirectionInt() == Direction.SOUTH) {
								attachmentNode = n;
							}
						}
					}
				}

				//Check if it's an intersection
				int intersect = map.getIntersection(pt);
				if (intersect == 3) {
					//Three-way
					//Find out which way it's branching to determine branch direction
					int branchDir;
					if (ptDir == 0) {
						//It's heading across
						boolean branchStraight = false;
						boolean branchRight = false;
						//The x changes, which means we go straight. Otherwise, we know we will go right
						if (ptAfter.x > pt.x)
							branchStraight = true;
						else
							branchRight = true;
						//Find the first branch that diverges from the current branch. We can then find out which two directions the three-way branches into.
						for (Branch comparingBranch : branches) {
							Point cBPt = comparingBranch.getPoint(j);
							Point cBPtAfter = comparingBranch.getPoint(j + 1);
							if (cBPt.equals(pt) && !cBPtAfter.equals(ptAfter)) {
								if (cBPtAfter.y == pt.y)
									//Heading across, so we branch straight
									branchStraight = true;
								else
									//Only other option is to go down (branch right)
									branchRight = true;
								//We can break since we know all other branches will take one of the two paths
								break;
							}
						}
						//If no branches diverge, then it must be a branch that joins this branch, which means the three-way will branch to the left
						if (branchStraight && branchRight)
							branchDir = ThreeWay.BRANCH_SR;
						else if (branchStraight)
							branchDir = ThreeWay.BRANCH_SL;
						else
							branchDir = ThreeWay.BRANCH_LR;
					}
					else {
						//It's heading down
						boolean branchLeft = false;
						boolean branchStraight = false;
						//The y changes, which means that we go straight. Otherwise, we know we will go left
						if (ptAfter.y > pt.y)
							branchStraight = true;
						else
							branchLeft = true;
						//Find the first branch that diverges from the current branch. We can then find out which two directions the three-way branches into.
						for (Branch comparingBranch : branches) {
							Point cBPt = comparingBranch.getPoint(j);
							Point cBPtAfter = comparingBranch.getPoint(j + 1);
							if (cBPt.equals(pt) && !cBPtAfter.equals(ptAfter)) {
								if (cBPtAfter.x == pt.x)
									//Heading down, so we branch straight
									branchStraight = true;
								else
									//Only other option is to go across (branch left)
									branchLeft = true;
								//We can break since we know all other branches will take one of the two paths
								break;
							}
						}
						//If no branches diverge, then it must be a branch that joins this branch, which means the three-way will branch to the right
						if (branchStraight && branchLeft)
							branchDir = ThreeWay.BRANCH_SL;
						else if (branchStraight)
							branchDir = ThreeWay.BRANCH_SR;
						else
							branchDir = ThreeWay.BRANCH_LR;
					}
					corridor = new ThreeWay(prevCor, attachmentNode, branchDir);
				}
				else if (intersect == 4) {
					//Four-way
					corridor = new FourWay(prevCor, attachmentNode);
				}
				else {
					//We know it's either a hallway or a corner
					//Compare whether the prev point equals the following point on one of the axis. If they don't, then we know it's a bend
					boolean straight = ptBefore.x == ptAfter.x || ptBefore.y == ptAfter.y;
					if (straight) {
						//It's a hallway
						corridor = new Hallway(prevCor, attachmentNode, 2);
					}
					else {
						//If it's down, we'll be bending to the left. Otherwise, it'll be to the right
						int bendDir = ptDir == 1 ? Corner.LEFT : Corner.RIGHT;
						//It's a corner
						corridor = new Corner(prevCor, attachmentNode, bendDir);
					}
				}
				//Add the corridor
				corridors.add(corridor);
				corridorPts.add(pt);
			}
		}

		//Add the end corridor
		corridors.add(end);
		corridorPts.add(new Point(horizCount - 1, vertCount - 1));
	}

	/**
	 * Gets the barriers (corridors + end-caps)
	 * @return the corridors + barriers that make up the path (as an array)
	 */
	public Barrier[] getBarriers(){
		Barrier[] barriers = new Barrier[corridors.size() + 2];
		for (int i = 1; i < corridors.size() + 1; ++i)
			barriers[i] = corridors.get(i - 1);
		barriers[0] = caps[0];
		barriers[barriers.length - 1] = caps[1];
		return barriers;
	}

}
