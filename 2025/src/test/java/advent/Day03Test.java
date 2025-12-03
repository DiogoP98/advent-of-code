package advent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day03Test {
    @Test
    void testPart1() {
        String input = TestUtil.getFile("Day03.txt");
        assertEquals(357, Day03.part1(input));
    }

    @Test
    void testPart2() {
        String input = TestUtil.getFile("Day03.txt");
        assertEquals(3121910778619L, Day03.part2(input));
    }
}
