fun main() {
    fun part1(input: List<String>): Int = getStreamMarker(input.first(), 4)

    fun part2(input: List<String>): Int = getStreamMarker(input.first(), 14)

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}

private fun getStreamMarker(datastream: String, streamLength: Int): Int {
    val stream = mutableMapOf<Char, Int>()
    var streamStart = 0

    datastream.forEachIndexed { index, character ->
        if (characterInCurrentStream(stream, streamStart, character)) {
            streamStart = stream.getValue(character) + 1
        }

        stream[character] = index

        if (getStreamLength(streamStart, index) == streamLength) {
            return index + 1
        }
    }

    return -1
}

private fun characterInCurrentStream(stream: Map<Char, Int>, streamStart: Int, character: Char) =
    stream.containsKey(character) && stream.getValue(character) >= streamStart

private fun getStreamLength(streamStart: Int, lastCharacterIndex: Int) =
    lastCharacterIndex - streamStart + 1
