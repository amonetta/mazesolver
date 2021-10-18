package amonetta.maze.generic;

import amonetta.maze.Heuristic;
import amonetta.maze.Maze;
import amonetta.maze.MazeNode;
import amonetta.maze.MazePath;

import java.util.List;

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
