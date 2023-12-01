fun main() {
    fun part1(input: List<String>): Int {
        var total = 0
        input.forEach { line ->
            val firstDigit = line.first { it.isDigit() }
            val lastDigit = line.last { it.isDigit() }
            val number = (firstDigit.toString() + lastDigit.toString()).toInt()
            total += number
        }
        return total
    }

    fun part2(input: List<String>): Int {
        var total = 0
        val listMatchers = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "1", "2", "3", "4", "5", "6", "7", "8", "9")
        input.forEach { line ->
            var (_, firstDigit) = line.findAnyOf(listMatchers)!!
            var (_, secondDigit) = line.findLastAnyOf(listMatchers)!!
            firstDigit =
                if (firstDigit.length == 1) firstDigit else (listMatchers.indexOfFirst { firstDigit == it } + 1).toString()
            secondDigit =
                if (secondDigit.length == 1) secondDigit else (listMatchers.indexOfFirst { secondDigit == it } + 1).toString()

            val number = (firstDigit + secondDigit).toInt()
            total += number
        }
        return total
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part2(testInput) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
