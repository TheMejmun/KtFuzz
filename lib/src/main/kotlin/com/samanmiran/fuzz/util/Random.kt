package com.samanmiran.fuzz.util

import org.w3c.dom.ranges.Range
import kotlin.random.Random
import kotlin.random.nextUInt
import kotlin.random.nextULong

object Random {
    private val CHARS: CharArray = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray()
    private const val MAX_LENGTH = 8

    fun itemCount(min: Int = 0, max: Int = MAX_LENGTH, fixed: Int? = null): Int =
        fixed ?: (min + Random.nextInt(max - min + 1))

    fun boolean(): Boolean = Random.nextBoolean()

    fun byte(min: Byte = Byte.MIN_VALUE, max: Byte = Byte.MAX_VALUE): Byte =
        Random.nextInt(min.toInt(), max.toInt()).toByte()

    fun short(min: Short = Short.MIN_VALUE, max: Short = Short.MAX_VALUE): Short =
        Random.nextInt(min.toInt(), max.toInt()).toShort()

    fun int(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Int =
        Random.nextInt(min, max)

    fun long(min: Long = Long.MIN_VALUE, max: Long = Long.MAX_VALUE): Long =
        Random.nextLong(min, max)

    fun uByte(min: UByte = UByte.MIN_VALUE, max: UByte = UByte.MAX_VALUE): UByte =
        Random.nextUInt(min.toUInt(), max.toUInt()).toUByte()

    fun uShort(min: UShort = UShort.MIN_VALUE, max: UShort = UShort.MAX_VALUE): UShort =
        Random.nextUInt(min.toUInt(), max.toUInt()).toUShort()

    fun uInt(min: UInt = UInt.MIN_VALUE, max: UInt = UInt.MAX_VALUE): UInt =
        Random.nextUInt(min, max)

    fun uLong(min: ULong = ULong.MIN_VALUE, max: ULong = ULong.MAX_VALUE): ULong =
        Random.nextULong(min, max)

    fun float(min: Float = 0.0f, max: Float = 1.0f): Float =
        min + Random.nextFloat() * (max - min)

    fun double(min: Double = 0.0, max: Double = 1.0): Double =
        min + Random.nextDouble() * (max - min)

    fun char(charset: CharArray = CHARS): Char =
        charset.random()

    fun string(
        min: Int = 0,
        max: Int = MAX_LENGTH,
        fixed: Int? = null,
        charset: CharArray = CHARS
    ): String =
        (0..<itemCount(min, max, fixed)).map { char(charset) }.joinToString("")

    fun charSequence(
        min: Int = 0,
        max: Int = MAX_LENGTH,
        fixed: Int? = null,
        charset: CharArray = CHARS,
    ): CharSequence =
        string(min, max, fixed, charset)
}