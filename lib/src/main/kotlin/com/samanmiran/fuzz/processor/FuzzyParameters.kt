package com.samanmiran.fuzz.processor

import com.google.devtools.ksp.closestClassDeclaration
import com.google.devtools.ksp.innerArguments
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSTypeArgument
import com.google.devtools.ksp.symbol.KSTypeReference
import com.squareup.kotlinpoet.BOOLEAN
import com.squareup.kotlinpoet.BYTE
import com.squareup.kotlinpoet.CHAR
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.DOUBLE
import com.squareup.kotlinpoet.FLOAT
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.INT
import com.squareup.kotlinpoet.LIST
import com.squareup.kotlinpoet.LONG
import com.squareup.kotlinpoet.MAP
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.SET
import com.squareup.kotlinpoet.SHORT
import com.squareup.kotlinpoet.STRING
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.jvm.jvmSuppressWildcards
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.toTypeParameterResolver
import com.squareup.kotlinpoet.typeNameOf
import com.sun.tools.javac.tree.TreeInfo.types
import kotlin.random.Random
import kotlin.random.nextInt

val PACKAGE_NAME = "com.samanmiran.fuzz"
val DEFAULTS_CLASS_NAME = "Defaults"

val FUZZY_ANNOTATION = ClassName("com.samanmiran.fuzz.annotation", "Fuzzy")

fun isFuzzy(classDeclaration: KSClassDeclaration): Boolean {
    val annotations = classDeclaration.annotations
    val annotationClasses = annotations.map { it.annotationType.resolve().toClassName() }
    return annotationClasses.contains(FUZZY_ANNOTATION)
}

fun fuzzyCodeBlock(classDeclaration: KSClassDeclaration): CodeBlock {
    return CodeBlock.of("fuzzy${classDeclaration.simpleName.asString()}()")
}

fun isList(classDeclaration: KSClassDeclaration): Boolean {
    return classDeclaration.toClassName() == LIST
}

fun listCodeBlock(arguments: List<KSTypeArgument>): CodeBlock {
    val elementType = arguments[0].type
    assert(elementType != null, { "Could not detect list elements" })
    val elementCodeBlock = getFuzzyDefault(elementType!!)
    return CodeBlock.of("List(Random.itemCount()) { $elementCodeBlock }")
}

fun isSet(classDeclaration: KSClassDeclaration): Boolean {
    return classDeclaration.toClassName() == SET
}

fun setCodeBlock(arguments: List<KSTypeArgument>): CodeBlock {
    val elementType = arguments[0].type
    assert(elementType != null, { "Could not detect set elements" })
    val elementCodeBlock = getFuzzyDefault(elementType!!)
    return CodeBlock.of("List(Random.itemCount()) { $elementCodeBlock }.toSet()")
}

fun isMap(classDeclaration: KSClassDeclaration): Boolean {
    return classDeclaration.toClassName() == MAP
}

fun mapCodeBlock(arguments: List<KSTypeArgument>): CodeBlock {
    val keyType = arguments[0].type
    val valType = arguments[1].type
    assert(keyType != null, { "Could not detect map key type" })
    assert(valType != null, { "Could not detect map value type" })
    val keyCodeBlock = getFuzzyDefault(keyType!!)
    val valCodeBlock = getFuzzyDefault(valType!!)
    return CodeBlock.of("List(Random.itemCount()) { $keyCodeBlock to $valCodeBlock }.toMap()")
}

fun getFuzzyDefault(type: KSTypeReference): CodeBlock {
    val classDeclaration = type.resolve().declaration.closestClassDeclaration()
    val arguments = type.resolve().arguments
    assert(classDeclaration != null, { "Could not find class declaration for $type" })

    if (isFuzzy(classDeclaration!!)) {
        return fuzzyCodeBlock(classDeclaration)
    }

    if (isList(classDeclaration)) {
        return listCodeBlock(arguments)
    }

    if (isSet(classDeclaration)) {
        return setCodeBlock(arguments)
    }

    if (isMap(classDeclaration)) {
        return mapCodeBlock(arguments)
    }

    when (classDeclaration.toClassName()) {
        BOOLEAN -> return CodeBlock.of("Random.boolean()")
        BYTE -> return CodeBlock.of("Random.byte()")
        SHORT -> return CodeBlock.of("Random.short()")
        INT -> return CodeBlock.of("Random.int()")
        LONG -> return CodeBlock.of("Random.long()")
        CHAR -> return CodeBlock.of("Random.char()")
        FLOAT -> return CodeBlock.of("Random.float()")
        DOUBLE -> return CodeBlock.of("Random.double()")
        STRING -> return CodeBlock.of("Random.string()")
    }

    // TODO maybe allow parameters without defaults
    throw RuntimeException("No default fuzzer found for ${type.toTypeName()}")
}