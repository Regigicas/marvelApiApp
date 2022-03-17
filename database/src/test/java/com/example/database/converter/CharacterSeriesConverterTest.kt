package com.example.database.converter

import com.example.common.characters.series.CharacterSeries
import com.example.database.base.BaseTest
import org.junit.Assert
import org.junit.Test

class CharacterSeriesConverterTest : BaseTest() {
    private val converter = CharacterSeriesConverter()

    @Test
    fun `test fromSeries`() {
        val convertedValue = "{\"available\":1,\"returned\":1,\"items\":[]}"
        val series = CharacterSeries(1, 1, listOf())
        val result = converter.fromSeries(series)
        Assert.assertEquals(convertedValue, result)
    }

    @Test
    fun `test toSeries`() {
        val seriesString = "{\"available\":1,\"returned\":1,\"items\":[]}"
        val series = CharacterSeries(1, 1, listOf())
        val result = converter.toSeries(seriesString)
        Assert.assertEquals(series, result)
    }
}
