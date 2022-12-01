import java.util.Collections
import java.util.PriorityQueue

fun main() {
    fun getCaloriesPerElf(input: List<String>): List<Int> {
        val caloriesPerElf = input.fold(mutableListOf(mutableListOf<Int>())) { acc, calories ->
            if (calories.isEmpty()) {
                acc.add(mutableListOf<Int>())
            } else {
                acc.last().add(calories.toInt())
            }

            acc
        }

        return caloriesPerElf.map { it.sum() }
    }

    fun part1(input: List<String>): Int {
        val caloriesPerElf = getCaloriesPerElf(input)

        return caloriesPerElf.max()
    }

    fun part2(input: List<String>): Int {
        val calories = PriorityQueue<Int>(Collections.reverseOrder())

        getCaloriesPerElf(input).forEach {
            calories.add(it)
        }

        var totalCalories = 0
        var numberOfElfs = 3
        while (calories.size > 0 && numberOfElfs > 0) {
            totalCalories += calories.poll()
            numberOfElfs -= 1
        }

        return totalCalories
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
