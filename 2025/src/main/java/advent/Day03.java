package advent;

public class Day03 {

    public static void main(String[] args) {
        String input = Util.getFile("Day03.txt");
        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    public static long part1(final String input) {
        return getTotalJoltage(input, 2);
    }

    public static long part2(final String input) {
        return getTotalJoltage(input, 12);
    }

    private static long getTotalJoltage(final String input, final int joltageLength) {
        var total = 0L;

        for (var bank : input.lines().toList()) {
            var bankLength = bank.length();
            var lastJoltageIndex = -1;

            var currentBankMaxJoltage = 0L;
            for (var i = 0; i < joltageLength; i++) {
                var maxJoltage = 0L;

                for (var j = lastJoltageIndex + 1; j < bankLength - (joltageLength - 1 - i); j++) {
                    var currentBattery = Long.parseLong(String.valueOf(bank.charAt(j)));

                    if (currentBattery > maxJoltage) {
                        maxJoltage = currentBattery;
                        lastJoltageIndex = j;
                    }
                }

                currentBankMaxJoltage = currentBankMaxJoltage * 10 + maxJoltage;
            }

            total += currentBankMaxJoltage;
        }

        return total;
    }
}
