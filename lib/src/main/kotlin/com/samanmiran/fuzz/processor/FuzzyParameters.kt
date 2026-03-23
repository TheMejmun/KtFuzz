package com.samanmiran.fuzz.processor

import com.google.devtools.ksp.closestClassDeclaration
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSTypeReference
import com.squareup.kotlinpoet.BOOLEAN
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.typeNameOf
import com.sun.tools.javac.tree.TreeInfo.types

val PACKAGE_NAME = "com.samanmiran.fuzz"
val DEFAULTS_CLASS_NAME = "Defaults"

val FUZZY_ANNOTATION = ClassName("com.samanmiran.fuzz.annotation", "Fuzzy")

fun isFuzzy(classDeclaration: KSClassDeclaration): Boolean {
    val annotations = classDeclaration.annotations
    val annotationClasses = annotations.map { it.annotationType.resolve().toClassName() }
    return annotationClasses.contains(FUZZY_ANNOTATION)
}

fun getFuzzyDefault(type: KSTypeReference): CodeBlock? {
    val classDeclaration = type.resolve().declaration.closestClassDeclaration()
    assert(classDeclaration != null, { "Could not find class declaration for $type" })

    if (isFuzzy(classDeclaration!!)) {
        return CodeBlock.of("fuzzy${classDeclaration.simpleName.asString()}()")
    } else {
        when(classDeclaration.toClassName()){
            BOOLEAN -> return CodeBlock.of("Random.boolean()")
        }
    }
    return null

}