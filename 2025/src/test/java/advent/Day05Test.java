package advent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day05Test {
    @Test
    void testPart1() {
        String input = TestUtil.getFile("Day05.txt");
        assertEquals(3, Day05.part1(input));
    }

    @Test
    void testPart2() {
        String input = TestUtil.getFile("Day05.txt");
        assertEquals(14, Day05.part2(input));
    }
}
