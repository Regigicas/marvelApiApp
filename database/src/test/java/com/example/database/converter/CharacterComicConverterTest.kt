package com.example.database.converter

import com.example.common.characters.comic.CharacterComic
import com.example.database.base.BaseTest
import org.junit.Assert
import org.junit.Test

class CharacterComicConverterTest : BaseTest() {
    private val converter = CharacterComicConverter()

    @Test
    fun `test fromComic`() {
        val convertedValue = "{\"available\":1,\"returned\":1,\"items\":[]}"
        val comic = CharacterComic(1, 1, listOf())
        val result = converter.fromComic(comic)
        Assert.assertEquals(convertedValue, result)
    }

    @Test
    fun `test toComic`() {
        val comicString = "{\"available\":1,\"returned\":1,\"items\":[]}"
        val comic = CharacterComic(1, 1, listOf())
        val result = converter.toComic(comicString)
        Assert.assertEquals(comic, result)
    }
}
