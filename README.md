# boolean-expression-siplify

Мультиплатформенная библиотека позволяющая оптимизировать логические выражения

Пример использования
```kotlin
import expressions.SimplerBooleanExpression
import expressions.dto.*

// A && (B || A) -> A
val condition = AndCondition(
    "A",
    OrCondition(
        "B",
        "A"
    )
)
println(condition) // A && (B || A)
val simpler = SimplerBooleanExpression()
val simpled = simpler.simplify(condition)
println(simpled) // A


// Или можно создать вызовами методов
val condition2 = Condition("A").and(
    Condition("B").or(
        Condition("A")
    )
)
println(condition2) // A && (B || A)


```


TODO: Опубликовать в Maven и NPM

TODO: Причесать код

TODO: Перевести на английский

TODO: Переделать на Generic возможно

TODO: Добавить возможность сериализовать