import kotlin.math.max
import kotlin.math.min
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<String>): Long {
        var total = 0
        var sourceList = input[0].toLongList(" ").toMutableList()

        val allSteps = input.findAllWithIndex { it.isBlank() }.windowed(size = 2, partialWindows = true).map {
            if(it.size > 1) {
                input.subList(it[0].first + 2, it[1].first)
            } else {
                //last
                input.subList(it[0].first + 2, input.size)
            }
        }.map{ it.map { line -> line.toLongList(" ")}}
            .forEach {step ->
                println(step)
                val newSourceList = mutableListOf<Long>()
                step.forEach { (desc, source, range) ->
                    val sourceRange = source to (source + range - 1)
                    sourceList.filter { it >= sourceRange.first && it <= sourceRange.second }.forEach { matchSource ->
                        val difference = matchSource - source
                        val matchDesc = desc + difference
                        newSourceList += matchDesc
                        sourceList.remove(matchSource)
                    }
                }
                newSourceList.addAll(sourceList)
                sourceList = newSourceList
            }

        return sourceList.min()
    }

    fun part2(input: List<String>): Long {
        var sourceList = input[0].toLongList(" ").chunked(2).map { it[0] to (it[0] + it[1] -1) }.toMutableList()
        input.findAllWithIndex { it.isBlank() }.windowed(size = 2, partialWindows = true).map {
            if(it.size > 1) {
                input.subList(it[0].first + 2, it[1].first)
            } else {
                input.subList(it[0].first + 2, input.size)
            }
        }.map{ it.map { line -> line.toLongList(" ")}}
            .forEach {step ->
                val newSourceList = mutableListOf<Pair<Long, Long>>()
                step.forEach { (desc, source, range) ->
                    val sourceRange = source to (source + range - 1)
                    sourceList.filter { it.first <= sourceRange.second && it.second >= sourceRange.first }.forEach { matchSource ->
                        val commonRange = max(matchSource.first, sourceRange.first) to min(matchSource.second, sourceRange.second)
                        val difference = commonRange.first - source to commonRange.second - source
                        val matchDesc = desc + difference.first to desc + difference.second
                        if(matchSource.first < commonRange.first) {
                            sourceList += matchSource.first to (commonRange.first - 1)
                        }
                        if (matchSource.second > commonRange.second) {
                            sourceList += (commonRange.second + 1) to matchSource.second
                        }
                        newSourceList += matchDesc
                        sourceList.remove(matchSource)
                    }
                }
                newSourceList.addAll(sourceList)
                sourceList = newSourceList
            }
        return sourceList.flatMap { it.toList() }.min()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")

    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)
//    println("Part 1 test passed")
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println() //77781696 wrong
}
//2278
//67953
