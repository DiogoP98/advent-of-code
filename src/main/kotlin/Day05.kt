import java.util.ArrayDeque

fun main() {
    fun part1(input: List<String>): String =
        getTopContainers(input) { stacks, numberOfContainers, originStack, destinationStack ->
            repeat(numberOfContainers) {
                if (stacks[originStack].size > 0) {
                    stacks[destinationStack].push(stacks[originStack].pop())
                }
            }
        }

    fun part2(input: List<String>): String =
        getTopContainers(input) { stacks, numberOfContainers, originStack, destinationStack ->
            val containersToInsert = ArrayDeque<String>()

            repeat(numberOfContainers) {
                if (stacks[originStack].size > 0) {
                    containersToInsert.addFirst(stacks[originStack].pop())
                }
            }

            with(containersToInsert) {
                repeat(size) {
                    stacks[destinationStack].push(pop())
                }
            }
        }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

private fun getTopContainers(
    input: List<String>,
    reorganizeContainers: (List<ArrayDeque<String>>, Int, Int, Int) -> Unit
) =
    getStacksLayout(input, reorganizeContainers).let { stacks ->
        stacks.fold("") { topContainers, currentStack ->
            topContainers + currentStack.peek()
        }
    }

private fun getStacksLayout(
    input: List<String>,
    reorganizeContainers: (List<ArrayDeque<String>>, Int, Int, Int) -> Unit
): List<ArrayDeque<String>> {
    val stacks = mutableListOf<ArrayDeque<String>>()

    input.forEach { line ->
        if (line.contains("[A-Z]".toRegex())) {
            fillStacks(stacks, line)
        } else if (line.startsWith("move")) {
            val command = retrieveCommand(line)
            val numberOfContainers = command[0]
            val (originStack, destinationStack) = command.takeLast(2).map { it - 1 }

            reorganizeContainers(stacks, numberOfContainers, originStack, destinationStack)
        }
    }

    return stacks
}

private fun fillStacks(stacks: MutableList<ArrayDeque<String>>, line: String) {
    val containerRegex = "\\w".toRegex()

    val containers = line.chunked(4) { stack -> containerRegex.find(stack)?.value }

    if (stacks.isEmpty()) {
        containers.forEach { _ ->
            stacks.add(ArrayDeque<String>())
        }
    }

    containers.forEachIndexed { index, container ->
        container?.let {
            stacks[index].addLast(container)
        }
    }
}

private fun retrieveCommand(line: String): List<Int> {
    val commandRegex = "\\d+".toRegex()

    return commandRegex.findAll(line).map { it.value.toInt() }.toList()
}
