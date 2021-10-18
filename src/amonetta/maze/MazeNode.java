package amonetta.maze;

import java.util.Collection;

/**
 * <p> The atomic unit of a maze. It represents the minimun unit of movement.
 * <p> Even, some kind of nodes may contents any sort of meta data.
 */
public interface MazeNode {

	/**
	 * @return A collection of nodes that are reachable form this one.
	 */
	Collection<? extends MazeNode> getNeighbors();

	/**
	 * This defines logical equality between to nodes. Even two different instances of node may be equals if they have
	 * the same logical references.
	 * @param other
	 * @return <i>true</i> if the other node is logically equals to this.
	 */
	boolean equalsTo(MazeNode other);

}
