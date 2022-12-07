import java.util.Stack

fun main() {
    fun part1(input: List<String>): Int {
        val currentDir = Stack<String>()
        var currentDirPath = ""
        val directories = mutableMapOf<String, String>()

        var index = 0

        while(index < input.size) {
            val line = input[index]

            if(line.startsWith("$")) {
                val commandRegex = "[\\w/\\d{..}]+".toRegex()

                val fullCommand = commandRegex.findAll(line).map { it.value }.toList()
                val command = fullCommand[0]

                if (command == "cd") {
                    val path = fullCommand[1]
                    if(path == "..") {
                        currentDir.pop()
                        currentDirPath = currentDirPath.split("|").dropLast(1).joinToString(separator = "|")
                    } else {
                        currentDir.push(path)
                        currentDirPath = if(currentDirPath == "") path else "${currentDirPath}|${path}"
                    }

                    index += 1
                } else if(command == "ls") {
                    while(index < input.size - 1) {
                        index += 1

                        if(input[index].startsWith("$")) {
                            break
                        }

                        if(input[index].startsWith("dir")) {
                            val commandRegex = "[\\w]+".toRegex()

                            val lsDir = commandRegex.findAll(input[index]).map { it.value }.toList()[1]

                            directories[currentDirPath] = if(directories.contains(currentDirPath)) directories.getValue(currentDirPath) + "|${lsDir}" else "${lsDir}"
                        } else {
                            val lsRegex = "\\d+".toRegex()

                            val size = lsRegex.findAll(input[index]).map { it.value }.toList()[0]

                            directories[currentDirPath] = if(directories.contains(currentDirPath)) directories.getValue(currentDirPath) + "|${size}" else "${size}"
                        }
                    }
                }
            } else {
                index += 1
            }
        }

        val directoriesSize = mutableMapOf<String, Int>()

        calculateSize(directories, directoriesSize, "/")

        return directoriesSize.values.filter { it <= 100000 }.sum()
    }

    fun part2(input: List<String>): Int {
        val currentDir = Stack<String>()
        var currentDirPath = ""
        val directories = mutableMapOf<String, String>()

        var index = 0

        while(index < input.size) {
            val line = input[index]

            if(line.startsWith("$")) {
                val commandRegex = "[\\w/\\d{..}]+".toRegex()

                val fullCommand = commandRegex.findAll(line).map { it.value }.toList()
                val command = fullCommand[0]

                if (command == "cd") {
                    val path = fullCommand[1]
                    if(path == "..") {
                        currentDir.pop()
                        currentDirPath = currentDirPath.split("|").dropLast(1).joinToString(separator = "|")
                    } else {
                        currentDir.push(path)
                        currentDirPath = if(currentDirPath == "") path else "${currentDirPath}|${path}"
                    }

                    index += 1
                } else if(command == "ls") {
                    while(index < input.size - 1) {
                        index += 1

                        if(input[index].startsWith("$")) {
                            break
                        }

                        if(input[index].startsWith("dir")) {
                            val commandRegex = "[\\w]+".toRegex()

                            val lsDir = commandRegex.findAll(input[index]).map { it.value }.toList()[1]

                            directories[currentDirPath] = if(directories.contains(currentDirPath)) directories.getValue(currentDirPath) + "|${lsDir}" else "${lsDir}"
                        } else {
                            val lsRegex = "\\d+".toRegex()

                            val size = lsRegex.findAll(input[index]).map { it.value }.toList()[0]

                            directories[currentDirPath] = if(directories.contains(currentDirPath)) directories.getValue(currentDirPath) + "|${size}" else "${size}"
                        }
                    }
                }
            } else {
                index += 1
            }
        }

        val directoriesSize = mutableMapOf<String, Int>()

        calculateSize(directories, directoriesSize, "/")

        val spaceLeft = 70000000 - directoriesSize.getValue("/")
        val neededSpace = 30000000 - spaceLeft

        return directoriesSize.values.filter { it >= neededSpace }.min()
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

private fun calculateSize(directories: Map<String, String>, directoriesSize: MutableMap<String, Int>, key: String): Int {
    if(directoriesSize.containsKey(key)) {
         return directoriesSize.getValue(key)
    }

    directories.getValue(key).split("|").forEach {
        if(it.matches("\\d+".toRegex())) {
            directoriesSize[key] = directoriesSize.getOrDefault(key, 0) + it.toInt()
        } else {
            if(directoriesSize.contains("${key}|${it}")) {
                directoriesSize[key] = directoriesSize.getOrDefault(key, 0) + directoriesSize.getValue("${key}|${it}")
            } else {
                directoriesSize[key] = directoriesSize.getOrDefault(key, 0) + calculateSize(directories, directoriesSize, "${key}|${it}")
            }
        }
    }

    return directoriesSize.getValue(key)
}
