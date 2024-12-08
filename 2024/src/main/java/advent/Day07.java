package advent;

import java.util.Arrays;
import java.util.List;

public class Day07 {
    public static void main(String[] args) {
        String input = Util.getFile("Day07.txt");
        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    public static long part1(final String input) {
        return getPossibleOperetions(input, false);
    }

    public static long part2(final String input) {
        return getPossibleOperetions(input, true);
    }

    private static long getPossibleOperetions(final String input, boolean concatenationEnabled) {
        long result = 0;

        for (String line : input.lines().toList()) {
            String[] lineValues = line.split(":");
            long target = Long.parseLong(lineValues[0]);
            List<Long> values = Arrays.stream(lineValues[1].trim().split(" ")).map(Long::parseLong).toList();

            if (values.size() == 1) {
                result += values.getFirst() == target ? target : 0;
                continue;
            }

            if (possibleOperation(target, values, 1, values.getFirst(), concatenationEnabled)) {
                result += target;
            }
        }

        return result;
    }

    private static boolean possibleOperation(long target, List<Long> values, int currentIndex, long currentOperationValue, boolean concatenationEnabled) {
        if (currentOperationValue == target) {
            return true;
        }

        if (currentOperationValue > target || currentIndex >= values.size()) {
            return false;
        }

        long currentValue = values.get(currentIndex);

        if (target / currentOperationValue < currentValue) {
            return possibleOperation(target, values, currentIndex + 1, currentOperationValue + currentValue, concatenationEnabled);
        }

        boolean possibleOperation =  possibleOperation(target, values, currentIndex + 1, currentOperationValue + currentValue, concatenationEnabled) ||
                possibleOperation(target, values, currentIndex + 1, currentOperationValue * currentValue, concatenationEnabled);

        if (concatenationEnabled) {
            possibleOperation = possibleOperation || possibleOperation(target, values, currentIndex + 1, Long.parseLong(currentOperationValue + "" + currentValue), concatenationEnabled);
        }

        return possibleOperation;
    }
}
