import java.util.LinkedList
import java.util.Queue

fun main() {
    fun part1(input: List<String>): Int {
        val height = input.size
        val width = input[0].length

        val area = getArea(input, height, width)
        val startingPoints = getPoints(input, listOf('S'))
        val destinationPoint = getPoints(input, listOf('E'))[0]

        return getMinimumSteps(area, startingPoints, destinationPoint, height, width)
    }

    fun part2(input: List<String>): Int {
        val height = input.size
        val width = input[0].length

        val area = getArea(input, height, width)
        val startingPoints = getPoints(input, listOf('S', 'a'))
        val destinationPoint = getPoints(input, listOf('E'))[0]

        return getMinimumSteps(area, startingPoints, destinationPoint, height, width)
    }

    val testInput = readInput("Day12_test")
    check(part1(testInput) == 31)
    check(part2(testInput) == 29)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}

private fun getArea(input: List<String>, height: Int, width: Int): List<List<Int>> {
    val area = MutableList(height) { MutableList(width) { 0 } }

    input.forEachIndexed { rowIndex, line ->
        line.forEachIndexed { columnIndex, square ->
            if (square == 'S') {
                area[rowIndex][columnIndex] = 0
            } else if (square == 'E') {
                area[rowIndex][columnIndex] = 'z'.code - 'a'.code
            } else {
                area[rowIndex][columnIndex] = square.code - 'a'.code
            }
        }
    }

    return area
}

private fun getPoints(input: List<String>, identifiers: List<Char>): List<Pair<Int, Int>> {
    val listOfPoints = mutableListOf<Pair<Int, Int>>()

    input.forEachIndexed { rowIndex, line ->
        line.forEachIndexed { columnIndex, square ->
            if (square in identifiers) {
                listOfPoints.add(Pair(rowIndex, columnIndex))
            }
        }
    }

    return listOfPoints
}

private fun getMinimumSteps(
    area: List<List<Int>>,
    startingPoint: List<Pair<Int, Int>>,
    destinationPoint: Pair<Int, Int>,
    height: Int,
    width: Int
): Int {
    val steps: Queue<Pair<Int, Pair<Int, Int>>> = LinkedList()
    val directions = listOf(Pair(0, 1), Pair(0, -1), Pair(1, 0), Pair(-1, 0))
    val visitedSteps = MutableList(height) { MutableList(width) { false } }

    startingPoint.forEach {
        steps.add(Pair(0, it))
    }

    while (steps.peek() != null) {
        val (stepCount, currentStep) = steps.remove()

        if (currentStep == destinationPoint) {
            return stepCount
        }

        if (visitedSteps[currentStep.first][currentStep.second]) {
            continue
        }

        visitedSteps[currentStep.first][currentStep.second] = true

        directions.forEach { move ->
            if (!validMove(area, currentStep, move, height, width)) {
                return@forEach
            }

            steps.add(Pair(stepCount + 1, Pair(currentStep.first + move.first, currentStep.second + move.second)))
        }
    }

    return -1
}

private fun validMove(area: List<List<Int>>, currentStep: Pair<Int, Int>, move: Pair<Int, Int>, height: Int, width: Int): Boolean {
    val destinationStep = Pair(currentStep.first + move.first, currentStep.second + move.second)

    return isWithinBoundaries(destinationStep, height, width) && isAtMostOneHigher(area, currentStep, destinationStep)
}

private fun isWithinBoundaries(step: Pair<Int, Int>, height: Int, width: Int) = step.first in 0 until height && step.second in 0 until width

private fun isAtMostOneHigher(area: List<List<Int>>, originStep: Pair<Int, Int>, destinationStep: Pair<Int, Int>) =
    area[destinationStep.first][destinationStep.second] <= area[originStep.first][originStep.second] + 1
