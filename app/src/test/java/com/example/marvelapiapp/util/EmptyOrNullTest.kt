package com.example.marvelapiapp.util

import com.example.marvelapiapp.base.BaseTest
import org.junit.Assert
import org.junit.Test

class EmptyOrNullTest : BaseTest() {
    override fun testInit() {}

    @Test
    fun `test checkStringNotEmpty true`() {
        val value = "testtest"
        val result = EmptyOrNull.checkStringNotEmpty(value)
        Assert.assertEquals(true, result)
    }

    @Test
    fun `test checkStringNotEmpty false`() {
        val value = ""
        val result = EmptyOrNull.checkStringNotEmpty(value)
        Assert.assertEquals(false, result)
    }

    @Test
    fun `test checkListHasData true`() {
        val value = listOf("test", "test")
        val result = EmptyOrNull.checkListHasData(value)
        Assert.assertEquals(true, result)
    }

    @Test
    fun `test checkListHasData false`() {
        val value = listOf<String>()
        val result = EmptyOrNull.checkListHasData(value)
        Assert.assertEquals(false, result)
    }
}
