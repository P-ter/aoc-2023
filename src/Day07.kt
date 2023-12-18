
fun main() {
    data class Hand(val card: List<Char>, val bet: Int, val rank: Int = 0)

    fun rankHand(cards: List<Char>): Int {
        val groupedCards = cards.groupBy { it }.map { it.key to it.value.size }
        if(groupedCards.size == 1) {
            return 7
        }
        if(groupedCards.size == 2) {
            return if(groupedCards.any { it.second == 4 }) 6 else 5
        }
        if(groupedCards.size == 3) {
            return if(groupedCards.any { it.second == 3 }) 4 else 3
        }
        if(groupedCards.size == 4) {
            return 2
        }
        return 1
    }

    fun Char.toCardValue(): Int {
        return when(this) {
            'A' -> 14
            'K' -> 13
            'Q' -> 12
            'J' -> 11
            'T' -> 10
            else -> this.toString().toInt()
        }
    }

    fun compareHands(a: Hand, b: Hand): Int {
        if (a.rank != b.rank) return a.rank.compareTo(b.rank)
        return a.card.zip(b.card).map { it.first.toCardValue().compareTo(it.second.toCardValue()) }.firstOrNull { it != 0 } ?: 0
    }

    fun part1(input: List<String>): Int {
        val sortedHands = input.map { it.split(" ") }
            .map { Hand(it[0].toList(), it[1].toInt(), rankHand(it[0].toList())) }
            .also { println(it) }
            .groupBy { it.rank }
            .toSortedMap { a, b -> b.compareTo(a) }
            .map {
                it.value.sortedWith { a, b -> -compareHands(a, b) }
            }
            .flatten()
            .also { println(it) }
        val total = sortedHands.foldIndexed(0) { index, acc, hand -> acc + hand.bet*(sortedHands.size - index) }
        println(total)
        return total
    }

    fun rankHand2(cards: List<Char>): Int {
        val groupedCards = cards.groupBy { it }.map { it.key to it.value.size }.toMap().toMutableMap()
        if(groupedCards.containsKey('J') && groupedCards.size > 1) {
            val numOfJ = groupedCards.getValue('J')
            groupedCards.remove('J')
            groupedCards.maxBy { it.value }
                .let { groupedCards[it.key] = it.value + numOfJ }
        }

        if(groupedCards.size == 1) {
            return 7
        }
        if(groupedCards.size == 2) {
            return if(groupedCards.any { it.value == 4 }) 6 else 5
        }
        if(groupedCards.size == 3) {
            return if(groupedCards.any { it.value == 3 }) 4 else 3
        }
        if(groupedCards.size == 4) {
            return 2
        }
        return 1
    }

    fun Char.toCardValue2(): Int {
        return when(this) {
            'A' -> 14
            'K' -> 13
            'Q' -> 12
            'J' -> 1
            'T' -> 10
            else -> this.toString().toInt()
        }
    }

    fun compareHands2(a: Hand, b: Hand): Int {
        if (a.rank != b.rank) return a.rank.compareTo(b.rank)
        return a.card.zip(b.card).map { it.first.toCardValue2().compareTo(it.second.toCardValue2()) }.firstOrNull { it != 0 } ?: 0
    }

    fun part2(input: List<String>): Long {
        val sortedHands = input.map { it.split(" ") }
            .map { Hand(it[0].toList(), it[1].toInt(), rankHand2(it[0].toList())) }
            .groupBy { it.rank }
            .toSortedMap { a, b -> b.compareTo(a) }
            .map {
                it.value.sortedWith { a, b -> -compareHands2(a, b) }
            }
            .flatten()
            .onEach { line -> println(line) }
        val total = sortedHands.foldIndexed(0L) { index, acc, hand -> acc + hand.bet*(sortedHands.size - index) }
        println(total)
        return total
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")

//    check(part1(testInput) == 6440)
    check(part2(testInput) == 5905L)
    val input = readInput("Day07")
//    part1(input).println()
    part2(input).println() //246499492
}
