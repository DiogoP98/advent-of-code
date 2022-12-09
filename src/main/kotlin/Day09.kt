import kotlin.math.abs
import kotlin.math.sign

fun main() {
    fun part1(input: List<String>): Int {
        var tailCurrentPosition = Pair(0, 0)
        val ropePositions = mutableSetOf(tailCurrentPosition)

        executeMotions(input) { headCurrentPosition ->
            if (tailCurrentPosition.touching(headCurrentPosition)) {
                return@executeMotions
            }

            tailCurrentPosition = tailCurrentPosition.closeUp(headCurrentPosition)

            ropePositions.add(tailCurrentPosition)
        }

        return ropePositions.size
    }

    fun part2(input: List<String>): Int {
        val tailCurrentPosition = MutableList(9) { Pair(0, 0) }
        val ropePositions = mutableSetOf(Pair(0, 0))

        executeMotions(input) { headCurrentPosition ->
            tailCurrentPosition.forEachIndexed { index, position ->
                if (index == 0) {
                    if (position.touching(headCurrentPosition)) {
                        return@executeMotions
                    }

                    tailCurrentPosition[index] = position.closeUp(headCurrentPosition)
                } else {
                    with(tailCurrentPosition[index - 1]) {
                        if (!position.touching(this)) {
                            tailCurrentPosition[index] = position.closeUp(this)
                        }
                    }
                }
            }

            ropePositions.add(tailCurrentPosition[8])
        }

        return ropePositions.size
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 88)
    check(part2(testInput) == 36)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}

private fun executeMotions(input: List<String>, moveTail: (Pair<Int, Int>) -> Unit) {
    val directionMapping = mapOf(
        'R' to Pair(0, 1),
        'L' to Pair(0, -1),
        'U' to Pair(-1, 0),
        'D' to Pair(1, 0)
    )

    var headCurrentPosition = Pair(0, 0)

    input.forEach { action ->
        val direction = directionMapping.getValue(action[0])
        val steps = action.getValuesMatchingRegex("\\d+".toRegex())[0].toInt()

        repeat(steps) {
            headCurrentPosition = headCurrentPosition.move(direction)

            moveTail(headCurrentPosition)
        }
    }
}

private fun Pair<Int, Int>.move(direction: Pair<Int, Int>) = Pair(first + direction.first, second + direction.second)

private fun Pair<Int, Int>.touching(other: Pair<Int, Int>) =
    abs(first - other.first) <= 1 && abs(second - other.second) <= 1

private fun Pair<Int, Int>.closeUp(other: Pair<Int, Int>) =
    this.copy(first + (other.first - first).sign * 1, second + (other.second - second).sign * 1)
