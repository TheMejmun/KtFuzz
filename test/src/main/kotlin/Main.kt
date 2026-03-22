package com.samanmiran.fuzz

import com.samanmiran.fuzz.annotation.Fuzzy

@Fuzzy
class TestClass(
    val name: String,
    val id: Int
)

fun main() {
    val testClass1 = TestClass("Saman", 0)
    val testClass2 = fuzzyTestClass("Saman", 0)
}