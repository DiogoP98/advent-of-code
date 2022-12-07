import java.util.Stack

fun main() {
    fun part1(input: List<String>): Int {
        val directories = retrieveDirectoryMapping(input)

        val directoriesSizes = mutableMapOf<String, Int>()
        calculateDirectorySize(directories, "/", directoriesSizes)

        return directoriesSizes.values.filter { it <= 100000 }.sum()
    }

    fun part2(input: List<String>): Int {
        val directories = retrieveDirectoryMapping(input)

        val directoriesSizes = mutableMapOf<String, Int>()
        calculateDirectorySize(directories, "/", directoriesSizes)

        val spaceLeft = 70000000 - directoriesSizes.getValue("/")
        val neededSpace = 30000000 - spaceLeft

        return directoriesSizes.values.filter { it >= neededSpace }.min()
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

private fun retrieveDirectoryMapping(input: List<String>): Map<String, Pair<Int, String>> {
    val currentDir = Stack<String>()
    var currentDirPath = ""

    return mutableMapOf<String, Pair<Int, String>>().apply {
        input.forEach { line ->
            if (line == "ls") {
                return@forEach
            }

            if (line.startsWith("$")) {
                val fullCommand = line.getValuesMatchingRegex("[\\w/\\d{..}]+".toRegex())
                val command = fullCommand[0]

                if (command == "cd") {
                    val path = fullCommand[1]

                    currentDirPath = updateCurrentDir(currentDir, currentDirPath, path)
                }
            } else {
                this[currentDirPath] = updateCurrentDirContent(this.getOrDefault(currentDirPath, Pair(0, "")), line)
            }
        }
    }
}

private fun updateCurrentDir(currentDir: Stack<String>, currentDirPath: String, path: String): String {
    if (path == "..") {
        currentDir.pop()

        return currentDirPath.split("|").dropLast(1).joinToString(separator = "|")
    } else {
        currentDir.push(path)

        return if (currentDirPath.isEmpty()) path else "$currentDirPath|$path"
    }
}

private fun updateCurrentDirContent(currentDirContent: Pair<Int, String>, contentLine: String): Pair<Int, String> {
    if (contentLine.startsWith("dir")) {
        val dir = contentLine.getValuesMatchingRegex("[\\w]+".toRegex())[1]

        return currentDirContent.copy(second = "${currentDirContent.second}|$dir")
    } else {
        val size = contentLine.getValuesMatchingRegex("\\d+".toRegex())[0].toInt()

        return currentDirContent.copy(first = currentDirContent.first + size)
    }
}

private fun calculateDirectorySize(directories: Map<String, Pair<Int, String>>, path: String, directoriesSizes: MutableMap<String, Int>): Int =
    with(directories.getValue(path)) {
        second.split("|").drop(1).fold(first) { acc, value ->
            acc + calculateDirectorySize(directories, "$path|$value", directoriesSizes)
        }.also { size ->
            directoriesSizes[path] = size
        }
    }
