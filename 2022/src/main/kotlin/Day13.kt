import kotlin.math.min

fun main() {
    fun part1(input: List<String>) =
        input.filter { !it.isEmpty() }.chunked(2).foldIndexed(0) { index, sum, packetPair ->
            val (leftPacket, rightPacket) = packetPair.map { retrievePacket(it, 1).second }
            sum + if (pairInRightOrder(leftPacket, rightPacket) > 0) index + 1 else 0
        }

    fun part2(input: List<String>): Int {
        val dividerPackets = listOf("[[2]]", "[[6]]")
        val packets = (input.filter { !it.isEmpty() } + dividerPackets).map { retrievePacket(it, 1).second }.toMutableList()

        packets.sortWith { p1, p2 -> pairInRightOrder(p2, p1) }

        return packets
            .withIndex()
            .filter { it.value.toString() in dividerPackets }
            .fold(1) { acc, elem -> acc * (elem.index + 1) }
    }

    val testInput = readInput("Day13_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 140)

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}

private fun retrievePacket(line: String, currentIndex: Int): Pair<Int, List<Any>> {
    val currentList = mutableListOf<Any>()
    var index = currentIndex

    while (index < line.length && line[index] != ']') {
        if (line[index].isDigit()) {
            var currentValue = 0

            while (line[index].isDigit()) {
                currentValue = currentValue * 10 + line[index].digitToInt()
                index += 1
            }

            currentList.add(currentValue)
        } else if (line[index] == '[') {
            val (nestedIndex, nestedList) = retrievePacket(line, index + 1)
            currentList.add(nestedList)

            index = nestedIndex
        } else {
            index += 1
        }
    }

    return Pair(index + 1, currentList)
}

private fun pairInRightOrder(leftPacket: List<*>, rightPacket: List<*>): Int {
    val leftSize = leftPacket.size
    val rightSize = rightPacket.size

    for (index in 0 until min(leftSize, rightSize)) {
        val checkResult = if (leftPacket[index] is Int) {
            if (rightPacket[index] is Int) {
                rightPacket[index] as Int - leftPacket[index] as Int
            } else {
                pairInRightOrder(listOf(leftPacket[index]) as List<*>, rightPacket[index] as List<*>)
            }
        } else {
            if (rightPacket[index] is Int) {
                pairInRightOrder(leftPacket[index] as List<*>, listOf(rightPacket[index]) as List<*>)
            } else {
                pairInRightOrder(leftPacket[index] as List<*>, rightPacket[index] as List<*>)
            }
        }

        if (checkResult != 0) {
            return checkResult
        }
    }

    return rightSize - leftSize
}
