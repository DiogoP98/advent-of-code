class Monkey(
    val items: MutableList<Long>,
    val operation: (Long) -> Long,
    val divisibleByTest: Long,
    val testSuccessMonkey: Int,
    val testFailMonkey: Int,
) {
    fun inspectItems(worryLevelDivisor: Long): List<Pair<Long, Int>> =
        items.fold(emptyList<Pair<Long, Int>>()) { list, item ->
            val worryLevel = operation(item) / worryLevelDivisor
            val destinationMonkey = if (worryLevel % divisibleByTest == 0L) testSuccessMonkey else testFailMonkey

            list + listOf(Pair(worryLevel, destinationMonkey))
        }.also {
            items.clear()
        }

    fun receiveItem(item: Long) = items.add(item)
}

fun main() {
    fun part1(input: List<String>): Long {
        val monkeys = getStartingState(input)

        return getItemChecksPerMonkey(monkeys, 20, 3L) { worryLevel -> worryLevel }
            .sortedDescending()
            .take(2)
            .reduce { acc, value -> acc * value }
    }

    fun part2(input: List<String>): Long {
        val monkeys = getStartingState(input)
        // LCM between prime numbers if the product of thw two numbers
        val lcm = monkeys.fold(1L) { acc, monkey ->
            acc * monkey.divisibleByTest
        }

        return getItemChecksPerMonkey(monkeys, 10000, 1L) { worryLevel -> worryLevel % lcm }
            .sortedDescending()
            .take(2)
            .reduce { acc, value -> acc * value }
    }

    val testInput = readInput("Day11_test")
    check(part1(testInput) == 10605L)
    check(part2(testInput) == 2713310158)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}

private fun getItemChecksPerMonkey(
    monkeys: List<Monkey>,
    numberOfRounds: Int,
    worryLevelDivisor: Long,
    worryLevelFunction: (Long) -> Long
): MutableList<Long> {
    val itemsChecksPerMonkey = MutableList(monkeys.size) { 0L }

    repeat(numberOfRounds) {
        monkeys.forEachIndexed { currentMonkeyIndex, currentMonkey ->
            currentMonkey.inspectItems(worryLevelDivisor).forEach { (worryLevel, destinationMonkey) ->
                monkeys[destinationMonkey].receiveItem(worryLevelFunction(worryLevel))
                itemsChecksPerMonkey[currentMonkeyIndex] += 1L
            }
        }
    }

    return itemsChecksPerMonkey
}

private fun getStartingState(input: List<String>): List<Monkey> {
    val monkeyList = mutableListOf<Monkey>()
    var currentLineIndex = 0

    while (currentLineIndex < input.size) {
        val currentLine = input[currentLineIndex]

        if (!currentLine.startsWith("Monkey")) {
            currentLineIndex += 1
            continue
        }

        monkeyList.add(
            Monkey(
                items = retrieveItems(input[currentLineIndex + 1]),
                operation = retrieveOperation(input[currentLineIndex + 2]),
                divisibleByTest = retrieveDivisibleBy(input[currentLineIndex + 3]),
                testSuccessMonkey = retrieveCondition(input[currentLineIndex + 4]),
                testFailMonkey = retrieveCondition(input[currentLineIndex + 5]),
            )
        )

        currentLineIndex += 6
    }

    return monkeyList
}

private fun retrieveItems(line: String) =
    line.substringAfter(":").split(",").map { it.trim().toLong() }.toMutableList()

private fun retrieveOperation(line: String): (Long) -> Long {
    val regex = """new = old ([+-\\*/]) ([\w\d]+)""".toRegex()

    val (operator, value) = regex.find(line.substringAfter(":").trim())!!.destructured

    return { old: Long ->
        val operationValue = if (value == "old") old else value.toLong()

        when (operator) {
            "+" -> old + operationValue
            "-" -> old - operationValue
            "*" -> old * operationValue
            else -> old / operationValue
        }
    }
}

private fun retrieveDivisibleBy(line: String) =
    line.substringAfter("by").trim().toLong()

private fun retrieveCondition(line: String) =
    line.substringAfter("monkey").trim().toInt()
