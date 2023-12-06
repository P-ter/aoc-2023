
fun main() {
    data class Race(val time: Int, val bestDistance: Int)

    fun part1(input: List<String>): Int {
        var total = 0
        val timeList = input[0].split(" ").mapNotNull { it.toIntOrNull() }
        val distanceList = input[1].split(" ").mapNotNull { it.toIntOrNull() }
        println(distanceList)
        val races = timeList.mapIndexed { index, time -> Race(time, distanceList[index]) }

        return races.map { race ->
            (1 until race.time).count {speed ->
                speed*(race.time -speed) > race.bestDistance
            }
        }.fold(1) { acc, i -> acc * i }

    }
    data class Race2(val time: Long, val bestDistance: Long)

    fun part2(input: List<String>): Long {

        val time = input[0].filter { it.isDigit() }.toLong()
        val bestDistance = input[1].filter { it.isDigit() }.toLong()
        val race = Race2(time, bestDistance)

        var count: Long = 0
        (1 until race.time).forEach {speed ->
            if(speed*(race.time -speed) > race.bestDistance) {
                count ++
            }
        }
        println(count)
        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")

//    check(part1(testInput) == 288)
    check(part2(testInput) == 71503L)
    val input = readInput("Day06")
//    part1(input).println()
    part2(input).println()
}
