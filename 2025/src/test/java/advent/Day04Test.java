package advent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day04Test {
    @Test
    void testPart1() {
        String input = TestUtil.getFile("Day04.txt");
        assertEquals(13, Day04.part1(input));
    }

    @Test
    void testPart2() {
        String input = TestUtil.getFile("Day04.txt");
        assertEquals(43, Day04.part2(input));
    }
}
