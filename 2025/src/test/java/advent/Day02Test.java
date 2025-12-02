package advent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class Day02Test {
    @Test
    void testPart1() {
        String input = TestUtil.getFile("Day02.txt");
        assertEquals(1227775554, Day02.part1(input));
    }

    @Test
    void testPart2() {
        String input = TestUtil.getFile("Day02.txt");
        assertEquals(4174379265L, Day02.part2(input));
    }
}
