fun main() {
    fun part1(input: List<String>): Int {
        var total = 0
        input.forEach { line ->
            var isOkay = true
            val (id, game) = line.split(": ")
            val gameId = id.split(" ")[1]
            val set = game.split("; ")
            set.forEach { combo ->
                var blueNum = 0
                var greenNum = 0
                var redNum = 0
                combo.split(", ").forEach { cube ->
                    val (num, color) = cube.split(" ")
                    when (color) {
                        "blue" -> blueNum += num.toInt()
                        "green" -> greenNum += num.toInt()
                        "red" -> redNum += num.toInt()
                    }
                }
                if(blueNum > 14 || greenNum > 13 || redNum > 12) {
                    isOkay = false
                }
            }
            if(isOkay) {
                total += gameId.toInt()
            }

        }
        return total
    }

    fun part2(input: List<String>): Int {
        var total = 0
        input.forEach { line ->
            val (_, listOfCubes) = line.split(": ")
            val set = listOfCubes.split("; ")
            val redList = mutableListOf<Int>()
            val blueList = mutableListOf<Int>()
            val greenList = mutableListOf<Int>()
            set.forEach { combo ->
                combo.split(", ").forEach { cube ->
                    val (num, color) = cube.trim().split(" ")
                    when (color) {
                        "blue" -> blueList.add(num.toInt())
                        "green" -> greenList.add(num.toInt())
                        "red" -> redList.add(num.toInt())
                    }
                }
            }
            val result = redList.max() * blueList.max() * greenList.max()
            total += result

        }
        return total
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
//2278
//67953
