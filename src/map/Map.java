/**
 * Author: Nyuutrino
 * Date: Apr 17, 2025
 * Description: Contains the map data - i.e: the list of branches & the list of barriers (mainly corridors + dead-end & door).
 */
package map;

import barrier.Barrier;

import java.awt.*;
import java.util.ArrayList;

public class Map {
	private final int numVertPoints;
	private final int numHorizPoints;
	//Used to store the branches in the graph.
	ArrayList<Branch> branches = new ArrayList<>();
	//Used to store the barriers for the map path
	Barrier[] barriers;
	//Stores the overlap of branches
	private int[][] overlapMap;
	//Stores the intersections. 0 for no intersections, 3 for a three-way & 4 for a four-way
	private int[][] intersectionMap;

	/**
	 * Initializes a map instance
	 * @param numVertPoints - The number of points vertically. Each point can be connected to another to form a path
	 * @param numHorizPoints - The number of points horizontally
	 */
	public Map(int numVertPoints, int numHorizPoints) {
		this.numVertPoints = numVertPoints;
		this.numHorizPoints = numHorizPoints;
		this.overlapMap = new int[numVertPoints][numHorizPoints];
		this.intersectionMap = new int[numVertPoints][numHorizPoints];
	}

	/**
	 * Clears the map of its branches
	 */
	public void clear() {
		branches.clear();
	}

	/**
	 * Pushes a branch to the map
	 * @param branch the branch to push to the map
	 */
	public void pushBranch(Branch branch) {
		//Update the intersections
		for (int i = 1; i < branch.getNumPoints() - 1; ++i) {
			Point pt = branch.getPoint(i);
			Point ptBefore = branch.getPoint(i - 1);
			Point ptAfter = branch.getPoint(i + 1);
			for (Branch b : branches) {
				Point ptComp = b.getPoint(i);
				//Pt equals the other. This could be overlap, or it could be an intersection
				if (pt.equals(ptComp)) {
					Point ptCompBefore = b.getPoint(i - 1);
					Point ptCompAfter = b.getPoint(i + 1);
					//We know it's a four-way if none of the before/after points match
					if (!ptBefore.equals(ptCompBefore) && !ptAfter.equals(ptCompAfter)) {
						intersectionMap[pt.y][pt.x] = 4;
					}
					//It's a three-way if one of them matches (but not both).
					//Also make sure that this wasn't previously a 4-way (because that means another branch uses the 4-way even if this one doesn't)
					else if(ptBefore.equals(ptCompBefore) ^ ptAfter.equals(ptCompAfter) && intersectionMap[pt.y][pt.x] != 4) {
						intersectionMap[pt.y][pt.x] = 3;
					}
				}
			}
		}
		branches.add(branch);

		//Update the intersection map for the beginning/end
		//It's actually quite simple - if all the branches take the same path, then it will be a 0. Otherwise, it will be a 3 (Three-way)
		int startAcrossDirCount = 0;
		int startDownDirCount = 0;
		int endAcrossDirCount = 0;
		int endDownDirCount = 0;
		for (Branch b : branches) {
			Point afterStartPt = b.getPoint(1);
			Point beforeEndPt = b.getPoint(b.getNumPoints() - 2);
			if (afterStartPt.x == 1)
				startAcrossDirCount++;
			else
				startDownDirCount++;
			if (beforeEndPt.x == numHorizPoints - 2)
				endAcrossDirCount++;
			else
				endDownDirCount++;
		}
		if (startAcrossDirCount != branches.size() && startDownDirCount != branches.size()) {
			intersectionMap[0][0] = 3;
		}
		if (endAcrossDirCount != branches.size() && endDownDirCount != branches.size()) {
			intersectionMap[numVertPoints - 1][numHorizPoints - 1] = 3;
		}

		//Update the overlap map
		for (int i = 0; i < branch.getNumPoints(); ++i) {
			Point pt = branch.getPoint(i);
			overlapMap[pt.y][pt.x]++;
		}
	}

	/**
	 * Returns a branch from the map
	 * @param index The index of the branch to get
	 * @return the branch at the specified index
	 */
	public Branch getBranch(int index) {
		return branches.get(index);
	}

	/**
	 * Prints the map
	 */
	public void print(){
		int[][] map = new int[numVertPoints][numHorizPoints];
		for (int i = 0; i < branches.size(); i++) {
			Branch b = branches.get(i);
			for (int j = 0; j < b.getNumPoints(); ++j) {
				Point p = b.getPoint(j);
				map[p.y][p.x] = i + 1;
			}
		}

		for (int[] row : map) {
			for (int point : row) {
				System.out.print((point > 0 ? point : " ") + " ");
			}
			System.out.println();
		}
	}

	/**
	 * Prints the overlap map
	 */
	public void printOverlap(){
		for (int[] row : this.overlapMap) {
			for (int point : row) {
				System.out.print((point > 0 ? point : " ") + " ");
			}
			System.out.println();
		}
	}

	/**
	 * Prints the intersection map
	 */
	public void printIntersection(){
		for (int[] row : this.intersectionMap) {
			for (int point : row) {
				System.out.print((point > 0 ? point : " ") + " ");
			}
			System.out.println();
		}
	}

	/**
	 * Returns the intersection status of a point on the map.
	 * @param p A point in the map
	 * @return 0 for no intersection, 3 for a three-way & 4 for a four-way
	 */
	public int getIntersection(Point p) {
		if (p.x < 0 || p.x >= numHorizPoints)
			throw new IllegalArgumentException("x coordinate out of bounds!");
		else if (p.y < 0 || p.y >= numVertPoints)
			throw new IllegalArgumentException("y coordinate out of bounds!");
		return intersectionMap[p.y][p.x];
	}
}
