package amonetta.maze.matrix_maze;

import amonetta.maze.Maze;
import amonetta.maze.MazePath;
import amonetta.maze.PathFinder;
import amonetta.maze.TaggedMaze;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MatrixTaggedPathFinder implements PathFinder<MatrixTaggedMazeNode> {

	private final int pathSize;

	public MatrixTaggedPathFinder(int pathSize) {
		this.pathSize = pathSize;
	}

	@Override
	public boolean appliesTo(Maze maze) {
		return maze instanceof TaggedMaze;
	}

	@Override
	public List<MazePath<MatrixTaggedMazeNode>> getPathsFrom(MatrixTaggedMazeNode mazeNode) {
		return getPathsFrom(mazeNode, pathSize);
	}

	@Override
	public MazePath<MatrixTaggedMazeNode> getEntrancePath(Maze<MatrixTaggedMazeNode> maze) {
		return new MatrixMazePath(maze.getEntranceNode());
	}

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
