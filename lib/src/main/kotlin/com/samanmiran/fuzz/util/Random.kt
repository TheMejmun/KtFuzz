package com.samanmiran.fuzz.util

import kotlin.random.Random
import kotlin.random.nextUInt
import kotlin.random.nextULong

fun randBoolean(): Boolean = Random.nextBoolean()

fun randByte(): Byte = Random.nextInt().toByte()
fun randShort(): Short = Random.nextInt().toShort()
fun randInt(): Int = Random.nextInt()
fun randLong(): Long = Random.nextLong()

fun randUByte(): UByte = Random.nextUInt().toUByte()
fun randUShort(): UShort = Random.nextUInt().toUShort()
fun randUInt(): UInt = Random.nextUInt()
fun randULong(): ULong = Random.nextULong()

fun randFloat(): Float = Random.nextFloat()
fun randDouble(): Double = Random.nextDouble()

private val chars: CharArray = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray()
fun randChar(): Char = chars.random()
fun randString():String = Random.nextBytes(8).toHexString()