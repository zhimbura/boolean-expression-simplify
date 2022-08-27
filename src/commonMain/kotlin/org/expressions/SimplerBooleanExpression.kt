package org.expressions

import org.expressions.impl.AlwaysFalseCondition
import org.expressions.impl.AlwaysTrueCondition
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.math.pow

/**
 * The main class that implements the expression simplification logic
 * */
@JsExport
class SimplerBooleanExpression {
    /**
     * Make simpled condition if it is possible
     * @param condition difficult composition condition
     *
     * Example
     *
     * input: (A || B || !B) && A
     *
     * return: A
     * */
    @JsName("simplify")
    fun <T> simplify(condition: ISimplifiedCondition<T>): ISimplifiedCondition<T>? {
        val uniqCandidates = condition.getAllCandidates().distinct()
        if (uniqCandidates.isEmpty()) {
            return AlwaysTrueCondition()
        }
        val groups: Map<Int, List<List<Int>>> = getBooleanTable(condition, uniqCandidates)
            .asSequence()
            .filter { it.value > 0 }
            .map { it.key }
            .groupBy { it.sum() }

        when {
            groups.isEmpty() -> return AlwaysFalseCondition()
            groups.size == 1 -> return transformToCondition(groups.values.first(), uniqCandidates)
        }

        var filtered = getCrossGroups(groups.values)
        do {
            val newFiltered: ArrayList<List<Int>> = arrayListOf()
            filtered.runEachWithEach { item1, item2 ->
                markDifferent(item1, item2)?.let {
                    newFiltered.add(it)
                }
            }
            if (newFiltered.isNotEmpty()) {
                filtered = newFiltered
            }
        } while (newFiltered.isNotEmpty())

        checkConstExpression<T>(filtered)?.let {
            return@simplify it
        }

        return transformToCondition(filtered, uniqCandidates)
            ?.takeIf { it.getAllCandidates().size < condition.getAllCandidates().size }
            ?: condition
    }

    /**
     * Make boolean table and check condition results by row
     * @param condition difficult composite condition (example A || B)
     * @param conditions uniq simple operands (example [A, B])
     *
     * Example
     *
     * input:
     *
     * [condition] = A || B
     *
     * [conditions] = [ A, B ]
     *
     * return => Map
     *
     * 0, 0 => 0
     *
     * 0, 1 => 1
     *
     * 1, 0 => 1
     *
     * 1, 1 => 1
     * */
    fun <T> getBooleanTable(
        condition: ISimplifiedCondition<T>,
        conditions: List<ISimplifiedCondition<T>>
    ): Map<List<Int>, Int> {
        val countRows = 2.0.pow(conditions.size).toInt() - 1
        val table: MutableMap<List<Int>, Int> = mutableMapOf()
        for (i in 0..countRows) {
            val row = i.toBinary().let {
                List(conditions.size - it.size) { 0 } + it
            }
            for (j in conditions.size - 1 downTo 0) {
                condition.setValue(conditions[j], row[j].toBoolean())
            }
            table[row] = condition.isSatisfiedByValue().toInt()
        }
        return table
    }

    /**
     * Check if all operands are true or all operands are false
     * example
     * [filtered] = [ [1, 1, 1] ]
     * returns => [AlwaysTrueCondition]
     * */
    private fun <T> checkConstExpression(filtered: List<List<Int>>): ISimplifiedCondition<T>? {
        var isAllPositive = true
        var isAllNegative = true
        filtered
            .asSequence()
            .flatten()
            .forEach {
                when {
                    it != 2 -> isAllPositive = false
                    it != 0 -> isAllNegative = false
                }
                if (!isAllPositive && !isAllNegative) {
                    return@checkConstExpression null
                }
            }
        return when {
            isAllPositive -> AlwaysTrueCondition()
            isAllNegative -> AlwaysFalseCondition()
            else -> null
        }
    }

    /**
     * Search one different operand result from different group and mark it
     * example
     * [allGroups] = [
     *  [
     *      [0, 0, 0],
     *      [0, 1, 1]
     *  ],
     *  [
     *      [0, 0, 1]
     *  ]
     * ]
     * return => [ [0, 0, 2] ]
     * */
    private fun getCrossGroups(allGroups: Collection<List<List<Int>>>): List<List<Int>> {
        val allMarked: ArrayList<List<Int>> = arrayListOf()
        val prevGroup = allGroups.first()
        for (group in allGroups) {
            if (prevGroup == group) continue
            for (listFirst in prevGroup) {
                for (listSecond in group) {
                    markDifferent(listFirst, listSecond)?.let {
                        allMarked.add(it)
                    }
                }
            }
        }
        return allMarked
    }

    /**
     * Transform and join table to condition
     * example
     * [group] = [[0, 1, 2], [1, 1, 1]]
     * [operands] = [A, B, C]
     * return => (!A & B) || (A && B && C)
     * */
    private fun <T> transformToCondition(
        group: Iterable<List<Int>>,
        operands: List<ISimplifiedCondition<T>>
    ): ISimplifiedCondition<T>? {
        var result: ISimplifiedCondition<T>? = null
        for (row in group) {
            val candidates = getWithoutMarked(row, operands)
            val andCondition = when {
                candidates.isEmpty() -> continue
                candidates.size == 1 -> candidates.first()
                else -> candidates.reduce { acc, simplifiedCondition ->
                    acc.and(simplifiedCondition)
                }
            }
            result = result?.or(andCondition) ?: andCondition
        }
        return result
    }

    /**
     * Returns not market candidates
     * example
     * [row] = [0, 1, 2]
     * [operands] = [A, B, C]
     * return => [!A, B]
     * */
    private fun <T> getWithoutMarked(
        row: List<Int>,
        operands: List<ISimplifiedCondition<T>>
    ): List<ISimplifiedCondition<T>> {
        return row.mapIndexedNotNull { index, item ->
            when (item) {
                1 -> operands[index]
                0 -> operands[index].not()
                else -> null
            }
        }
    }

    /**
     * @return marked array if input arrays have one different
     * example input [0, 0, 0] and [0, 0, 1] return [0, 0, 2] or null if difference more than one
     */
    private fun markDifferent(rowFirst: List<Int>, rowSecond: List<Int>): List<Int>? {
        if (rowFirst.size != rowSecond.size || rowFirst.isEmpty()) return null
        var diffIndex = -1
        for (i in rowFirst.indices) {
            if (rowFirst[i] != rowSecond[i]) {
                if (diffIndex == -1) {
                    diffIndex = i
                } else {
                    return null
                }
            }
        }
        return ArrayList(rowFirst).also { it[diffIndex] = 2 }
    }

    private fun Int.toBoolean() = this != 0
    private fun Boolean.toInt() = if (this) 1 else 0
    private inline fun <T> List<T>.runEachWithEach(lambda: (T, T) -> Unit) {
        val range = indices
        for (i in range) {
            for (j in range) {
                if (i == j) continue
                lambda(this[i], this[j])
            }
        }
    }

    private fun Int.toBinary(): List<Int> {
        return this.toString(2).map { it.digitToInt() }
    }
}