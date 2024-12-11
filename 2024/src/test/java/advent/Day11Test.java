package advent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day11Test {
    @Test
    void testPart1() {
        String input = TestUtil.getFile("Day11.txt");
        assertEquals(55312, Day11.part1(input));
    }

    @Test
    void testPart2() {
        String input = TestUtil.getFile("Day11.txt");
        assertEquals(65601038650482L, Day11.part2(input));
    }
}
