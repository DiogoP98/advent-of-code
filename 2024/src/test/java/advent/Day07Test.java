package advent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day07Test {
    @Test
    void testPart1() {
        String input = TestUtil.getFile("Day07.txt");
        assertEquals(3749, Day07.part1(input));
    }

    @Test
    void testPart2() {
        String input = TestUtil.getFile("Day07.txt");
        assertEquals(11387, Day07.part2(input));
    }
}
