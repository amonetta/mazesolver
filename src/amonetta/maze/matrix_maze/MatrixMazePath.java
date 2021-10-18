package amonetta.maze.matrix_maze;

import amonetta.maze.MazePath;

import java.util.LinkedList;
import java.util.List;

public class MatrixMazePath implements MazePath<MatrixTaggedMazeNode> {

	private final List<MatrixTaggedMazeNode> nodeListPath;

	public MatrixMazePath(MatrixTaggedMazeNode node) {
		this.nodeListPath = List.of(node);
	}

	MatrixMazePath(List<MatrixTaggedMazeNode> nodeListPath) {
		this.nodeListPath = nodeListPath;
	}

	@Override
	public MatrixTaggedMazeNode getInitialNode() {
		return nodeListPath.get(0);
	}

	@Override
	public MatrixTaggedMazeNode getFinalNode() {
		return nodeListPath.get(nodeListPath.size() - 1);
	}

	@Override
	public List<MatrixTaggedMazeNode> getNodeSequence() {
		return List.copyOf(nodeListPath);
	}

	@Override
	public MazePath<MatrixTaggedMazeNode> concat(MazePath<MatrixTaggedMazeNode> mazePath) {
		LinkedList<MatrixTaggedMazeNode> nodes = new LinkedList<>(nodeListPath);
		nodes.addAll(mazePath.getNodeSequence());
		return new MatrixMazePath(nodes);
	}

	@Override
	public MazePath<MatrixTaggedMazeNode> concat(MatrixTaggedMazeNode mazeNode) {
		LinkedList<MatrixTaggedMazeNode> nodes = new LinkedList<>(nodeListPath);
		nodes.add(mazeNode);
		return new MatrixMazePath(nodes);
	}
}
