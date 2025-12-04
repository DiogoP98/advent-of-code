package advent;

import java.util.List;
import java.util.Objects;

public class Day04 {
    public static void main(String[] args) {
        String input = Util.getFile("Day04.txt");
        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    public static int part1(final String input) {
        var map = parseMap(input);
        var rolls = 0;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (hasFewerThanFourAdjacentRoles(map, i, j)) {
                    rolls++;
                }
            }
        }

        return rolls;
    }

    public static long part2(final String input) {
        var map = parseMap(input);
        var rolls = 0;
        var rollsRemoved = true;

        while (rollsRemoved) {
            rollsRemoved = false;

            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    if (hasFewerThanFourAdjacentRoles(map, i, j)) {
                        rolls++;
                        map[i][j] = ".";
                        rollsRemoved = true;
                    }
                }
            }
        }

        return rolls;
    }

    private static boolean hasFewerThanFourAdjacentRoles(final String[][] map, final int i, final int j) {
        if (Objects.equals(map[i][j], ".")) return false;

        var totalAdjacentRoles = countAdjacentRoles(map, i, j);
        return totalAdjacentRoles < 4;
    }

    private static int countAdjacentRoles(final String[][] map, final int i, final int j) {
        var count = 0;
        for (Move move : Move.MOVES) {
            if (!isValidPosition(i + move.dx, j + move.dy, map.length, map[i].length)) continue;

            if (Objects.equals(map[i + move.dx][j + move.dy], "@")) count++;
        }
        return count;
    }

    private static String[][] parseMap(final String input) {
        return input.lines().map(l -> l.split("")).toArray(String[][]::new);
    }

    private static boolean isValidPosition(final int x, final int y, final int height, final int width) {
        return x >= 0 && x < height && y >= 0 && y < width;
    }

    private enum Move {
        UP(-1, 0),
        DOWN(1, 0),
        LEFT(0, -1),
        RIGHT(0, 1),
        UP_LEFT(-1, -1),
        UP_RIGHT(-1, 1),
        BOTTOM_LEFT(1, -1),
        BOTTOM_RIGHT(1, 1);

        private final int dx, dy;

        Move(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        static final List<Move> MOVES = List.of(values());
    }
}
