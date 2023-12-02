import java.lang.Integer.max

fun main() {
    fun part1(input: List<String>): Int {
        val (height, width) = getInputSize(input)
        val treeMap = generateMap(input)

        var nonVisibleTrees = 0
        treeMap.forEachIndexed { rowIndex, row ->
            if (rowIndex == 0 || rowIndex == height - 1) {
                return@forEachIndexed
            }

            row.forEachIndexed { columnIndex, treeHeight ->
                getViews(treeMap, rowIndex, columnIndex, height, width).all { direction ->
                    direction.any { it >= treeHeight }
                }.also { nonVisible ->
                    if (nonVisible) {
                        nonVisibleTrees += 1
                    }
                }
            }
        }

        return height * width - nonVisibleTrees
    }

    fun part2(input: List<String>): Int {
        val (height, width) = getInputSize(input)
        val treeMap = generateMap(input)

        return treeMap.foldIndexed(0) { rowIndex, maxScenicScore, row ->
            val rowMaxScenicScore = row.foldIndexed(0) { columnIndex, rowMaxScenicScore, treeHeight ->
                val currentScenicScore = getViews(treeMap, rowIndex, columnIndex, height, width).map { direction ->
                    getDirectionViewingDistance(direction, treeHeight)
                }.reduce { acc, viewingDistance ->
                    acc * viewingDistance
                }

                max(currentScenicScore, rowMaxScenicScore)
            }

            max(maxScenicScore, rowMaxScenicScore)
        }
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

private fun getInputSize(input: List<String>): Pair<Int, Int> =
    Pair(first = input.size, input.getOrNull(0)?.length ?: 0)

private fun generateMap(input: List<String>) =
    input.map { row ->
        row.map { it.digitToInt() }
    }

private fun getViews(treeMap: List<List<Int>>, rowIndex: Int, columnIndex: Int, height: Int, width: Int) =
    listOf(
        (columnIndex - 1 downTo 0).map { treeMap[rowIndex][it] },
        (columnIndex + 1 until width).map { treeMap[rowIndex][it] },
        (rowIndex + 1 until height).map { treeMap[it][columnIndex] },
        (rowIndex - 1 downTo 0).map { treeMap[it][columnIndex] },

    )

private fun getDirectionViewingDistance(direction: List<Int>, treeHeight: Int) =
    direction.indexOfFirst { it >= treeHeight }.let { index ->
        if (index == -1) direction.size else index + 1
    }
