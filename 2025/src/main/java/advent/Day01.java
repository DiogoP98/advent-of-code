package advent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day01 {
    private static Map<Character, Integer> directionMapping = Map.ofEntries(Map.entry('L', -1), Map.entry('R', 1));

    public static void main(String[] args) {
        String input = Util.getFile("Day01.txt");
        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    public static Integer part1(final String input) {
        var currentNumber = 50;
        var password = 0;

        for (String command : input.lines().toList()) {
            var commandInstructions = command.toCharArray();
            var direction = directionMapping.get(commandInstructions[0]);
            var clicks = Integer.valueOf(command.substring(1));

            currentNumber = Math.floorMod(currentNumber + direction * clicks, 100);

            if (currentNumber == 0) password++;
        }

        return password;
    }

    //5941
    public static Integer part2(final String input) {
        var currentNumber = 50;
        var password = 0;

        for (String command : input.lines().toList()) {
            var commandInstructions = command.toCharArray();
            var direction = directionMapping.get(commandInstructions[0]);
            var clicks = Integer.valueOf(command.substring(1));

            var previousNumber = currentNumber;
            var position = currentNumber + direction * clicks;
            password += Math.abs(position / 100);
            currentNumber = Math.floorMod(position, 100);

            if (previousNumber > 0 && position < 0) password++;
        }

        return password;
    }
}
