package advent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day10Test {
    @Test
    void testPart1() {
        String input = TestUtil.getFile("Day10.txt");
        assertEquals(36, Day10.part1(input));
    }

    @Test
    void testPart2() {
        String input = TestUtil.getFile("Day10.txt");
        assertEquals(81, Day10.part2(input));
    }
}
