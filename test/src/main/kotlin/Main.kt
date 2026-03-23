package com.samanmiran.fuzz

import com.samanmiran.fuzz.annotation.Fuzzy

@Fuzzy
data class TestClass(
    val a: Boolean,
    val b: Boolean
)

@Fuzzy
data class TestClassB(
    val testClass: TestClass
)

fun main() {
    val testClass1 = TestClass(true, false)
    val testClass2 = fuzzyTestClass(false)
    val testClass3 = fuzzyTestClass()
    val testClassB = fuzzyTestClassB()
    println(testClass1)
    println(testClass2)
    println(testClass3)
    println(testClassB)
}