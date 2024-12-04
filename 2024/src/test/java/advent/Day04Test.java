package advent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day04Test {
    @Test
    void testPart1() {
        String input = TestUtil.getFile("Day04.txt");
        assertEquals(18, Day04.part1(input));
    }

    @Test
    void testPart2() {
        String input = TestUtil.getFile("Day04.txt");
        assertEquals(9, Day04.part2(input));
    }
}
