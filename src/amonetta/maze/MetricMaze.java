package amonetta.maze;

/**
 * Defines a maze where we can know the distance for any node.
 * @param <T>
 */
public interface MetricMaze<T extends MazeNode> extends Maze<T> {

	Double getDistanceToEntrance(T mazeNode);

	Double getDistanceToOutput(T mazeNode);
}
