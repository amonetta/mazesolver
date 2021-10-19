package amonetta.maze.matrix_maze;

import amonetta.maze.Maze;
import amonetta.maze.MazePath;
import amonetta.maze.PathFinder;
import amonetta.maze.TaggedMaze;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This movement's rules force to move over the same node tag giving n-steps.
 */
public class MatrixTaggedPathFinder implements PathFinder<MatrixTaggedMazeNode> {

	private final int pathSize;

	/**
	 * @param pathSize number of steps per movement.
	 */
	public MatrixTaggedPathFinder(int pathSize) {
		this.pathSize = pathSize;
	}

	@Override
	public boolean appliesTo(Maze maze) {
		return maze instanceof TaggedMaze;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<MazePath<MatrixTaggedMazeNode>> getPathsFrom(MatrixTaggedMazeNode mazeNode) {
		return getPathsFrom(mazeNode, pathSize);
	}

	@Override
	public MazePath<MatrixTaggedMazeNode> getEntrancePath(Maze<MatrixTaggedMazeNode> maze) {
		return new MatrixMazePath(maze.getEntranceNode());
	}

	/**
	 * Recursive definition to find paths from given node.
	 * @param mazeNode base node to look up paths.
	 * @param n number of nodes per path.
	 * @return all found neighbor paths for the given node.
	 */
	List<MazePath<MatrixTaggedMazeNode>> getPathsFrom(MatrixTaggedMazeNode mazeNode, int n) {
		if (n == 1) {
			Collection<MatrixTaggedMazeNode> neighbors = mazeNode.getNeighbors();
			return neighbors.stream().map(neighbor -> new MatrixMazePath(neighbor)).collect(Collectors.toList());
		}
		return getPathsFrom(mazeNode,n - 1).stream().flatMap(path ->
				path.getFinalNode().getNeighbors().stream()
						.filter(neighbor -> neighbor.getTag().equals(path.getFinalNode().getTag()) && !path.getNodeSequence().contains(neighbor))
						.map(neighbor -> new MatrixMazePath(new LinkedList<>(path.getNodeSequence()){{this.add(neighbor);}}))
		).collect(Collectors.toList());
	}
}
