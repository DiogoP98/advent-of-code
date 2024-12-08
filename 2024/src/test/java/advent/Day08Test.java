package advent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day08Test {
    @Test
    void testPart1() {
        String input = TestUtil.getFile("Day08.txt");
        assertEquals(14, Day08.part1(input));
    }

    @Test
    void testPart2() {
        String input = TestUtil.getFile("Day08.txt");
        assertEquals(34, Day08.part2(input));
    }
}
