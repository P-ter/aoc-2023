fun main() {
    fun part1(input: List<String>): Int {
        var total = 0
        input.forEach {card ->
            val (winningListString, yourListString) = card.split(": ")[1].split(" | ")
            val winningList = winningListString.split(" ").mapNotNull { it.toIntOrNull() }
            val yourList = yourListString.split(" ").mapNotNull { it.toIntOrNull() }
            val numberOfMatches = winningList.intersect(yourList.toSet()).size
            val points = Math.pow(2.0, (numberOfMatches - 1).toDouble()).toInt()
            total += points
        }
        return total
    }

    fun part2(input: List<String>): Int {
        var total = 0
        val cardList = mutableMapOf<Int, Int>()
        input.forEachIndexed { index, card ->
            val (winningListString, yourListString) = card.split(": ")[1].split(" | ")
            val winningList = winningListString.split(" ").mapNotNull { it.toIntOrNull() }
            val yourList = yourListString.split(" ").mapNotNull { it.toIntOrNull() }
            val numberOfMatches = winningList.intersect(yourList.toSet()).size
            val numOfCurrentCard = cardList.getOrDefault(index, 1)
            if(numberOfMatches > 0) {
                (index+1 .. index+numberOfMatches).forEach {
                    val currentCount = cardList.getOrPut(it, { 1 })
                    cardList[it] = currentCount + numOfCurrentCard
                }
            }
            total += numOfCurrentCard
        }
        return total
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
//2278
//67953
