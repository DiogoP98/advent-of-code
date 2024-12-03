package advent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day03Test {
    @Test
    void testPart1() {
        String input = TestUtil.getFile("Day03.txt");
        assertEquals(161, Day03.part1(input));
    }

    @Test
    void testPart2() {
        String input = TestUtil.getFile("Day03.txt");
        assertEquals(48, Day03.part2(input));
    }
}
