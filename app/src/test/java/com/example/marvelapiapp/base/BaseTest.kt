package com.example.marvelapiapp.base

import io.mockk.clearAllMocks
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before

abstract class BaseTest {

    @Before
    fun init() {
        destroy()
        testInit()
    }

    @After
    fun destroy() {
        clearAllMocks()
        unmockkAll()
    }

    abstract fun testInit()

    protected fun <T: Any> T.setTestValue(fieldName: String, value: Any?): Any {
        val currentClassFields = this.javaClass.superclass?.let { superClass ->
            this.javaClass.declaredFields + superClass.declaredFields
        } ?: this.javaClass.declaredFields
        currentClassFields.find { it.name == fieldName }
            ?.also { it.isAccessible = true }
            ?.set(this, value)
        return this
    }

    protected fun <T : Any?> Any.getTestValue(fieldName: String): T =
        this.javaClass.getDeclaredField(fieldName).apply { isAccessible = true }.get(this) as T

    protected fun <T> Any.callPrivateMethod(
        methodName: String,
        vararg parameters: Any?
    ): T {
        val paramTypes = parameters.map { parameter ->
            parameter?.let {
                it::class.javaPrimitiveType ?: it::class.java
            } ?: Nothing::class.java
        }.toTypedArray()

        return this.javaClass.getDeclaredMethod(methodName, *paramTypes)
            .apply { isAccessible = true }
            .invoke(this, *parameters.map { it }.toTypedArray()) as T
    }
}
