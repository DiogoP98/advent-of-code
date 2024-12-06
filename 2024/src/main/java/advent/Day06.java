package advent;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Day06 {
    public static void main(String[] args) {
        String input = Util.getFile("Day06.txt");
        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    public static int part1(final String input) {
        String[][] lab = parseLab(input);
        Position startPosition = getStartPosition(lab);

        if (startPosition == null) {
            return 0;
        }

        Set<PositionIgnoringDirection> visitedPositions = new HashSet<>();
        PositionIgnoringDirection currentPosition = new PositionIgnoringDirection(startPosition);

        while (currentPosition.position.isValid(lab)) {
            Position position = currentPosition.position;
            visitedPositions.add(currentPosition);

            while (position.isNextObstacle(lab)) {
                position = position.rotate();
            }

            currentPosition = new PositionIgnoringDirection(position.move());
        }

        return visitedPositions.size();
    }

    public static int part2(final String input) {
        String[][] lab = parseLab(input);
        Position currentPosition = getStartPosition(lab);

        if (currentPosition == null) {
            return 0;
        }

        int possibleLoops = 0;

        for (int i = 0; i < lab.length; i++) {
            for (int j = 0; j < lab[i].length; j++) {
                if (lab[i][j].equals(".")) {
                    lab[i][j] = "#";
                    if (isLoop(lab, currentPosition)) {
                        possibleLoops += 1;
                    }
                    lab[i][j] = ".";
                }
            }
        }

        return possibleLoops;
    }

    private static String[][] parseLab(final String input) {
        return input.lines().map(l -> l.split("")).toArray(String[][]::new);
    }

    private static Position getStartPosition(final String[][] lab) {
        for (int i = 0; i < lab.length; i++) {
            for (int j = 0; j < lab[i].length; j++) {
                if (lab[i][j].equals("^")) {
                    return new Position(i, j, Position.Direction.UP);
                }
            }
        }

        return null;
    }

    private static boolean isLoop(final String[][] lab, final Position startPosition) {
        Set<Position> visitedPositions = new HashSet<>();
        Position currentPosition = startPosition;

        while (currentPosition.isValid(lab)) {
            if (visitedPositions.contains(currentPosition)) {
                return true;
            }

            visitedPositions.add(currentPosition);

            while (currentPosition.isNextObstacle(lab)) {
                currentPosition = currentPosition.rotate();
            }

            currentPosition = currentPosition.move();
        }

        return false;
    }

    private record Position(int x, int y, Direction direction) {
        private record Move(int x, int y) {
        }

        private enum Direction {
            UP(new Move(-1, 0)),
            RIGHT(new Move(0, 1)),
            DOWN(new Move(1, 0)),
            LEFT(new Move(0, -1));

            private final Move move;

            Direction(final Move move) {
                this.move = move;
            }

            public Direction next() {
                return switch (this) {
                    case UP -> RIGHT;
                    case RIGHT -> DOWN;
                    case DOWN -> LEFT;
                    case LEFT -> UP;
                };
            }
        }

        boolean isValid(final String[][] lab) {
            return x >= 0 && y >= 0 && x < lab.length && y < lab[x].length;
        }

        Position move() {
            Move move = this.direction.move;
            return new Position(this.x + move.x, this.y + move.y, this.direction);
        }

        boolean isNextObstacle(final String[][] lab) {
            Position nextPosition = move();

            if (!nextPosition.isValid(lab)) {
                return false;
            }

            return lab[nextPosition.x][nextPosition.y].equals("#");
        }

        Position rotate() {
            return new Position(this.x, this.y, this.direction.next());
        }
    }

    private record PositionIgnoringDirection(Position position) {
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            PositionIgnoringDirection other = (PositionIgnoringDirection) obj;
            return position.x() == other.position.x() && position.y() == other.position.y();
        }

        @Override
        public int hashCode() {
            return Objects.hash(position.x(), position.y());
        }
    }
}
