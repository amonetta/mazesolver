package amonetta.maze.matrix_maze;

import amonetta.maze.MetricMaze;
import amonetta.maze.TaggedMaze;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * <p>This kind of maze can be represented as a matrix of n x m dimension which nodes have a simple string tag.
 * <p>The movement's rules are implemented at {@link MatrixTaggedPathFinder}.
 */
public class MatrixTaggedMaze implements MetricMaze<MatrixTaggedMazeNode>, TaggedMaze<MatrixTaggedMazeNode> {

	private final MatrixTaggedMazeNode[][] matrixMazeMap;

	private final MatrixTaggedMazeNode entranceNode;

	private final MatrixTaggedMazeNode outputNode;

	private MatrixTaggedMaze(MatrixTaggedMazeNode[][] matrixMazeMap, MatrixTaggedMazeNode entranceNode, MatrixTaggedMazeNode outputNode) {
		this.matrixMazeMap = matrixMazeMap;
		this.entranceNode = entranceNode;
		this.outputNode = outputNode;
	}

	@Override
	public MatrixTaggedMazeNode getEntranceNode() {
		return entranceNode;
	}

	@Override
	public MatrixTaggedMazeNode getOutputNode() {
		return outputNode;
	}

	@Override
	public Double getDistanceToEntrance(MatrixTaggedMazeNode mazeNode) {
		int[] entrancePosition = getEntranceNode().getPosition();
		int[] mazeNodePosition = mazeNode.getPosition();
		return (double) (Math.abs(mazeNodePosition[0] - entrancePosition[0]) + Math.abs(mazeNodePosition[1] - entrancePosition[1]));
	}

	@Override
	public Double getDistanceToOutput(MatrixTaggedMazeNode mazeNode) {
		int[] outputPosition = getOutputNode().getPosition();
		int[] mazeNodePosition = mazeNode.getPosition();
		return (double) (Math.abs(mazeNodePosition[0] - outputPosition[0]) + Math.abs(mazeNodePosition[1] - outputPosition[1]));
	}

	Collection<MatrixTaggedMazeNode> getNeighborsFor(MatrixTaggedMazeNode node) {
		Set<MatrixTaggedMazeNode> neighbors = new HashSet<>();
		int[] mazeNodePosition = node.getPosition();
		if (mazeNodePosition[0] > 0 && matrixMazeMap[mazeNodePosition[0] - 1][mazeNodePosition[1]] != null) {
			neighbors.add(matrixMazeMap[mazeNodePosition[0] - 1][mazeNodePosition[1]]);
		}
		if (mazeNodePosition[0] < (matrixMazeMap.length - 1) && matrixMazeMap[mazeNodePosition[0]+1][mazeNodePosition[1]] != null) {
			neighbors.add(matrixMazeMap[mazeNodePosition[0] + 1][mazeNodePosition[1]]);
		}
		if (mazeNodePosition[1] > 0 && matrixMazeMap[mazeNodePosition[0]][mazeNodePosition[1] - 1] != null) {
			neighbors.add(matrixMazeMap[mazeNodePosition[0]][mazeNodePosition[1] - 1]);
		}
		if (mazeNodePosition[1] < (matrixMazeMap[0].length - 1) && matrixMazeMap[mazeNodePosition[0]][mazeNodePosition[1] + 1] != null) {
			neighbors.add(matrixMazeMap[mazeNodePosition[0]][mazeNodePosition[1] + 1]);
		}
		return neighbors;
	}

	public static class MatrixTaggedMazeBuilder {

		private String[][] mazeMapTags;

		private int[] entrancePosition;

		private int[] outputPosition;

		/**
		 * Define if the given configuration is valid to build a maze.
		 */
		public boolean isValidMazeMap() {
			// This should be implemented as separately validations rules to raise a comprehensive message,
			// but it's implemented as a simple validation condition for simplification due that it's the aim of this challenge.
			return mazeMapTags != null &&
					entrancePosition != null &&
					outputPosition != null &&
					mazeMapTags.length != 0 && mazeMapTags[0].length != 0 &&
					entrancePosition[0] >= 0 && entrancePosition[1] >= 0 &&
					outputPosition[0] >= 0 && outputPosition[1] >= 0 &&
					entrancePosition[0] < mazeMapTags.length && outputPosition[0] < mazeMapTags.length &&
					entrancePosition[1] < mazeMapTags[1].length && outputPosition[1] < mazeMapTags[1].length;
		}

		public MatrixTaggedMazeBuilder setMazeMap(String[][] mazeMapTags) {
			this.mazeMapTags = mazeMapTags;
			return this;
		}

		public MatrixTaggedMazeBuilder setEntrancePosition(int i, int j) {
			this.entrancePosition = new int[]{i,j};
			return this;
		}

		public MatrixTaggedMazeBuilder setOutputPosition(int i, int j) {
			this.outputPosition = new int[]{i,j};
			return this;
		}

		public MatrixTaggedMaze build() {
			if (!isValidMazeMap()) {
				throw new RuntimeException("Not yet implemented");
			}
			MatrixTaggedMazeNode[][] matrixMazeMap = new MatrixTaggedMazeNode[mazeMapTags.length][mazeMapTags[0].length];
			for (int i = 0; i < matrixMazeMap.length; i++) {
				for (int j = 0; j < matrixMazeMap[0].length; j++) {
					if (mazeMapTags[i][j] != null) {
						matrixMazeMap[i][j] = new MatrixTaggedMazeNode(mazeMapTags[i][j], i, j);
					}
				}
			}
			MatrixTaggedMaze matrixTaggedMaze = new MatrixTaggedMaze(matrixMazeMap,
					matrixMazeMap[entrancePosition[0]][entrancePosition[1]],
					matrixMazeMap[outputPosition[0]][outputPosition[1]]);
			Arrays.stream(matrixMazeMap).flatMap(Arrays::stream)
					.filter(Objects::nonNull)
					.forEach(matrixTaggedMazeNode -> matrixTaggedMazeNode.setMaze(matrixTaggedMaze));
			return matrixTaggedMaze;
		}
	}
}
