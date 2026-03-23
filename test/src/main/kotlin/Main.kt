package com.samanmiran.fuzz

import com.samanmiran.fuzz.annotation.Fuzzy

@Fuzzy
data class TestClassA(
    val a: Boolean,
    val b: Boolean
)

@Fuzzy
data class TestClassB(
    val testClass: TestClassA
)

//@Fuzzy
//data class TestClassC(
//    val i: Int
//)

fun main() {
    val testClass1 = TestClassA(true, false)
    val testClass2 = fuzzyTestClassA(false)
    val testClass3 = fuzzyTestClassA()
    val testClassB = fuzzyTestClassB()
    println(testClass1)
    println(testClass2)
    println(testClass3)
    println(testClassB)
}