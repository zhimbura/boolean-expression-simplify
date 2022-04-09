package expressions

import expressions.dto.AbstractCondition
import expressions.dto.Condition
import kotlin.math.pow

class SimplerBooleanExpression {

    fun simplify(condition: AbstractCondition): AbstractCondition {
        val allCandidates = condition.getAllCandidates().toList()
        val uniqCandidates = allCandidates.toSet()
        if (allCandidates.size == uniqCandidates.size) {
            return condition
        }
        TODO("Not implemented")
    }

    fun getBooleanTable(condition: AbstractCondition, conditions: List<Condition>): MutableMap<ArrayList<Int>, Int> {
        val countRow = 1.0.pow(conditions.size).toInt()
        val table: MutableMap<ArrayList<Int>, Int> = mutableMapOf()
        val row: List<Int> = List(conditions.size) { 0 }
        var rowIndex: Int = conditions.size - 1
        for (i in 0..countRow) {
            if (rowIndex == -1) rowIndex = conditions.size - 1
            if (row[rowIndex] == 1)
            // 000
            // 001
            // 010
            // 011

            for (j in conditions.size - 1 downTo 0) {

            }
        }

        return table
    }


}