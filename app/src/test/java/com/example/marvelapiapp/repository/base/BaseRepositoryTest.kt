package com.example.marvelapiapp.repository.base

import com.example.marvelapiapp.BuildConfig
import com.example.marvelapiapp.base.BaseTest
import com.example.marvelapiapp.repository.characters.CharactersRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.junit.Assert
import org.junit.Test

class BaseRepositoryTest : BaseTest() {
    private lateinit var repository: CharactersRepository

    override fun testInit() {
        repository = spyk(CharactersRepository(mockk(relaxed = true), mockk(relaxed = true)))
    }

    @Test
    fun `test getAuthParams`() {
        every { repository["getCurrentTime"]() } returns 1L

        val params = AuthParams(apiKey = BuildConfig.PUBLIC_KEY,
            ts = 1, privateKey = BuildConfig.PRIVATE_KEY)
        val result = repository.getAuthParams()
        Assert.assertEquals(params.apiKey, result.apiKey)
        Assert.assertEquals(params.ts, result.ts)
        Assert.assertEquals(params.privateKey, result.privateKey)
    }

    @Test
    fun `test getCurrentTime`() {
        val now = System.currentTimeMillis() / 1000
        val result = repository.callPrivateAbstractMethod<Long>("getCurrentTime")
        val valid = (result - now) < 10
        Assert.assertEquals(true, valid)
    }

    private inline fun <reified T> Any.callPrivateAbstractMethod(
        methodName: String,
        vararg parameters: Any): T {
        val methods = BaseRepository::class.java.getDeclaredMethod(methodName,
            *parameters.map { it::class.java }.toTypedArray()).apply { isAccessible = true }
        return methods.invoke(this, *parameters) as T
    }
}
