package amonetta.maze.generic;

import amonetta.maze.Heuristic;
import amonetta.maze.Maze;
import amonetta.maze.MazeNode;
import amonetta.maze.MazePath;
import amonetta.maze.MazeSolver;
import amonetta.maze.PathFinder;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * <p> The stacking solving strategy uses two stacks to iterate until find the solution like reading a tree. The order to
 * test solutions is given by the selected heuristic.
 * <p> With the {@code avoidPAthOverlapping = true} option is possible to prevent find solutions that involves cross over
 * already given steps.
 * @param <T>
 * @param <M>
 */
public class StackingSolver<T extends MazeNode, M extends Maze<T>> implements MazeSolver<T,M> {

	private final Heuristic<T> heuristic;

	private final PathFinder<T> pathFinder;

	private final boolean avoidPathOverlapping;

	public StackingSolver(Heuristic<T> heuristic, PathFinder<T> pathFinder, boolean avoidPathOverlapping) {
		this.heuristic = heuristic;
		this.pathFinder = pathFinder;
		this.avoidPathOverlapping = avoidPathOverlapping;
	}

	public StackingSolver(Heuristic<T> heuristic, PathFinder<T> pathFinder) {
		this(heuristic, pathFinder, false);
	}

	@Override
	public MazePath<T> solve(M maze) {
		// Initialize the stack with the entrance path
		Stack<StackedPath<T>> stackedPaths = new Stack<>();
		MazePath<T> entrancePath = pathFinder.getEntrancePath(maze);
		List<MazePath<T>> paths = new LinkedList<>(){{this.add(entrancePath);}};

		// Here we take care of not step over two time from same paths. Preventing infinite loops.
		List<T> visitedNodes = new LinkedList<>();
		Stack<MazePath<T>> solutionStack = new Stack<>();

		stackedPaths.push(new StackedPath(null, paths));
		while (!stackedPaths.empty()) {
			StackedPath stackedPath = stackedPaths.peek();
			// Check final condition: reached output?
			MazePath<T> mazePath = (MazePath<T>) stackedPath.getMazePathsFromNode().get(0);
			solutionStack.push(mazePath);
			stackedPath.getMazePathsFromNode().remove(0);
			if (stackedPath.getMazePathsFromNode().isEmpty()) {
				stackedPaths.pop();
			}
			if (mazePath.getFinalNode().getNeighbors().stream().anyMatch(neighbor -> maze.getOutputNode().equalsTo(neighbor))) {
				return composeSolution(solutionStack).concat(maze.getOutputNode());
			}

			// Recurrent iterations
			T headNode = mazePath.getFinalNode();

			// Get possible paths from actual node.
			List<MazePath<T>> newPaths = pathFinder.getPathsFrom(headNode);

			//Prevent infinite loop
			if (avoidPathOverlapping) {
				visitedNodes.addAll(mazePath.getNodeSequence());
				newPaths = newPaths.stream().filter(newMazePath -> !visitedNodes.stream().anyMatch(visitedNode -> newMazePath.getNodeSequence().contains(visitedNode))).collect(Collectors.toList());
			} else {
				visitedNodes.add(headNode);
				newPaths = newPaths.stream().filter(newMazePath -> !visitedNodes.contains(newMazePath.getFinalNode())).collect(Collectors.toList());
			}

			if (newPaths.isEmpty()) {
				// If no more path from here, give a step back.
				solutionStack.pop();
				if (avoidPathOverlapping) {
					visitedNodes.removeAll(mazePath.getNodeSequence());
				} else {
					visitedNodes.remove(headNode);
				}
			} else {
				// sort possible paths according given heuristic.
				heuristic.sortPaths(maze, newPaths);
				stackedPaths.push(new StackedPath<>(headNode, newPaths));
			}
		}
		return null; // the maze has no solution.
	}

	private MazePath<T> composeSolution(Stack<MazePath<T>> stackedPaths) {
		return stackedPaths.stream()
				.reduce((mazePath1, mazePath2) -> mazePath1.concat(mazePath2))
				.get();
	}

	private static class StackedPath<T extends MazeNode> {

		private final T mazeNode;

		private final List<MazePath<T>> mazePathsFromNode;

		public StackedPath(T mazeNode, List<MazePath<T>> mazePathsFromNode) {
			this.mazeNode = mazeNode;
			this.mazePathsFromNode = mazePathsFromNode;
		}

		public T getMazeNode() {
			return mazeNode;
		}

		public List<MazePath<T>> getMazePathsFromNode() {
			return mazePathsFromNode;
		}
	}
}
