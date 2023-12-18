
fun main() {

    fun part1(input: List<String>): Int {
        return input.map { line ->
            var numbers = line.toIntList(" ")
            val lastNumberList = mutableListOf(numbers.last())
            var allZero = false
            while (!allZero) {
                numbers = numbers.windowed(2).map { (a, b) ->
                    b - a
                }
                lastNumberList.add(numbers.last())
                allZero = numbers.all { it == 0 }
            }
            lastNumberList.fold(0) { acc, i -> acc + i }
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.map { line ->
            var numbers = line.toIntList(" ").reversed()
            val lastNumberList = mutableListOf(numbers.last())
            var allZero = false
            while (!allZero) {
                numbers = numbers.windowed(2).map { (a, b) ->
                    a-b
                }
                lastNumberList.add(numbers.last())
                allZero = numbers.all { it == 0 }
            }
            lastNumberList.also { it.println() }.foldRight(0) { acc, i -> acc - i }
        }.sum().also { println(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")

//    check(part1(testInput) == 114)
    check(part2(testInput) == 2)
    val input = readInput("Day09")
//    part1(input).println()
    part2(input).println()
}
