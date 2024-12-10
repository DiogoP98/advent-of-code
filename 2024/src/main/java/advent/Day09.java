package advent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day09 {
    public static void main(String[] args) {
        String input = Util.getFile("Day09.txt");
        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    public static long part1(final String input) {
        List<Integer> fileBlocks = input.lines().findFirst().get().chars().mapToObj(c -> c - '0').collect(Collectors.toList());
        int lineLength = fileBlocks.size();
        List<FileBlock> fileBlockCounts = new ArrayList<>();

        fileBlockCounts.add(new FileBlock(0, fileBlocks.getFirst()));
        int i = 1;
        int j = lineLength % 2 == 0 ? lineLength - 2 : lineLength - 1;
        int currentIdi = 1;
        int currentIdj = Math.ceilDiv(lineLength, 2) - 1;

        while (i < j) {
            int difference = fileBlocks.get(i) - fileBlocks.get(j);

            if (difference == 0) {
                fileBlockCounts.add(new FileBlock(currentIdj, fileBlocks.get(j)));
                fileBlockCounts.add(new FileBlock(currentIdi, fileBlocks.get(i + 1)));

                i += 2;
                currentIdi += 1;
                j -= 2;
                currentIdj -= 1;
            } else if (difference > 0) {
                fileBlocks.set(i, fileBlocks.get(i) - fileBlocks.get(j));
                fileBlockCounts.add(new FileBlock(currentIdj, fileBlocks.get(j)));

                j -= 2;
                currentIdj -= 1;
            } else {
                fileBlocks.set(j, fileBlocks.get(j) - fileBlocks.get(i));
                fileBlockCounts.add(new FileBlock(currentIdj, fileBlocks.get(i)));

                fileBlockCounts.add(new FileBlock(currentIdi, fileBlocks.get(i + 1)));

                i += 2;
                currentIdi += 1;
            }
        }

        int currentIndex = 0;
        long result = 0;

        for (FileBlock fb : fileBlockCounts) {
            int sum = IntStream.range(currentIndex, currentIndex + fb.length).sum();
            result += (long) sum * fb.id;
            currentIndex += fb.length;
        }

        return result;
    }

    public static long part2(final String input) {
        List<Integer> fileBlocks = input.lines().findFirst().get().chars().mapToObj(c -> c - '0').collect(Collectors.toList());
        int lineLength = fileBlocks.size();
        List<Segment> segments = new LinkedList<>();
        int currentId = 0;

        for (int i = 0; i < lineLength; i++) {
            if (i % 2 == 0) {
                segments.add(new FileBlock(currentId++, fileBlocks.get(i)));
            } else {
                segments.add(new EmptyBlock(fileBlocks.get(i)));
            }
        }

        int firstEmptyBlockIndex = 1;
        int right = segments.size() - 1;

        while (right >= 0) {
            Segment currentRight = segments.get(right);
            if (currentRight instanceof EmptyBlock) {
                right -= 1;
                continue;
            }

            while (firstEmptyBlockIndex < right && !(segments.get(firstEmptyBlockIndex) instanceof EmptyBlock)) {
                firstEmptyBlockIndex += 1;
            }

            int left = firstEmptyBlockIndex;
            Segment currentLeft = segments.get(left);

            while (left <= right) {
                if (currentLeft instanceof EmptyBlock && currentRight instanceof FileBlock) {
                    int difference = currentLeft.length() - currentRight.length();

                    if (difference >= 0) {
                        segments.set(left, currentRight);

                        if (difference > 0) {
                            EmptyBlock spaceToRight = new EmptyBlock(currentRight.length());
                            segments.set(right, spaceToRight);
                            EmptyBlock remainingSpace = new EmptyBlock(difference);
                            segments.add(left + 1, remainingSpace);
                            right += 1;
                        } else {
                            segments.set(right, currentLeft);
                        }

                        break;
                    }
                }

                left++;
                currentLeft = segments.get(left);
            }

            right--;
        }

        int currentIndex = 0;
        long result = 0;
        for(Segment segment : segments) {
            if(segment instanceof EmptyBlock) {
                currentIndex += segment.length();
                continue;
            }

            FileBlock fileBlock = (FileBlock) segment;
            int sum = IntStream.range(currentIndex, currentIndex + fileBlock.length).sum();
            result += (long) sum * fileBlock.id;
            currentIndex += fileBlock.length;
        }

        return result;
    }

    interface Segment {
        int length();
    }

    private record FileBlock(int id, int length) implements Segment {
        @Override
        public int length() {
            return length;
        }
    }

    private record EmptyBlock(int length) implements Segment {
        @Override
        public int length() {
            return length;
        }
    }
}

