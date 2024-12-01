package advent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day01 {
    public static void main(String[] args) {
        String input = Util.getFile("Day01.txt");
        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    public static Integer part1(String input) {
        Lists lists = getLists(input);

        Collections.sort(lists.leftSide());
        Collections.sort(lists.rightSide());

        return IntStream.range(0, lists.leftSide().size())
                .map(i -> Math.abs(lists.leftSide.get(i) - lists.rightSide.get(i)))
                .sum();
    }

    public static Integer part2(String input) {
        Lists lists = getLists(input);

        Map<Integer, Long> occurrences = lists.rightSide.stream()
                .collect(Collectors.groupingBy(
                        element -> element,
                        Collectors.counting()
                ));

        return lists.leftSide.stream().reduce(0, (a, b) -> a + b * occurrences.getOrDefault(b, 0L).intValue());
    }

    private static Lists getLists(String input) {
        List<Integer> leftSide = new ArrayList<>();
        List<Integer> rightSide = new ArrayList<>();

        for (String line : input.lines().toList()) {
            String[] numbers = line.split("\\s+");

            leftSide.add(Integer.valueOf(numbers[0]));
            rightSide.add(Integer.valueOf(numbers[1]));
        }

        return new Lists(leftSide, rightSide);
    }

    private record Lists(List<Integer> leftSide, List<Integer> rightSide) {
    }
}
