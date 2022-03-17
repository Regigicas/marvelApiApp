package com.example.marvelapiapp.repository.characters

import com.example.common.characters.CharacterInfo
import com.example.marvelapiapp.base.BaseTest
import org.junit.Assert
import org.junit.Test

class CharacterInfoCacheConverterTest : BaseTest() {
    private val characterInfo = CharacterInfo(1, "Test", null,
        null, null, null, null, null)

    override fun testInit() {}

    @Test
    fun `test convertTo`() {
        val result = CharacterInfoCacheConverter.convertTo(characterInfo)
        Assert.assertEquals(characterInfo.name, result.info.name)
        Assert.assertEquals(characterInfo.id, result.characterId)
    }

    @Test
    fun `test convertToList`() {
        val result = CharacterInfoCacheConverter.convertToList(listOf(characterInfo))
        Assert.assertEquals(1, result.size)
        Assert.assertEquals(characterInfo.name, result[0].info.name)
        Assert.assertEquals(characterInfo.id, result[0].characterId)
    }
}
