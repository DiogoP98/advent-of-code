fun main() {
    fun part1(input: List<String>): Int =
        input.fold(0) { sum, rucksack ->
            val setOfItems = mutableSetOf<Char>()
            val splitIndex = rucksack.length / 2

            for (position in 0 until splitIndex) {
                setOfItems.add(rucksack.get(position))
            }

            var priority = 0

            for (position in splitIndex until rucksack.length) {
                val item = rucksack.get(position)

                if (setOfItems.contains(item)) {
                    priority = getPriority(item)
                    break
                }
            }

            sum + priority
        }

    fun part2(input: List<String>): Int {
        val badges =
            input
                .windowed(size = 3, step = 3)
                .fold(listOf<Char>()) { badges, group ->
                    val items = mutableMapOf<Char, Int>()

                    group.forEach { rucksack ->
                        val distinctItems = rucksack.toList().distinct()

                        distinctItems.forEach { item ->
                            items.put(item, items.getOrDefault(item, 0) + 1)
                        }
                    }

                    val badgeItem = items.filterValues { it == 3 }.keys.first()

                    badges + badgeItem
                }

        return badges.sumOf(::getPriority)
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

private fun getPriority(item: Char): Int =
    if (item.isUpperCase()) item.code - 'A'.code + 27 else item.code - 'a'.code + 1
