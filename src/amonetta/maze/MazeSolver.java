package amonetta.maze;

/**
 * Defines the main logic to solve a maze.
 * @param <T> Kind of nodes that this solver understand
 * @param <M> Kind of maze to be solved.
 */
public interface MazeSolver<T extends MazeNode, M extends Maze<T>> {

	MazePath<T> solve(M maze);
}
