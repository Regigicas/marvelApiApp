package com.example.database.converter

import com.example.common.characters.story.CharacterStory
import com.example.database.base.BaseTest
import org.junit.Assert
import org.junit.Test

class CharacterStoryConverterTest : BaseTest() {
    private val converter = CharacterStoryConverter()

    @Test
    fun `test fromStory`() {
        val convertedValue = "{\"available\":1,\"returned\":1,\"items\":[]}"
        val story = CharacterStory(1, 1, listOf())
        val result = converter.fromStory(story)
        Assert.assertEquals(convertedValue, result)
    }

    @Test
    fun `test toComic`() {
        val storyString = "{\"available\":1,\"returned\":1,\"items\":[]}"
        val story = CharacterStory(1, 1, listOf())
        val result = converter.toStory(storyString)
        Assert.assertEquals(story, result)
    }
}
