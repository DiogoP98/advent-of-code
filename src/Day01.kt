import java.util.Collections
import java.util.PriorityQueue

fun main() {
    fun part1(input: List<String>): Int {
        var maxCalouries = 0
        var currentElfCalories = 0

        for(line in input) {
            if(line.isEmpty()) {
                maxCalouries = maxOf(maxCalouries, currentElfCalories)
                currentElfCalories = 0
                continue
            }

            currentElfCalories += line.toInt()
        }

        if(currentElfCalories != 0) {
            maxCalouries = maxOf(maxCalouries, currentElfCalories)
        }

        return maxCalouries
    }

    fun part2(input: List<String>): Int {
        var currentElfCalories = 0
        val calories = PriorityQueue<Int>(Collections.reverseOrder())

        for(line in input) {
            if(line.isEmpty()) {
                calories.add(currentElfCalories)
                currentElfCalories = 0
                continue
            }

            currentElfCalories += line.toInt()
        }

        if(currentElfCalories != 0) {
            calories.add(currentElfCalories)
        }

        var totalCalories = 0
        var numberOfElfs = 3
        while(calories.size > 0 && numberOfElfs > 0) {
            totalCalories = totalCalories + calories.poll()
            numberOfElfs = numberOfElfs - 1
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
