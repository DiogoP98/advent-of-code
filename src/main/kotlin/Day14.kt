
import java.util.Stack
import kotlin.math.max
import kotlin.math.min

data class Point(
    val x: Int,
    val y: Int
)

fun main() {
    fun part1(input: List<String>): Int {
        val (blockedPoints, maxHeight) = retrieveBlockedPoints(input)

        return retriveCount(blockedPoints, maxHeight, true)
    }

    fun part2(input: List<String>): Int {
        val (blockedPoints, maxHeight) = retrieveBlockedPoints(input)

        return retriveCount(blockedPoints, maxHeight + 2, false)
    }

    val testInput = readInput("Day14_test")
    check(part1(testInput) == 24)
    check(part2(testInput) == 93)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}

private fun retrieveBlockedPoints(input: List<String>): Pair<MutableSet<Point>, Int> {
    val blockedPoints = mutableSetOf<Point>()
    var maxHeight = 0

    input.forEach { line ->
        val points = line.split("->").map(::stringToPoint)

        points.windowed(2).forEach { pointPair ->
            val (firstPoint, secondPoint) = pointPair

            maxHeight = max(maxHeight, max(firstPoint.x, secondPoint.x))

            for (x in min(firstPoint.x, secondPoint.x)..max(firstPoint.x, secondPoint.x)) {
                for (y in min(firstPoint.y, secondPoint.y)..max(firstPoint.y, secondPoint.y)) {
                    blockedPoints.add(Point(x, y))
                }
            }
        }
    }

    return Pair(blockedPoints, maxHeight)
}

private fun stringToPoint(pointString: String): Point {
    val (y, x) = pointString.split(",").map { it.trim().toInt() }

    return Point(x, y)
}

private fun retriveCount(
    blockedPoints: MutableSet<Point>,
    maxHeight: Int,
    abyss: Boolean
): Int {
    val possibleMoves = listOf(Pair(1, 0), Pair(1, -1), Pair(1, 1))
    val sandStack = Stack<Point>()
    sandStack.add(Point(0, 500))

    var standingSand = 0

    while (!sandStack.empty()) {
        var currentPoint = sandStack.peek()

        val moveOutcome = getMoveOutcome(blockedPoints, sandStack, maxHeight, currentPoint, possibleMoves)

        if (moveOutcome == -1) {
            if (abyss) {
                return standingSand
            } else {
                blockedPoints.add(currentPoint)
                sandStack.pop()
            }
        }

        if (moveOutcome == 1) {
            blockedPoints.add(currentPoint)
            standingSand += 1
            sandStack.pop()
        }
    }

    return standingSand
}

private fun getMoveOutcome(
    blockedPoints: MutableSet<Point>,
    sandStack: Stack<Point>,
    maxHeight: Int,
    currentPoint: Point,
    possibleMoves: List<Pair<Int, Int>>
): Int {
    for (move in possibleMoves) {
        val newPoint = Point(currentPoint.x + move.first, currentPoint.y + move.second)

        if (newPoint.x > maxHeight) {
            return -1
        }

        if (blockedPoints.contains(newPoint)) {
            continue
        } else {
            sandStack.add(newPoint)
            return 0
        }
    }

    return 1
}
