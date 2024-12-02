package advent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day02 {
    public static void main(String[] args) {
        String input = Util.getFile("Day02.txt");
        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    public static Integer part1(final String input) {
        int safeLevels = 0;

        for(String level: input.lines().toList()) {
            List<String> numbers = Arrays.asList(level.split("\\s+"));
            List<Integer> differences = getLevelDifferences(numbers);

            if(isSafeDifferences(differences)) {
                safeLevels += 1;
            }
        }

        return safeLevels;
    }

    public static int part2(final String input) {
        int safeLevels = 0;

        for (String level : input.lines().toList()) {
            List<String> numbers = Arrays.asList(level.split("\\s+"));
            List<Integer> differences = getLevelDifferences(numbers);

            if (isSafeDifferences(differences)) {
                safeLevels += 1;
            } else {
                for (int i = 0; i < numbers.size(); i++) {
                    List<String> numbersCopy = new ArrayList<>(numbers);
                    numbersCopy.remove(i);
                    differences = getLevelDifferences(numbersCopy);

                    if (isSafeDifferences(differences)) {
                        safeLevels += 1;
                        break;
                    }
                }
            }
        }

        return safeLevels;
    }

    private static List<Integer> getLevelDifferences(final List<String> numbers) {
        if (numbers.isEmpty() || numbers.size() == 1) {
            return Collections.emptyList();
        }

        List<Integer> differences = new ArrayList<>(numbers.size() - 1);

        for (int i = 1; i < numbers.size(); i++) {
            differences.add(Integer.parseInt(numbers.get(i)) - Integer.parseInt(numbers.get(i - 1)));
        }

        return differences;
    }

    private static boolean isSafeDifferences(List<Integer> differences) {
        float sign = Math.signum(differences.getFirst());

        for (int difference : differences) {
            if (Math.signum(difference) != sign || difference == 0 || Math.abs(difference) > 3) {
                return false;
            }
        }
        return true;
    }
}
