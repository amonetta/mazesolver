package amonetta.maze.generic;

import amonetta.maze.Heuristic;
import amonetta.maze.Maze;
import amonetta.maze.MazeNode;
import amonetta.maze.MazePath;
import amonetta.maze.MetricMaze;

import java.util.Comparator;
import java.util.List;

/**
 * <p>This Heuristic can be applied to any {@link MetricMaze}
 * <p>If it's possible to know the distance from any node to another, this heuristic will try to prioritize the path that
 * gets closer to the output.
 * @param <T>
 */
public class DecreasingDistanceHeuristic<T extends MazeNode> implements Heuristic<T> {

	@Override
	public boolean appliesTo(Maze<T> maze) {
		return maze instanceof MetricMaze;
	}

	@Override
	public void sortPaths(Maze<T> maze, List<MazePath<T>> paths) {
		MetricMaze<T> metricMaze = (MetricMaze<T>) maze;
		paths.sort(Comparator.comparing((MazePath<T> mazePath) -> metricMaze.getDistanceToEntrance(mazePath.getFinalNode())).reversed());
	}
}
