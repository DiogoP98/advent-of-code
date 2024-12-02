package advent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day02Test {
    @Test
    void testPart1() {
        String input = TestUtil.getFile("Day02.txt");
        assertEquals(2, Day02.part1(input));
    }

    @Test
    void testPart2() {
        String input = TestUtil.getFile("Day02.txt");
        assertEquals(4, Day02.part2(input));
    }
}
