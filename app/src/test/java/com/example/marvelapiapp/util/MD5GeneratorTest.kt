package com.example.marvelapiapp.util

import com.example.marvelapiapp.base.BaseTest
import org.junit.Assert
import org.junit.Test

class MD5GeneratorTest : BaseTest() {
    override fun testInit() {}

    @Test
    fun `test MD5GeneratorTest`() {
        val md5 = "645978933e3c58920925d031cdee87c9"
        val result = MD5Generator.runFor("public",
            1234.toString(), "private")
        Assert.assertEquals(md5, result)
    }
}
