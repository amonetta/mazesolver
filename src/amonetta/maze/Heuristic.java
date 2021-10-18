package amonetta.maze;

import java.util.List;

/**
 * Contains the logic to choose the "best" way to find the output.
 * @param <T>
 */
public interface Heuristic<T extends MazeNode> {

	boolean appliesTo(Maze<T> maze);

	/**
	 * Sort options according strategy.
	 * @param maze
	 * @param paths
	 */
	void sortPaths(Maze<T> maze, List<MazePath<T>> paths);
}
