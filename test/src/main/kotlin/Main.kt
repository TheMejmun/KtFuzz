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

@Fuzzy
data class TestClassC(
    val boolean: Boolean,
    val byte: Byte,
    val short: Short,
    val int: Int,
    val long: Long,
    val char: Char,
    val float: Float,
    val double: Double,
    val string: String,
)

@Fuzzy
data class TestClassD(
    val list: List<TestClassA>,
    val set: Set<String>,
    val map: Map<String, TestClassA>,
)


fun main() {
    val testClass1 = TestClassA(true, false)
    val testClass2 = fuzzyTestClassA(false)
    val testClass3 = fuzzyTestClassA()
    println(testClass1)
    println(testClass2)
    println(testClass3)
    println(fuzzyTestClassB())
    println(fuzzyTestClassC())
    println(fuzzyTestClassD())
}