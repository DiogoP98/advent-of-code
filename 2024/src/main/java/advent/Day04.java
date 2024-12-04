package advent;

import java.util.List;

public class Day04 {
    public static void main(String[] args) {
        String input = Util.getFile("Day04.txt");
        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    public static int part1(final String input) {
        List<String> lines = input.lines().toList();

        int matches = 0;

        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                for (Position.Move move : Position.moves) {
                    if (isMatch(lines, "XMAS", new Position(i, j), move)) {
                        matches++;
                    }
                }
            }
        }

        return matches;
    }

    public static int part2(final String input) {
        List<String> lines = input.lines().toList();

        int matches = 0;
        List<Position.Move> diagonalMovesSameDirection = Position.moves.stream()
                .filter(move -> move.x != 0 && move.y != 0 && Math.signum(move.x) == Math.signum(move.y))
                .toList();
        List<Position.Move> diagonalMovesDifferentDirection = Position.moves.stream()
                .filter(move -> move.x != 0 && move.y != 0 && Math.signum(move.x) != Math.signum(move.y))
                .toList();

        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                char currentCharacter = lines.get(i).charAt(j);

                if (currentCharacter != 'A') {
                    continue;
                }

                for (Position.Move moveSameDirection : diagonalMovesSameDirection) {
                    for (Position.Move moveDifferentDirection : diagonalMovesDifferentDirection) {
                        Position firstPosition = new Position(i + moveSameDirection.x * -1, j + moveSameDirection.y * -1);
                        Position secondPosition = new Position(i + moveDifferentDirection.x * -1, j + moveDifferentDirection.y * -1);

                        if (isMatch(lines, "MAS", firstPosition, moveSameDirection)
                                && isMatch(lines, "MAS", secondPosition, moveDifferentDirection)) {
                            matches++;
                        }
                    }
                }
            }
        }

        return matches;
    }

    private static boolean isMatch(List<String> lines, String match, Position firstPosition, Position.Move move) {
        int matchStringPosition = 0;
        Position currentPosition = firstPosition;

        while (matchStringPosition < match.length()) {
            if (!currentPosition.isValid(lines)) {
                return false;
            }

            if (lines.get(currentPosition.x).charAt(currentPosition.y) != match.charAt(matchStringPosition)) {
                return false;
            }

            matchStringPosition++;
            currentPosition = currentPosition.move(move);
        }

        return true;
    }

    private record Position(int x, int y) {
        private record Move(int x, int y) {
        }

        static List<Move> moves = List.of(
                new Move(1, 0),
                new Move(-1, 0),
                new Move(0, 1),
                new Move(0, -1),
                new Move(1, 1),
                new Move(1, -1),
                new Move(-1, 1),
                new Move(-1, -1)
        );

        Position move(Move move) {
            return new Position(this.x + move.x, this.y + move.y);
        }

        boolean isValid(List<String> lines) {
            return x >= 0 && y >= 0 && x < lines.size() && y < lines.get(x).length();
        }
    }
}
