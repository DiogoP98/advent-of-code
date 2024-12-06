package advent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day06Test {
    @Test
    void testPart1() {
        String input = TestUtil.getFile("Day06.txt");
        assertEquals(41, Day06.part1(input));
    }

    @Test
    void testPart2() {
        String input = TestUtil.getFile("Day06.txt");
        assertEquals(6, Day06.part2(input));
    }
}
