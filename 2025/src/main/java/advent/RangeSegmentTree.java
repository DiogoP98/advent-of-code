package advent;

public class RangeSegmentTree {

    private final Node root;
    private static final long MIN_VAL = 0L;
    private static final long MAX_VAL = Long.MAX_VALUE;

    public RangeSegmentTree() {
        this.root = new Node();
    }

    public void addRange(final long start, final long end) {
        update(root, MIN_VAL, MAX_VAL, start, end, 1);
    }

    public boolean contains(final long point) {
        if (point < MIN_VAL || point > MAX_VAL) return false;
        return query(root, MIN_VAL, MAX_VAL, point) > 0;
    }

    public long getTotalCoverage() {
        return root.totalLength;
    }

    private void update(final Node node, final long start, final long end, final long left, final long right, final long delta) {
        if (start > end || start > right || end < left) {
            return;
        }

        if (start >= left && end <= right) {
            node.count += delta;
        } else {
            var mid = start + (end - start) / 2;

            if (node.left == null) node.left = new Node();
            if (node.right == null) node.right = new Node();

            update(node.left, start, mid, left, right, delta);
            update(node.right, mid + 1, end, left, right, delta);
        }

        if (node.count > 0) {
            node.totalLength = end - start + 1;
        } else if (start == end) {
            node.totalLength = 0;
        } else {
            var leftLen = (node.left == null) ? 0 : node.left.totalLength;
            var rightLen = (node.right == null) ? 0 : node.right.totalLength;
            node.totalLength = leftLen + rightLen;
        }
    }

    private long query(final Node node, final long start, final long end, final long point) {
        if (node.count > 0) {
            return node.count;
        }

        if (start == end) {
            return 0;
        }

        var mid = start + (end - start) / 2;

        if (point <= mid) {
            return (node.left == null) ? 0 : query(node.left, start, mid, point);
        } else {
            return (node.right == null) ? 0 : query(node.right, mid + 1, end, point);
        }
    }

    private static class Node {
        long count = 0;
        long totalLength = 0;
        Node left, right;
    }
}
