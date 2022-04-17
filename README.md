# Minimizer of Boolean Algebra Functions

## Description

Multiplatform library witch can make the expression easier. This library can be used on JVM, JS, Swift.

## How use it

### Classes 

You can use exist classes in  [org.expressions.impl](src/commonMain/kotlin/org/expressions/impl) for create condition and [SimplerBooleanExpression](src/commonMain/kotlin/org/expressions/SimplerBooleanExpression.kt) for doing simle your condition.

For example with using constructor:

```kotlin
import org.expressions.SimplerBooleanExpression
import org.expressions.impl.*

// A && (B || A) -> A
fun main() {
    // Define condition "A && (B || A)"
    val condition = AndCondition(
        Condition("A"),
        OrCondition(
            Condition("B"),
            Condition("A"),
        )
    )
    println(condition.toString()) // (A && (B || A))
    
    // Create instance SimplerBooleanExpression 
    val simpler = SimplerBooleanExpression()
    // Make easier
    val simpled = simpler.simplify(condition)
    println(simpled.toString()) // A
}

```

For example with using methods: 

```kotlin
import org.expressions.SimplerBooleanExpression
import org.expressions.impl.*

// A && (B || A) -> A
fun main() {
    // Define condition "A && (B || A)"
    val condition = Condition("A").and(Condition("B").or(Condition("A")))
    println(condition.toString()) // (A && (B || A))
    
    // Create instance SimplerBooleanExpression 
    val simpler = SimplerBooleanExpression()
    // Make easier
    val simpled = simpler.simplify(condition)
    println(simpled.toString()) // A
}
```



### Custom conditions 

If you want use custom conditions, you need implement interface [SimplifiedCondition](src/commonMain/kotlin/org/expressions/SimplifiedCondition.kt) and then you will could use [SimplerBooleanExpression](src/commonMain/kotlin/org/expressions/SimplerBooleanExpression.kt) similary first code examples.



### TODO

- Publish to maven central and npm
- Make html with parse string condition and make it easier