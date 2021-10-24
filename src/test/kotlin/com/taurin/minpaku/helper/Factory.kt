package com.taurin.minpaku.helper

import org.apache.poi.ss.formula.functions.T

class Factory {
    companion object {
        private val factoryMap = mutableMapOf<T, (Map<String, Any>?) -> T>()

        fun define(staticClass: T, fn: (Map<String, Any>?) -> T) {
            factoryMap[staticClass] = fn
        }

        fun make(staticClass: T, parameters: Map<String, Any>?): T? {
            return factoryMap[staticClass]?.invoke(parameters)
        }
    }
}

