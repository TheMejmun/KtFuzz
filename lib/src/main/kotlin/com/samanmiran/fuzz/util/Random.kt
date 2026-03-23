package com.samanmiran.fuzz.util

import kotlin.random.Random
import kotlin.random.nextUInt
import kotlin.random.nextULong

object Random {
    fun itemCount(): Int = Random.nextInt(8)

    fun boolean(): Boolean = Random.nextBoolean()

    fun byte(): Byte = Random.nextInt().toByte()
    fun short(): Short = Random.nextInt().toShort()
    fun int(): Int = Random.nextInt()
    fun long(): Long = Random.nextLong()

    fun uByte(): UByte = Random.nextUInt().toUByte()
    fun uShort(): UShort = Random.nextUInt().toUShort()
    fun uInt(): UInt = Random.nextUInt()
    fun uLong(): ULong = Random.nextULong()

    fun float(): Float = Random.nextFloat()
    fun double(): Double = Random.nextDouble()

    private val chars: CharArray = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray()
    fun char(): Char = chars.random()
    fun string(): String = Random.nextBytes(8).toHexString()
}