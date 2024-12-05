package advent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day05Test {
    @Test
    void testPart1() {
        String input = TestUtil.getFile("Day05.txt");
        assertEquals(143, Day05.part1(input));
    }

    @Test
    void testPart2() {
        String input = TestUtil.getFile("Day05.txt");
        assertEquals(123, Day05.part2(input));
    }
}
