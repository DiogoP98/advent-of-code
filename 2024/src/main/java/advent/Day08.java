package advent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day08 {
    public static void main(String[] args) {
        String input = Util.getFile("Day08.txt");
        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    public static long part1(final String input) {
        return calculateAntinodePositions(input, false);
    }

    public static int part2(final String input) {
        return calculateAntinodePositions(input, true);
    }

    private static int calculateAntinodePositions(String input, boolean extendSearch) {
        List<String> lines = input.lines().toList();
        Map<Character, List<Position>> antennasLocations = parseAntennas(lines);
        Set<Position> antinodePositions = new HashSet<>();

        for (List<Position> antennaLocations : antennasLocations.values()) {
            findAntinodePositions(lines, antinodePositions, antennaLocations, extendSearch);
        }

        return antinodePositions.size();
    }

    private static Map<Character, List<Position>> parseAntennas(List<String> lines) {
        Map<Character, List<Position>> antennasLocations = new HashMap<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char character = line.charAt(j);
                if (character != '.') {
                    antennasLocations.computeIfAbsent(character, k -> new ArrayList<>()).add(new Position(i, j));
                }
            }
        }

        return antennasLocations;
    }

    private static void findAntinodePositions(List<String> lines, Set<Position> antinodePositions,
                                              List<Position> antennaLocations, boolean extendSearch) {
        for (int i = 0; i < antennaLocations.size(); i++) {
            for (int j = i + 1; j < antennaLocations.size(); j++) {
                Position p1 = antennaLocations.get(i);
                Position p2 = antennaLocations.get(j);

                int dx = p1.x - p2.x;
                int dy = p1.y - p2.y;

                exploreAntinode(lines, antinodePositions, new Position(p1.x + dx, p1.y + dy), dx, dy, extendSearch);
                exploreAntinode(lines, antinodePositions, new Position(p2.x - dx, p2.y - dy), -dx, -dy, extendSearch);
            }
        }

        if (extendSearch) {
            antinodePositions.addAll(antennaLocations);
        }
    }

    private static void exploreAntinode(List<String> lines, Set<Position> antinodePositions, Position start,
                                        int dx, int dy, boolean extendSearch) {
        Position current = start;
        while (current.isValid(lines)) {
            antinodePositions.add(current);
            if (!extendSearch) break;
            current = new Position(current.x + dx, current.y + dy);
        }
    }

    private record Position(int x, int y) {
        boolean isValid(List<String> lines) {
            return x >= 0 && y >= 0 && x < lines.size() && y < lines.get(x).length();
        }
    }
}

