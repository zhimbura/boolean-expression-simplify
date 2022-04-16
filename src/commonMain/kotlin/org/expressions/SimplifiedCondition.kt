package org.expressions


/**
 * Интерфейс для возможности упрощения выражения
 * */
interface SimplifiedCondition<T> {
    /**
     * Возвращает все базовые операнды условия
     * */
    fun getAllCandidates(): List<SimplifiedCondition<T>>

    /**
     * Возвращает значение установленное с помощью [setValue] с учетом всех условий
     * */
    fun isSatisfiedByValue(): Boolean

    /**
     * Устанавливает значение, которое вернет [isSatisfiedByValue] в базовых операндах
     * @param condition базовый операнд, подразумевается что значение будет установленно для всех операндов с одинаковым [hashCode]
     * @param value значение
     * */
    fun setValue(condition: SimplifiedCondition<T>, value: Boolean)

    /**
     * Создает объединяющий операнд по признаку ИЛИ
     * */
    fun or(conditions: SimplifiedCondition<T>): SimplifiedCondition<T>

    /**
     * Создает объединяющий операнд по признаку И
     * */
    fun and(conditions: SimplifiedCondition<T>): SimplifiedCondition<T>

    /**
     * Создает инвертирующий операнд
     * */
    fun not(): SimplifiedCondition<T>
}