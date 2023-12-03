fun main() {
    fun part1(input: List<String>): Int {
        var total = 0
        val numberLocations = mutableMapOf<Int, MutableMap<List<Int>, Int>>()
        val symbolLocations = mutableListOf<Pair<Int, Int>>()
        input.forEachIndexed { index, line ->
            var currentIndexOf = 0
            val stringParts = line.split(".")
            val y = index
            stringParts.forEachIndexed { indexVal, part ->
                if(part.isNotBlank()) {
                    val currentX = line.indexOf(part, currentIndexOf)
                    if(part.length == 1 && part.toIntOrNull() == null) {
                        symbolLocations.add(Pair(y, currentX))
                    } else {
                        var specialChars = part.filterNot { it.isDigit() }.split("").map { it.trim() }.filter { it.isNotBlank() }
                        if(specialChars.isNotEmpty()) {
                            var modifiedPart = part
                            specialChars.forEach { specialChar ->
                                val specialCharIndex = part.indexOf(specialChar)
                                symbolLocations.add(Pair(y, currentX + specialCharIndex))
                                modifiedPart = modifiedPart.replace(specialChar, " ")
                            }
                            modifiedPart.split(" ").forEach {number ->
                                total += number.toIntOrNull() ?: 0
                            }
                        } else {
                            val value = part.toInt()
                            val range = currentX..(currentX + part.length -1)
                            val innerMap = numberLocations.getOrPut(y, { mutableMapOf<List<Int>, Int>() } )
                            innerMap.putIfAbsent(range.toList(), value)
                        }
                    }
                    currentIndexOf  = currentX + part.length
                }
            }
        }
        symbolLocations.forEach { (y, x) ->
            ((y-1) .. (y+1)).forEach {
                if(it >= 0 && it < input.size) {
                    val row = numberLocations[it]
                    if(row != null) {
                        val toRemove = mutableListOf<List<Int>>()
                        row.forEach { ( range, number) ->
                            if(x in range || (x + 1) in range || (x - 1) in range) {
                                total += number
                                toRemove.add(range)
                            }
                        }
                        toRemove.forEach { range ->
                            row.remove(range)
                        }
                    }
                }
            }
        }
        return total
    }

    fun part2(input: List<String>): Int {
        var total = 0
        val numberLocations = mutableMapOf<Int, MutableMap<List<Int>, Int>>()
        val symbolLocations = mutableListOf<Pair<Int, Int>>()
        input.forEachIndexed { index, line ->
            var currentIndexOf = 0
            val stringParts = line.split(".")
            val y = index
            stringParts.forEachIndexed { indexVal, part ->
                if(part.isNotBlank()) {
                    val currentX = line.indexOf(part, currentIndexOf)
                    if(part.length == 1 && part == "*") {
                        symbolLocations.add(Pair(y, currentX))
                    } else {
                        var specialChars = part.filterNot { it.isDigit() }.split("").map { it.trim() }.filter { it.isNotBlank() }
                        if(specialChars.isNotEmpty()) {
                            var modifiedPart = part
                            specialChars.forEach { specialChar ->
                                val specialCharIndex = part.indexOf(specialChar)
                                if(specialChar == "*") {
                                    symbolLocations.add(Pair(y, currentX + specialCharIndex))
                                }
                                modifiedPart = modifiedPart.replace(specialChar, " ").trim()
                            }
                            modifiedPart.split(" ").filter { it.isNotBlank() }.forEach {number ->
                                val value = number.toInt()
                                val range = currentX..(currentX + part.indexOf(number) + number.length -1)
                                val innerMap = numberLocations.getOrPut(y, { mutableMapOf<List<Int>, Int>() } )
                                innerMap.putIfAbsent(range.toList(), value)
                            }
                        } else {
                            val value = part.toInt()
                            val range = currentX..(currentX + part.length -1)
                            val innerMap = numberLocations.getOrPut(y, { mutableMapOf<List<Int>, Int>() } )
                            innerMap.putIfAbsent(range.toList(), value)
                        }
                    }
                    currentIndexOf  = currentX + part.length
                }
            }
        }
        symbolLocations.forEach { (y, x) ->
            val adjacentNumbers = mutableListOf<Int>()
            ((y-1) .. (y+1)).forEach {
                if(it >= 0 && it < input.size) {
                    val row = numberLocations[it]
                    if(row != null) {
                        row.forEach { ( range, number) ->
                            if(x in range || (x + 1) in range || (x - 1) in range) {
                                adjacentNumbers.add(number)
                            }
                        }
                    }
                }
            }
            var product = 0
            if(adjacentNumbers.size > 1) {
                product = adjacentNumbers.reduce { acc, i -> acc * i }
            }
            total += product
        }
        return total
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
//2278
//67953
