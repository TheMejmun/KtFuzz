package com.samanmiran.fuzz.processor

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class FuzzyFactory : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return FuzzyProcessor(
            codeGenerator = environment.codeGenerator,
            logger = environment.logger
        )
    }
}