package amonetta.maze;

import java.util.List;

/**
 * Defines the rules of movement to solve a maze.
 * @param <T>
 */
public interface PathFinder<T extends MazeNode> {

	/**
	 * Kind of mazed that this path finder applies.
	 * @param maze
	 * @return
	 */
	boolean appliesTo(Maze maze);

	/**
	 * @param mazeNode
	 * @return a list of possible next movements from the given node.
	 */
	List<MazePath<T>> getPathsFrom(T mazeNode);

	/**
	 * @param maze
	 * @return The first path defined by entrance node.
	 */
	MazePath<T> getEntrancePath(Maze<T> maze);
}
