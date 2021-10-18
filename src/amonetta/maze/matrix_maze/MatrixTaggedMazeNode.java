package amonetta.maze.matrix_maze;

import amonetta.maze.MazeNode;
import amonetta.maze.TaggedMazeNode;

import java.util.Arrays;
import java.util.Collection;

public class MatrixTaggedMazeNode implements TaggedMazeNode {

	private MatrixTaggedMaze maze;

	private final String tag;

	private final int[] position;

	MatrixTaggedMazeNode(String tag, int i_position, int j_position) {
		this.tag = tag;
		this.position = new int[]{i_position, j_position};
	}

	void setMaze(MatrixTaggedMaze maze) {
		this.maze = maze;
	}

	@Override
	public Collection<MatrixTaggedMazeNode> getNeighbors() {
		return this.maze.getNeighborsFor(this);
	}

	@Override
	public boolean equalsTo(MazeNode other) {
		return this == other || // short verification: if the reference to memory is the same, no further verifications is needed.
				(MatrixTaggedMazeNode.class.isAssignableFrom(other.getClass()) && // At least a sub-class of this.
				this.getMaze() == ((MatrixTaggedMazeNode) other).getMaze() && // Same maze reference.
				Arrays.equals(this.getPosition(), ((MatrixTaggedMazeNode) other).getPosition()) && // Same position in maze.
				this.getTag().equals(((MatrixTaggedMazeNode) other).getTag())); // Same tag
	}

	@Override
	public String getTag() {
		return tag;
	}

	public int[] getPosition() {
		return Arrays.copyOf(position, position.length);
	}

	MatrixTaggedMaze getMaze() {
		return this.maze;
	}
}
