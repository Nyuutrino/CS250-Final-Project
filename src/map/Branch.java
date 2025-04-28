/**
 * Author: Nyuutrino
 * Date: Apr 25, 2025
 * Description: Defines a branch in the map.
 */
package map;

import java.awt.*;
import java.util.ArrayList;

public class Branch {
	//The beginning & end (relative to the map class) of the branch
	private Point branchStart;
	private Point branchEnd;
	private int numPoints;
	//Stores the path of the branch
	private ArrayList<Point> branchPoints = new ArrayList<>();
	public Branch(Point branchStart, Point branchEnd) {
		this.branchStart = branchStart;
		this.branchEnd = branchEnd;
		//NOTE: We assume the branchEnd coordinates will be greater than the branchStart ones due to the nature of the map generation algorithm
		numPoints = (branchEnd.x - branchStart.x) + (branchEnd.y - branchStart.y);
	}

	/**
	 * Adds point to branch
	 * @param point the new point
	 */
	public void addToBranch(Point point){
		if (point.x < branchStart.x || point.x > branchEnd.x)
			throw new IllegalArgumentException("Point out of bounds on the x-axis!");
		if (point.y < branchStart.y || point.y > branchEnd.y)
			throw new IllegalArgumentException("Point out of bounds on the y-axis!");
		branchPoints.add(point);
	}

	/**
	 * Returns the number of points in the branch
	 * @return The number of points of the branch
	 */
	int getNumPoints() {
		return numPoints;
	}

	/**
	 * Returns the point at the specified index in the arraylist
	 * @param index The index of the point
	 * @return a point in the branch from the branch's arraylist
	 */
	Point getPoint(int index) {
		return branchPoints.get(index);
	}
}
