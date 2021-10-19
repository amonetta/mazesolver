package amonetta.maze.generic;

import amonetta.maze.Heuristic;
import amonetta.maze.Maze;
import amonetta.maze.MazeNode;
import amonetta.maze.MazePath;

import java.util.List;

/**
 * <p> This heuristic can be applied to any kind of {@link Maze}
 * <p> If we have no clue about which possible path can be the best one, so random selection will give us a 50-50 chance
 * to find an appropriate path.
 * @param <T>
 */
public class RandomHeuristic <T extends MazeNode> implements Heuristic<T> {

	@Override
	public boolean appliesTo(Maze<T> maze) {
		return true;
	}

	@Override
	public void sortPaths(Maze<T> maze, List<MazePath<T>> paths) {
		paths.sort((mazePath1, mazePath2) -> mazePath1.equals(mazePath2)? 0 : (Math.random() > 0.5)? 1 : -1);
	}
}
