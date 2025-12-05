package advent;

import java.util.List;

public class Day05 {
    public static void main(String[] args) {
        String input = Util.getFile("Day05.txt");
        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    public static long part1(final String input) {
        var lines = input.lines().toList();
        var tree = new RangeSegmentTree();
        var lineIndex = parseRanges(lines, tree);

        var freshProducts = 0L;
        for (int i = lineIndex; i < lines.size(); i++) {
            long number = Long.parseLong(lines.get(i));
            if (tree.contains(number)) {
                freshProducts++;
            }
        }

        return freshProducts;
    }

    public static long part2(final String input) {
        var lines = input.lines().toList();
        var tree = new RangeSegmentTree();
        parseRanges(lines, tree);

        return tree.getTotalCoverage();
    }

    private static int parseRanges(final List<String> lines, final RangeSegmentTree tree) {
        var lineIndex = 0;
        while (lineIndex < lines.size() && !lines.get(lineIndex).isBlank()) {
            var ranges = lines.get(lineIndex).split("-");

            var leftBoundary = Long.parseLong(ranges[0]);
            var rightBoundary = Long.parseLong(ranges[1]);
            tree.addRange(leftBoundary, rightBoundary);

            lineIndex++;
        }

        return ++lineIndex;
    }
}
