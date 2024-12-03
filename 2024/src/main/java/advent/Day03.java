package advent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 {
    public static void main(String[] args) {
        String input = Util.getFile("Day03.txt");
        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    public static int part1(final String input) {
        Matcher matcher = getMatcher(input, "mul\\((\\d+),(\\d+)\\)");

        return matcher.results()
                .mapToInt(match ->
                        Integer.parseInt(match.group(1)) * Integer.parseInt(match.group(2))
                )
                .sum();
    }

    public static int part2(final String input) {
        Matcher matcher = getMatcher(input, "mul\\((\\d+),(\\d+)\\)|do\\(\\)|don't\\(\\)");

        int sum = 0;
        boolean enabled = true;

        while (matcher.find()) {
            String str = matcher.group();

            if (str.equals("do()")) {
                enabled = true;
            } else if (str.equals("don't()")) {
                enabled = false;
            } else if (enabled) {
                sum += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
            }
        }

        return sum;
    }

    private static Matcher getMatcher(final String input, final String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}
