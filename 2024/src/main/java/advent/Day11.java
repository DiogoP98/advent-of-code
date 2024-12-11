package advent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11 {
    public static void main(String[] args) {
        String input = Util.getFile("Day11.txt");

        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    public static long part1(String input) {
        return getNumberOfStones(input, 25);
    }

    public static long part2(String input) {
        return getNumberOfStones(input, 75);
    }

    private static long getNumberOfStones(String input, int numberOfBlinks) {
        List<Long> stones = Arrays.stream(input.lines().findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid input"))
                                .split(" "))
                                .map(Long::parseLong)
                                .toList();

        Map<Long, Long> stoneCounts = new HashMap<>();
        for (Long stone : stones) {
            stoneCounts.merge(stone, 1L, Long::sum);
        }

        for (int i = 0; i < numberOfBlinks; i++) {
            Map<Long, Long> updatedStoneCounts = new HashMap<>();

            for (Map.Entry<Long, Long> stoneCount : stoneCounts.entrySet()) {
                long stone = stoneCount.getKey();
                long count = stoneCount.getValue();

                if (stone == 0) {
                    updateMapCount(updatedStoneCounts, 1L, count);
                    continue;
                }

                String stoneString = String.valueOf(stone);
                int stringLength = stoneString.length();

                if (stringLength % 2 == 0) {
                    long firstNumber = Long.parseLong(stoneString.substring(0, stringLength / 2));
                    long secondNumber = Long.parseLong(stoneString.substring(stringLength / 2, stringLength));

                    updateMapCount(updatedStoneCounts, firstNumber, count);
                    updateMapCount(updatedStoneCounts, secondNumber, count);
                } else {
                    updateMapCount(updatedStoneCounts, stone * 2024, count);
                }
            }

            stoneCounts = updatedStoneCounts;
        }

        return stoneCounts.values().stream().mapToLong(Long::longValue).sum();
    }

    private static void updateMapCount(Map<Long, Long> stoneCounts, long stone, long count) {
        stoneCounts.compute(stone, (key, value) -> value == null ? count : value + count);
    }
}
