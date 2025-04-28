/**
 * Author: Nyuutrino
 * Date: Apr 17, 2025
 * Description: Contains the map data - i.e: the list of path coordinates & the list of barriers.
 */
package map;

import barrier.Barrier;

public class Map {
	private final int numVertPoints;
	private final int numHorizPoints;
	//Used to store the vertices of the map graph. Each vertex has either a 1 or a 0, indicating whether or not another vertex is connected to it, respectively.
	int[][] vertices;
	//Used to store the barriers for the map path
	Barrier[] barriers;
	/**
	 * Initializes a map instance
	 * @param numVertPoints - The number of points vertically. Each point can be connected to another to form a path
	 * @param numHorizPoints - The number of points horizontally
	 */
	public Map(int numVertPoints, int numHorizPoints) {
		this.numVertPoints = numVertPoints;
		this.numHorizPoints = numHorizPoints;
		//Initialize vertices
		vertices = new int[numVertPoints][numHorizPoints];
		for (int i = 0; i < numVertPoints; i++) {
			for (int j = 0; j < numHorizPoints; j++) {
				vertices[i][j] = 0;
			}
		}
	}
	/**
	 * Set a vertex's state
	 * @param x - The vertex's x-axis location (0 - numHorizPoints)
	 * @param y - The vertex's y-axis location (0 - numVertPoints)
	 * @param state - true for connected, false for disconnected
	 */
	public void setVertexState(int x, int y, boolean state) {
		//Make sure coordinates are in bounds
		if (x < 0 || x >= numHorizPoints || y < 0 || y >= numVertPoints) {
			throw new IllegalArgumentException("Coordinates out of bounds!");
		}
		vertices[y][x] = state ? 1 : 0;
	}

	/**
	 * Gets a vertex's state
	 * @param x - The vertex's x-axis location (0 - numHorizPoints)
	 * @param y - The vertex's y-axis location (0 - numVertPoints)
	 * @return true for connected, false for disconnected
	 */
	public boolean getVertexState(int x, int y) {
		//Make sure coordinates are in bounds
		if (x < 0 || x >= numHorizPoints || y < 0 || y >= numVertPoints) {
			throw new IllegalArgumentException("Coordinates out of bounds!");
		}
		return vertices[y][x] == 1;
	}

	/**
	 * Gets a vertex's state, without throwing an error for out of bounds coordinate.
	 * @param x - The vertex's x-axis location (0 - numHorizPoints)
	 * @param y - The vertex's y-axis location (0 - numVertPoints)
	 * @return true for connected, false for disconnected or out of bounds.
	 */
	public boolean getVertexStateNoError(int x, int y) {
		//Make sure coordinates are in bounds
		if (x < 0 || x >= numHorizPoints || y < 0 || y >= numVertPoints) {
			return false;
		}
		return vertices[y][x] == 1;
	}

	/**
	 * Clears any paths that were previously written.
	 */
	public void clearPaths(){
		for (int i = 0; i < numVertPoints; i++) {
			for (int j = 0; j < numHorizPoints; j++) {
				vertices[i][j] = 0;
			}
		}
	}

	/**
	 * Prints the map
	 */
	public void print(){
		for (int i = 0; i < numVertPoints; i++) {
			for (int j = 0; j < numHorizPoints; j++) {
				System.out.printf("%d%s", vertices[i][j], j < numHorizPoints - 1 ? ", " : "");
			}
			System.out.println();
		}
	}
}
