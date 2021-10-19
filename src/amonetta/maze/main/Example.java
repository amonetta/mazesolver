package amonetta.maze.main;

import amonetta.maze.MazePath;
import amonetta.maze.generic.DecreasingDistanceHeuristic;
import amonetta.maze.generic.RandomHeuristic;
import amonetta.maze.generic.StackingSolver;
import amonetta.maze.matrix_maze.MatrixTaggedMaze;
import amonetta.maze.matrix_maze.MatrixTaggedMazeNode;
import amonetta.maze.matrix_maze.MatrixTaggedPathFinder;

public class Example {

	public static void main(String[] args) {
		// :: Define number of steps per movement. ::
		int stepsPerPath = 3;
		// :: Define the maze map as a matrix ::
			// Here suggestion for a very simple maze map
		/*String[][] taggedMazeStringMap = {
			{"A", "B", "D", "D", "C"},
			{"A", "C", "D", "A", "C"},
			{"A", "C", "C", "D", "C"},
			{"C", "D", "D", "C", "B"},
			{"D", "C", "D", "D", "A"},
		};*/
			// Here the given example for this challenge
			// Suggestion: add some null spaces to act as path blockers and see as how results changes. ;)
		String[][] taggedMazeStringMap = {
				{"A", "B", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A"},
				{"A", "C", "A", "D", "D", "E", "A", "C", "C", "C", "D", "A"},
				{"A", "C", "A", "D", "D", "E", "A", "D", "A", "D", "A", "A"},
				{"A", "A", "A", "A", "A", "E", "D", "D", "A", "D", "E", "A"},
				{"A", "C", "C", "D", "D", "D", "A", "A", "A", "A", "E", "A"},
				{"A", "C", "A", "A", "A", "A", "A", "D", "D", "D", "E", "A"},
				{"A", "D", "D", "D", "E", "E", "A", "C", "A", "A", "A", "A"},
				{"A", "A", "A", "E", "A", "E", "A", "C", "C", "D", "D", "A"},
				{"A", "D", "E", "E", "A", "D", "A", "A", "A", "A", "A", "A"},
				{"A", "A", "D", "A", "A", "D", "A", "C", "D", "D", "A", "A"},
				{"A", "D", "D", "D", "A", "D", "C", "C", "A", "D", "E", "B"},
				{"A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A"},
		};
		// :: Initialize the Maze ::
		MatrixTaggedMaze maze = new MatrixTaggedMaze.MatrixTaggedMazeBuilder()
				// Set the maze map from matrix.
				.setMazeMap(taggedMazeStringMap)
				// Set the initial position
				.setEntrancePosition(0,1)
				// Set the target output position. Completely free, it isn't needed to be a border node!
				.setOutputPosition(10,11)
				.build();
		// :: Initialize the solver ::
		StackingSolver<MatrixTaggedMazeNode, MatrixTaggedMaze> solver = new StackingSolver(
				// Choose one heuristic method
				new DecreasingDistanceHeuristic<>(), // Try to decrease distance to the exit of the maze with each movement.
				//new RandomHeuristic(), // Just choose a random way per each movement.
				new MatrixTaggedPathFinder(stepsPerPath), // The Path Finder defines the movement rules for the maze solver.
				true); // Allows or disallows path overlapping.

		// :: Solve your maze with given configuration ::
		MazePath<MatrixTaggedMazeNode> result = solver.solve(maze);

		// :: Print solution ::
		if (result == null) {
			System.out.println("No path found");
		} else {
			result.getNodeSequence().stream()
					.forEach(matrixTaggedMazeNode -> {
						int[] nodePosition = matrixTaggedMazeNode.getPosition();
						System.out.println(String.format("%s/(%d,%d)", matrixTaggedMazeNode.getTag(), nodePosition[0], nodePosition[1]));
					}
			);
		}
	}
}
