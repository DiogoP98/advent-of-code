
import java.util.Stack
import kotlin.math.max
import kotlin.math.min

data class Point(
    val x: Int,
    val y: Int
) {
    companion object {
        fun fromString(pointString: String): Point {
            val (y, x) = pointString.split(",").map { it.trim().toInt() }

            return Point(x, y)
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        var minWidth = 500
        var maxWidth = 500
        var maxHeight = 0

        input.forEach { line ->
            val points = line.split("->").map { Point.fromString(it) }

            points.forEach { point: Point ->
                minWidth = min(minWidth, point.y)
                maxWidth = max(maxWidth, point.y)

                maxHeight = max(maxHeight, point.x)
            }
        }

        val cave = MutableList(maxHeight + 1) { MutableList(maxWidth - minWidth + 1) { '.' } }

        input.forEach { line ->
            val points = line.split("->").map { Point.fromString(it) }

            var currentPoint = points.first()

            points.drop(1).forEach { point: Point ->
                if (currentPoint.x == point.x) {
                    val startingColumn = min(currentPoint.y, point.y)
                    val endColumn = max(currentPoint.y, point.y)
                    for (column in startingColumn..endColumn) {
                        cave[currentPoint.x][column - minWidth] = '#'
                    }
                } else {
                    val startingRow = min(currentPoint.x, point.x)
                    val endRow = max(currentPoint.x, point.x)
                    for (row in startingRow..endRow) {
                        cave[row][currentPoint.y - minWidth] = '#'
                    }
                }

                currentPoint = point
            }
        }

        val sandPouringPoint = Point(0, 500 - minWidth)
        val possibleMoves = listOf(Pair(1, 0), Pair(1, -1), Pair(1, 1))

        return retriveCount(cave, possibleMoves, sandPouringPoint)
    }

    fun part2(input: List<String>): Int {
        var minWidth = 500
        var maxWidth = 500
        var maxHeight = 0

        input.forEach { line ->
            val points = line.split("->").map { Point.fromString(it) }

            points.forEach { point: Point ->
                minWidth = min(minWidth, point.y)
                maxWidth = max(maxWidth, point.y)

                maxHeight = max(maxHeight, point.x)
            }
        }

        minWidth = minWidth - maxHeight
        maxWidth = maxWidth + maxHeight
        val cave = MutableList(maxHeight + 3) { MutableList(maxWidth - minWidth + 1) { '.' } }

        for (index in minWidth..maxWidth) {
            cave[maxHeight + 2][index - minWidth] = '#'
        }

        input.forEach { line ->
            val points = line.split("->").map { Point.fromString(it) }

            var currentPoint = points.first()

            points.drop(1).forEach { point: Point ->
                if (currentPoint.x == point.x) {
                    val startingColumn = min(currentPoint.y, point.y)
                    val endColumn = max(currentPoint.y, point.y)
                    for (column in startingColumn..endColumn) {
                        cave[currentPoint.x][column - minWidth] = '#'
                    }
                } else {
                    val startingRow = min(currentPoint.x, point.x)
                    val endRow = max(currentPoint.x, point.x)
                    for (row in startingRow..endRow) {
                        cave[row][currentPoint.y - minWidth] = '#'
                    }
                }

                currentPoint = point
            }
        }

        val sandPouringPoint = Point(0, 500 - minWidth)
        val possibleMoves = listOf(Pair(1, 0), Pair(1, -1), Pair(1, 1))

        return retriveCount(cave, possibleMoves, sandPouringPoint)
    }

    val testInput = readInput("Day14_test")
    check(part1(testInput) == 24)
    check(part2(testInput) == 93)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}

private fun retriveCount(cave: MutableList<MutableList<Char>>, possibleMoves: List<Pair<Int, Int>>, sandPouringPoint: Point): Int {
    var moveOutcome: Int
    var standingSand = 0
    val sandStack = Stack<Point>()
    sandStack.add(sandPouringPoint)

    while (!sandStack.empty()) {
        var currentPoint = sandStack.peek()

        moveOutcome = getMoveOutcome(cave, sandStack, currentPoint, possibleMoves)

        if (moveOutcome == -1) {
            break
        }

        if (moveOutcome == 1) {
            cave[currentPoint.x][currentPoint.y] = 'o'
            standingSand += 1
            sandStack.pop()
        }
    }

    return standingSand
}

private fun getMoveOutcome(cave: List<List<Char>>, sandStack: Stack<Point>, currentPoint: Point, possibleMoves: List<Pair<Int, Int>>): Int {
    for (move in possibleMoves) {
        val newPoint = Point(currentPoint.x + move.first, currentPoint.y + move.second)

        if (newPoint.x >= cave.size || newPoint.y < 0 || newPoint.y >= cave[0].size) {
            return -1
        }

        if (cave[newPoint.x][newPoint.y] == '#' || cave[newPoint.x][newPoint.y] == 'o') {
            continue
        } else {
            sandStack.add(newPoint)
            return 0
        }
    }

    return 1
}
