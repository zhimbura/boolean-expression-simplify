package expressions

import expressions.dto.*
import kotlin.math.pow

class SimplerBooleanExpression {

    fun simplify(condition: AbstractCondition): AbstractCondition? {
        val uniqCandidates = condition.getAllCandidates().toSet().toList()
        if (uniqCandidates.isEmpty()) {
            return null
        }
        val booleanTable = getBooleanTable(condition, uniqCandidates)
        val groups: MutableMap<Int, ArrayList<List<Int>>> = mutableMapOf()
        for ((key, value) in booleanTable) {
            if (value > 0) {
                val group = groups.getOrPut(key.sumOf { it }) { arrayListOf() }
                group.add(key)
            }
        }
        if (groups.isEmpty()) {
            return AlwaysFalseCondition()
        }

        val allGroups = groups.values
        // Если группа одна конвертируем ее в простое выражение
        if (allGroups.size == 1) {
            val firstGroup = allGroups.first()
            return transformToCondition(firstGroup, uniqCandidates)
        }

        // Если групп несколько, то щем пересечения
        val allMarked: ArrayList<List<Int>> = arrayListOf()
        val prevGroup = allGroups.first()
        for (group in allGroups) {
            if (prevGroup == group) continue
            for (listFirst in prevGroup) {
                for (listSecond in group) {
                    val marked = markDifferent(listFirst, listSecond)
                    if (marked.isNotEmpty()) {
                        allMarked.add(marked)
                    }
                }
            }
        }

        // После того как пересечения между группами найдены, оптимизируем каждый с каждым
        var filtered = allMarked.toList()
        while (true) {
            val newFiltered: ArrayList<List<Int>> = arrayListOf()
            for (i in filtered.indices) {
                for (j in filtered.indices) {
                    if (i == j) continue
                    val marked = markDifferent(filtered[i], filtered[j])
                    if (marked.isNotEmpty()) {
                        newFiltered.add(marked)
                    }
                }
            }
            if (newFiltered.isEmpty()) {
                break
            } else {
                filtered = newFiltered
            }
        }
        var isAllPositive = true
        var isAllNegative = true
        for (i in filtered.indices) {
            for (j in filtered[i].indices) {
                if (filtered[i][j] != 2) {
                    isAllPositive = false
                } else if (filtered[i][j] != 0) {
                    isAllNegative = false
                }
            }
        }
        if (isAllNegative) {
            return AlwaysFalseCondition()
        }
        if (isAllPositive) {
            return AlwaysTrueCondition()
        }
        val result = transformToCondition(filtered, uniqCandidates)
        return if (result != null && result.getAllCandidates().size < condition.getAllCandidates().size) {
            result
        } else {
            condition
        }
    }

    private fun transformToCondition(
        firstGroup: Iterable<List<Int>>,
        uniqCandidates: List<Condition>
    ): AbstractCondition? {
        var result: AbstractCondition? = null
        for (row in firstGroup) {
            val candidates = getTrueCandidates(row, uniqCandidates)
            val andCondition = if (candidates.size == 1) {
                candidates.first()
            } else {
                AndCondition(*candidates.toTypedArray())
            }
            result = result?.or(andCondition) ?: andCondition
        }
        return result
    }

    private fun getTrueCandidates(booleanResult: List<Int>, allCandidates: List<Condition>): List<Condition> {
        val result: ArrayList<Condition> = arrayListOf()
        for (i in booleanResult.indices) {
            if (booleanResult[i] == 1) {
                result.add(allCandidates[i])
            }
        }
        return result
    }

    /**
     * Returns marked array if input arrays have one different
     * example input [0, 0, 0] and [0, 0, 1] return [0, 0, 2]
     */
    private fun markDifferent(inputFirst: List<Int>, inputSecond: List<Int>): List<Int> {
        if (inputFirst.size != inputSecond.size || inputFirst.isEmpty()) return emptyList()
        var diffIndex = -1
        for (i in inputFirst.indices) {
            if (inputFirst[i] != inputSecond[i]) {
                if (diffIndex == -1) {
                    diffIndex = i
                } else {
                    return emptyList()
                }
            }
        }
        return ArrayList(inputFirst).also { it[diffIndex] = 2 }
    }

    fun getBooleanTable(condition: AbstractCondition, conditions: List<Condition>): MutableMap<List<Int>, Int> {
        val countRows = 2.0.pow(conditions.size).toInt() - 1
        val table: MutableMap<List<Int>, Int> = mutableMapOf()
        for (i in 0..countRows) {
            val binaryString = i.toString(2)
            val row = MutableList(conditions.size) { 0 }
            for (charIndex in binaryString.length - 1 downTo 0) {
                if (binaryString[charIndex] == '1') {
                    row[conditions.size - (binaryString.length - charIndex)] = 1
                }
            }
            for (j in conditions.size - 1 downTo 0) {
                condition.setValue(conditions[j], row[j].toBoolean())
            }
            table[row] = condition.isSatisfied().toInt()
        }
        return table
    }
}