package com.example.database.base

import io.mockk.clearAllMocks
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before

abstract class BaseTest {

    @Before
    fun init() {
        destroy()
    }

    @After
    fun destroy() {
        clearAllMocks()
        unmockkAll()
    }
}
