fun main() {
    fun part1(input: List<String>): Int {
        val commandList = retrieveCommandList(input)

        return commandList.scan(1) { sum, operation ->
            sum + operation
        }.filterIndexed { index, _ ->
            (index - 19) % 40 == 0
        }.foldIndexed(0) { index, sum, value ->
            val cycle = 20 + 40 * index
            sum + cycle * value
        }
    }

    fun part2(input: List<String>): String {
        val commandList = retrieveCommandList(input)

        val crtWidth = 40
        val crtHeight = 6
        val image = MutableList(crtHeight) { MutableList(crtWidth) { '.' } }
        var currentCycle = 0

        commandList.fold(1) { sum, operation ->
            val spriteRange = (sum - 1..sum + 1)
            if (currentCycle % 40 in spriteRange) {
                image[currentCycle / 40][currentCycle % 40] = '#'
            }

            currentCycle += 1
            sum + operation
        }

        return image.joinToString(separator = "\n") {
            it.joinToString(separator = "")
        }
    }

    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)
    check(
        part2(testInput) ==
            "##..##..##..##..##..##..##..##..##..##..\n" +
            "###...###...###...###...###...###...###.\n" +
            "####....####....####....####....####....\n" +
            "#####.....#####.....#####.....#####.....\n" +
            "######......######......######......####\n" +
            "#######.......#######.......#######....."
    )

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}

private fun retrieveCommandList(input: List<String>) =
    input.fold(emptyList<Int>()) { list, line ->
        list + if (line == "noop") {
            listOf(0)
        } else {
            val value = line.split(" ")[1].toInt()
            listOf(0, value)
        }
    }
