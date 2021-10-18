package amonetta.maze;

import java.util.List;

/**
 * A maze path defines a sequence of node that are connected and able to move in order between then.
 * @param <T>
 */
public interface MazePath<T extends MazeNode> {

	T getInitialNode();

	T getFinalNode();

	List<T> getNodeSequence();

	/**
	 * Join to path in a new one
	 * @param mazePath
	 * @return
	 */
	MazePath<T> concat(MazePath<T> mazePath);

	/**
	 * Creates a new path with the given node added.
	 * @param mazeNode
	 * @return
	 */
	MazePath<T> concat(T mazeNode);
}
