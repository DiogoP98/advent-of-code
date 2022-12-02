fun main() {
    val WIN_SCORE = 6
    val DRAW_SCORE = 3

    val opponentPlayMapping = mapOf<String, String>(
        "A" to "rock",
        "B" to "paper",
        "C" to "scissors"
    )
    val playerPlayMapping = mapOf<String, String>(
        "X" to "rock",
        "Y" to "paper",
        "Z" to "scissors"
    )
    val winPlay = mapOf<String, String>(
        "scissors" to "rock",
        "paper" to "scissors",
        "rock" to "paper"
    )
    val playScores = mapOf<String, Int>(
        "rock" to 1,
        "paper" to 2,
        "scissors" to 3
    )

    fun part1(input: List<String>): Int =
        input.fold(0) { sum, round ->
            sum + round.split(" ").let { roundPlays ->
                val opponentPlay = opponentPlayMapping.getValue(roundPlays[0])
                val playerPlay = playerPlayMapping.getValue(roundPlays[1])

                if (opponentPlay == playerPlay) {
                    DRAW_SCORE + playScores.getValue(playerPlay)
                } else if (playerPlay == winPlay.getValue(opponentPlay)) {
                    WIN_SCORE + playScores.getValue(playerPlay)
                } else {
                    playScores.getValue(playerPlay)
                }
            }
        }

    fun part2(input: List<String>): Int =
        input.fold(0) { sum, round ->
            sum + round.split(" ").let { roundPlays ->
                val opponentPlay = opponentPlayMapping.getValue(roundPlays[0])
                val strategy = roundPlays[1]

                if (strategy == "Y") {
                    DRAW_SCORE + playScores.getValue(opponentPlay)
                } else if (strategy == "Z") {
                    WIN_SCORE + playScores.getValue(winPlay.getValue(opponentPlay))
                } else {
                    playScores.getValue(winPlay.getValue(winPlay.getValue(opponentPlay)))
                }
            }
        }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
