package com.example.data.datasource.database.converter

import com.example.common.characters.thumbnail.CharacterThumbnail
import com.example.data.base.BaseTest
import org.junit.Assert
import org.junit.Test

class CharacterThumbnailConverterTest : BaseTest() {
    private val converter = CharacterThumbnailConverter()

    @Test
    fun `test fromThumbnail`() {
        val convertedValue = "{\"path\":\"/test\",\"extension\":\".png\"}"
        val thumbnail = CharacterThumbnail("/test", ".png")
        val result = converter.fromThumbnail(thumbnail)
        Assert.assertEquals(convertedValue, result)
    }

    @Test
    fun `test toThumbnail`() {
        val thumbnailString = "{\"path\":\"/test\",\"extension\":\".png\"}"
        val thumbnail = CharacterThumbnail("/test", ".png")
        val result = converter.toThumbnail(thumbnailString)
        Assert.assertEquals(thumbnail, result)
    }
}
