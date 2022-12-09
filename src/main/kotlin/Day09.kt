import kotlin.math.abs
import kotlin.math.sign

fun main() {
    fun part1(input: List<String>): Int = retrieveUniquePositions(input, 2)

    fun part2(input: List<String>): Int = retrieveUniquePositions(input, 10)

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 88)
    check(part2(testInput) == 36)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}

private fun retrieveUniquePositions(input: List<String>, knots: Int): Int {
    val directionMapping = mapOf(
        'R' to Pair(0, 1),
        'L' to Pair(0, -1),
        'U' to Pair(-1, 0),
        'D' to Pair(1, 0)
    )

    val rope = MutableList(knots) { Pair(0, 0) }
    val head = 0
    val tail = knots - 1
    val ropeTailPositions = mutableSetOf(rope[tail])

    input.forEach { action ->
        val direction = directionMapping.getValue(action[0])
        val steps = action.getValuesMatchingRegex("\\d+".toRegex())[0].toInt()

        repeat(steps) {
            rope[head] = rope[head].move(direction)

            for (position in 1 until knots) {
                val previousKnotPosition = rope[position - 1]
                val currentKnowPosition = rope[position]

                if (currentKnowPosition.touching(previousKnotPosition)) {
                    break
                }

                rope[position] = currentKnowPosition.closeUp(previousKnotPosition)
            }

            ropeTailPositions.add(rope[tail])
        }
    }

    return ropeTailPositions.size
}

private fun Pair<Int, Int>.move(direction: Pair<Int, Int>) = Pair(first + direction.first, second + direction.second)

private fun Pair<Int, Int>.touching(other: Pair<Int, Int>) =
    abs(first - other.first) <= 1 && abs(second - other.second) <= 1

private fun Pair<Int, Int>.closeUp(other: Pair<Int, Int>) =
    this.copy(first + (other.first - first).sign * 1, second + (other.second - second).sign * 1)
