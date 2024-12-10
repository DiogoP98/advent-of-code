package advent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day10 {
    public static void main(String[] args) {
        String input = Util.getFile("Day10.txt");

        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    public static long part1(String input) {
        return countTrailheads(input, false);
    }

    public static long part2(String input) {
        return countTrailheads(input, true);
    }

    private static long countTrailheads(String input, boolean allowBacktracking) {
        int[][] map = parseMap(input);

        int trailheadCount = 0;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] != 0) {
                    continue;
                }
                trailheadCount += findTrailheads(map, new Position(i, j), new HashSet<>(), -1, allowBacktracking);
            }
        }

        return trailheadCount;
    }

    private static int findTrailheads(int[][] map, Position position, Set<Position> visited, int previousValue, boolean allowBacktracking) {
        if (!position.isValid(map) || map[position.x][position.y] != previousValue + 1 || visited.contains(position)) {
            return 0;
        }

        int currentValue = map[position.x][position.y];

        if (currentValue == 9) {
            if (!allowBacktracking) {
                visited.add(position);
            }
            return 1;
        }

        visited.add(position);
        int trailheadCount = 0;

        for (Move move : Move.VALID_MOVES) {
            trailheadCount += findTrailheads(map, position.move(move), visited, currentValue, allowBacktracking);
        }

        if (allowBacktracking) {
            visited.remove(position);
        }

        return trailheadCount;
    }

    private static int[][] parseMap(String input) {
        return input.lines()
                .map(line -> line.chars()
                        .map(c -> c - '0')
                        .toArray())
                .toArray(int[][]::new);
    }

    private record Position(int x, int y) {
        Position move(Move move) {
            return new Position(x + move.dx, y + move.dy);
        }

        boolean isValid(int[][] map) {
            return x >= 0 && x < map.length && y >= 0 && y < map[x].length;
        }
    }

    private enum Move {
        UP(-1, 0),
        DOWN(1, 0),
        LEFT(0, -1),
        RIGHT(0, 1);

        private final int dx, dy;

        Move(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        static final List<Move> VALID_MOVES = List.of(values());
    }
}
