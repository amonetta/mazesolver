package amonetta.maze;

/**
 * A related collection of maze nodes to try to find a path between to nodes.
 * @param <T> kind of nodes
 */
public interface Maze<T extends MazeNode> {

	/**
	 * @return The initial node to solve the maze.
	 */
	T getEntranceNode();

	/**
	 * @return The target final node of the maze. Be carefully! there is no grantee that exists a path from entrance to the exit.
	 */
	T getOutputNode();
}
