package com.example.marvelapiapp.repository.base

import com.example.marvelapiapp.base.BaseTest
import com.example.data.util.MD5Generator
import io.mockk.mockkObject
import io.mockk.verify
import org.junit.Test

class AuthParamsTest : BaseTest() {
    override fun testInit() {}

    @Test
    fun `test getHash`() {
        mockkObject(MD5Generator)
        val params = AuthParams("public", 1234, "private")
        params.getHash()

        verify(exactly = 1) {
            MD5Generator.runFor(params.apiKey,
                params.ts.toString(), params.privateKey)
        }
    }
}
