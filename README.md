# boolean-expression-siplify

Мультиплатформенная библиотека позволяющая оптимизировать логические выражения.

Вы можете использовать готовые классы для оптимизации выражений либо реализовать интерфейс [SimplifiedCondition](src/commonMain/kotlin/org/expressions/SimplifiedCondition.kt) и передавать свои классы для упрощения условий.

Пример использования:

```kotlin
import expressions.SimplerBooleanExpression
import expressions.impl.*

val simpler = SimplerBooleanExpression()

// A && (B || A) -> A
val condition = AndCondition(
    Condition("A"),
    OrCondition(
        Condition("B"),
        Condition("A")
    )
)
println(condition) // A && (B || A)
// Оптимизируем
val simpled = simpler.simplify(condition)
println(simpled) // A


// Или можно создать вызовами методов
val condition2 = Condition("A").and(
    Condition("B").or(
        Condition("A")
    )
)
println(condition2) // A && (B || A)
// Оптимизируем
val simpled2 = simpler.simplify(condition2)
println(simpled2) // A

```

TODO: Добавить пример с имплементацией

TODO: Опубликовать в Maven и NPM

TODO: Сделать документацию и опубликовать в pages с рабочим примером на js

TODO: Перевести на английский

TODO: Добавить возможность сериализовать (не уверен что нужно)

TODO: Однако generic не нужен 