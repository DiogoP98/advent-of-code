import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Int =
        getAssignments(input) { assignment1, assignment2 ->
            assignment1.isFullyContainedIn(assignment2) || assignment2.isFullyContainedIn(assignment1)
        }

    fun part2(input: List<String>): Int =
        getAssignments(input) { assignment1, assignment2 ->
            areOverlapping(assignment1, assignment2)
        }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

private fun getAssignments(
    input: List<String>,
    overlap: (assignment1: Pair<Int, Int>, assignment2: Pair<Int, Int>) -> Boolean
) =
    input.fold(0) { fullyContainedPairs, pair ->
        fullyContainedPairs + pair.split(",").let { assignments ->
            val assignment1 = getAssignedRange(assignments[0])
            val assignment2 = getAssignedRange(assignments[1])

            if (overlap(assignment1, assignment2)) {
                1
            } else {
                0
            }
        }
    }

private fun getAssignedRange(assignment: String) =
    assignment.split("-").map { it.toInt() }.zipWithNext().first()

private fun Pair<Int, Int>.isFullyContainedIn(other: Pair<Int, Int>) =
    other.first <= first && other.second >= second

private fun areOverlapping(assignment1: Pair<Int, Int>, assignment2: Pair<Int, Int>) =
    min(assignment1.second, assignment2.second) >= max(assignment1.first, assignment2.first)
