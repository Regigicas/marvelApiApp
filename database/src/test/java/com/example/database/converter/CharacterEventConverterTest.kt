package com.example.database.converter

import com.example.common.characters.event.CharacterEvent
import com.example.database.base.BaseTest
import org.junit.Assert
import org.junit.Test

class CharacterEventConverterTest : BaseTest() {
    private val converter = CharacterEventConverter()

    @Test
    fun `test fromEvent`() {
        val convertedValue = "{\"available\":1,\"returned\":1,\"items\":[]}"
        val event = CharacterEvent(1, 1, listOf())
        val result = converter.fromEvent(event)
        Assert.assertEquals(convertedValue, result)
    }

    @Test
    fun `test toEvent`() {
        val eventString = "{\"available\":1,\"returned\":1,\"items\":[]}"
        val event = CharacterEvent(1, 1, listOf())
        val result = converter.toEvent(eventString)
        Assert.assertEquals(event, result)
    }
}
