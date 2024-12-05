package advent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day05 {
    public static void main(String[] args) {
        String input = Util.getFile("Day05.txt");
        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    public static int part1(final String input) {
        Map<Integer, List<Integer>> pageDependencies = new HashMap<>();
        return processInput(input, pageDependencies, Day05::calculateMiddlePage);
    }

    public static int part2(final String input) {
        Map<Integer, List<Integer>> pageDependencies = new HashMap<>();
        return processInput(input, pageDependencies, Day05::fixAndCalculateMiddlePage);
    }

    private static int processInput(final String input, Map<Integer, List<Integer>> pageDependencies, PageProcessor processor) {
        int result = 0;

        for (String line : input.lines().map(String::trim).filter(l -> !l.isEmpty()).toList()) {
            if (line.contains("|")) {
                parsePageDependency(line, pageDependencies);
            } else {
                result += processor.process(line, pageDependencies);
            }
        }

        return result;
    }

    private static void parsePageDependency(String pageOrder, Map<Integer, List<Integer>> pageDependencies) {
        String[] pages = pageOrder.split("\\|");
        int page1 = Integer.parseInt(pages[0].trim());
        int page2 = Integer.parseInt(pages[1].trim());

        pageDependencies.computeIfAbsent(page2, k -> new ArrayList<>()).add(page1);
    }

    private static int calculateMiddlePage(String pageOrder, Map<Integer, List<Integer>> pageDependencies) {
        List<Integer> pages = parsePages(pageOrder.split(","));
        Map<Integer, Integer> pageOccurrences = getPagesFirstOccurrence(pages);

        if (getInvalidPositions(pageOccurrences, pageDependencies).isEmpty()) {
            return pages.get(pages.size() / 2);
        }

        return 0;
    }

    private static int fixAndCalculateMiddlePage(String pageOrder, Map<Integer, List<Integer>> pageDependencies) {
        List<Integer> pages = parsePages(pageOrder.split(","));
        Map<Integer, Integer> pagesFirstOccurrence = getPagesFirstOccurrence(pages);

        Map<Integer, PagePair> invalidPositions = getInvalidPositions(pagesFirstOccurrence, pageDependencies);

        if (invalidPositions.isEmpty()) {
            return 0;
        }

        while (!invalidPositions.isEmpty()) {
            for (PagePair pair : invalidPositions.values()) {
                int page1Idx = pagesFirstOccurrence.get(pair.page1());
                int page2Idx = pagesFirstOccurrence.get(pair.page2());

                Collections.swap(pages, page1Idx, page2Idx);
                pagesFirstOccurrence.put(pair.page1, page2Idx);
                pagesFirstOccurrence.put(pair.page2, page1Idx);
            }

            invalidPositions = getInvalidPositions(pagesFirstOccurrence, pageDependencies);
        }

        return pages.get(pages.size() / 2);
    }

    private static List<Integer> parsePages(String[] pageStrings) {
        return Arrays.stream(pageStrings)
                .map(page -> Integer.parseInt(page.trim()))
                .collect(Collectors.toList());
    }

    private static Map<Integer, Integer> getPagesFirstOccurrence(List<Integer> pages) {
        Map<Integer, Integer> pagesFirstOccurrence = new HashMap<>();

        for (int i = 0; i < pages.size(); i++) {
            pagesFirstOccurrence.putIfAbsent(pages.get(i), i);
        }

        return pagesFirstOccurrence;
    }

    private static Map<Integer, PagePair> getInvalidPositions(Map<Integer, Integer> pagesFirstOccurrence, Map<Integer, List<Integer>> pageDependencies) {
        Map<Integer, PagePair> invalidPositions = new HashMap<>();

        for (var entry : pagesFirstOccurrence.entrySet()) {
            int page = entry.getKey();
            int position = entry.getValue();

            for (int dependency : pageDependencies.getOrDefault(page, Collections.emptyList())) {
                if (pagesFirstOccurrence.containsKey(dependency) && pagesFirstOccurrence.get(dependency) > position) {
                    invalidPositions.put(page, new PagePair(page, dependency));
                }
            }
        }

        return invalidPositions;
    }

    private record PagePair(int page1, int page2) {
    }

    @FunctionalInterface
    private interface PageProcessor {
        int process(String pageOrder, Map<Integer, List<Integer>> pageDependencies);
    }
}
