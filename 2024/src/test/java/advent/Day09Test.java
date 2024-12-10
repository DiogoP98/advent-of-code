package advent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day09Test {
    @Test
    void testPart1() {
        String input = TestUtil.getFile("Day09.txt");
        assertEquals(1928, Day09.part1(input));
    }

    @Test
    void testPart2() {
        String input = TestUtil.getFile("Day09.txt");
        assertEquals(2858, Day09.part2(input));
    }
}
