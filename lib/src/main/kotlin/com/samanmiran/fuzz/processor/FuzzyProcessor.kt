package com.samanmiran.fuzz.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ksp.toTypeName

class FuzzyProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver
            .getSymbolsWithAnnotation("com.samanmiran.fuzz.annotation.Fuzzy")
            .filterIsInstance<KSClassDeclaration>()

        symbols.forEach { classDecl ->
            generateFactory(classDecl)
        }

        return emptyList()
    }

    private fun generateFactory(classDecl: KSClassDeclaration) {
        val packageName = classDecl.packageName.asString()
        val className = classDecl.simpleName.asString()
        val classTypeName = ClassName(packageName, className)

        assert(classDecl.primaryConstructor != null) { "${classDecl.simpleName.asString()} must have a primary constructor" }
        val args = classDecl.primaryConstructor!!.parameters
            .map { Pair(it.name!!.getShortName(), it.type.toTypeName()) }
        println("Generating factory for ${classDecl.simpleName.asString()} with args: $args")

        val parameterSpecs = args.map { (name, type) ->
            ParameterSpec.builder(name, type)
                .defaultValue(null)
                .build()
        }

        val returnString = "return %T(${args.map { it.first }.joinToString(", ")})"
        val factoryFun = FunSpec.builder("fuzzy${className}")
            .returns(classTypeName)
            .addParameters(parameterSpecs)
            .addStatement(returnString, classTypeName)
            .build()

        val fileSpec = FileSpec.builder(packageName, "${className}FuzzyFactory")
            .addFunction(factoryFun)
            .build()

        val output = codeGenerator.createNewFile(
            Dependencies(false, classDecl.containingFile!!),
            packageName,
            "${className}FuzzyFactory"
        )

        output.writer().use { fileSpec.writeTo(it) }
    }
}