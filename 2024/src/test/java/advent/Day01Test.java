package advent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day01Test {
    @Test
    void testPart1() {
        String input = TestUtil.getFile("Day01.txt");
        assertEquals(11, Day01.part1(input));
    }

    @Test
    void testPart2() {
        String input = TestUtil.getFile("Day01.txt");
        assertEquals(31, Day01.part2(input));
    }
}
