import java.util.ArrayDeque

fun main() {
    fun part1(input: List<String>): String {
        val stacks = mutableListOf<ArrayDeque<String>>()
        val containerRegex = "\\w".toRegex()
        val commandRegex = "\\d+".toRegex()

        input.forEach { line ->
            if (line.contains("[")) {
                val containers = line.chunked(4) { stack -> containerRegex.find(stack)?.value }

                if(stacks.isEmpty()) {
                    containers.forEach{
                        stacks.add(ArrayDeque<String>())
                    }
                }

                containers.forEachIndexed{ index, container ->
                    container?.let {
                        stacks[index].offerLast(container)
                    }
                }
            } else if(line.startsWith("move")) {
                val command = commandRegex.findAll(line).map { it.value.toInt() }.toList()
                var numberOfContainers = command[0]
                val originStack = command[1] - 1
                val destinationStack = command[2] - 1

                while(numberOfContainers > 0) {
                    if(stacks[originStack].size > 0) {
                        stacks[destinationStack].push(stacks[originStack].pop())
                    }

                    numberOfContainers -= 1
                }

            }
        }

        return stacks.fold("") { topContainers, currentStack ->
            topContainers + currentStack.peek()
        }
    }
        

    fun part2(input: List<String>): String {
        val stacks = mutableListOf<ArrayDeque<String>>()
        val containerRegex = "\\w".toRegex()
        val commandRegex = "\\d+".toRegex()

        input.forEach { line ->
            if (line.contains("[")) {
                val containers = line.chunked(4) { stack -> containerRegex.find(stack)?.value }

                if(stacks.isEmpty()) {
                    containers.forEach{
                        stacks.add(ArrayDeque<String>())
                    }
                }

                containers.forEachIndexed{ index, container ->
                    container?.let {
                        stacks[index].offerLast(container)
                    }
                }
            } else if(line.startsWith("move")) {
                val command = commandRegex.findAll(line).map { it.value.toInt() }.toList()
                var numberOfContainers = command[0]
                val originStack = command[1] - 1
                val destinationStack = command[2] - 1

                val toInsert = ArrayDeque<String>()

                while(numberOfContainers > 0) {
                    if(stacks[originStack].size > 0) {
                        toInsert.offerFirst(stacks[originStack].pop())
                    }

                    numberOfContainers -= 1
                }

                while(toInsert.size > 0) {
                    stacks[destinationStack].push(toInsert.pop())
                }
            }
        }

        return stacks.fold("") { topContainers, currentStack ->
            topContainers + currentStack.peek()
        }
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
