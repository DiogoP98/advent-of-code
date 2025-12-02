package advent;

public class Day02 {

    public static void main(String[] args) {
        String input = Util.getFile("Day02.txt");
        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    public static Long part1(final String input) {
        return sumMatchingNumbers(input, Day02::hasMirroredHalves);
    }

    public static Long part2(final String input) {
        return sumMatchingNumbers(input, Day02::hasRepeatingPattern);
    }

    private static long sumMatchingNumbers(final String input, NumberPredicate predicate) {
        var ranges = input.split(",");
        var sum = 0L;

        for (String range : ranges) {
            var boundaries = range.split("-");
            var start = Long.parseLong(boundaries[0].strip());
            var end = Long.parseLong(boundaries[1].strip());

            for (var num = start; num <= end; num++) {
                if (predicate.test(num)) {
                    sum += num;
                }
            }
        }

        return sum;
    }

    private static boolean hasMirroredHalves(final long num) {
        var str = String.valueOf(num);
        var length = str.length();

        if (length % 2 != 0) {
            return false;
        }

        var middle = length / 2;
        return str.substring(0, middle).equals(str.substring(middle));
    }

    private static boolean hasRepeatingPattern(final long num) {
        var str = String.valueOf(num);
        var length = str.length();

        for (int patternLength = 1; patternLength <= length / 2; patternLength++) {
            if (length % patternLength != 0) {
                continue;
            }

            var pattern = str.substring(0, patternLength);
            if (matchesPattern(str, pattern, patternLength)) {
                return true;
            }
        }

        return false;
    }

    private static boolean matchesPattern(String str, String pattern, int patternLength) {
        for (int i = patternLength; i < str.length(); i += patternLength) {
            if (!str.substring(i, i + patternLength).equals(pattern)) {
                return false;
            }
        }
        return true;
    }

    @FunctionalInterface
    private interface NumberPredicate {
        boolean test(long num);
    }
}
