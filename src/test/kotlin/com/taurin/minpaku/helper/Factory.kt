package com.taurin.minpaku.helper

class Factory {
    companion object {
        private val factoryMap = mutableMapOf<String, (Map<String, Any>?) -> Any>()

        fun define(staticName: String, fn: (Map<String, Any>?) -> Any) {
            factoryMap[staticName] = fn
        }

        fun make(staticName: String, parameters: Map<String, Any>?): Any {
            return factoryMap[staticName]!!.invoke(parameters)
        }
    }
}

