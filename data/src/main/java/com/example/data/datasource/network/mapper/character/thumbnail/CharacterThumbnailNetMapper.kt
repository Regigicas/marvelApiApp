package com.example.data.datasource.network.mapper.character.thumbnail

import com.example.data.datasource.network.model.character.thumbnail.CharacterThumbnailNet
import com.example.domain.mapper.EntityMapper

class CharacterThumbnailNetMapper : EntityMapper<CharacterThumbnailNet, String> {

    override fun fromItem(from: CharacterThumbnailNet): String {
        var urlPath = from.path
        urlPath?.let {
            if (!it.contains("https")) { // The api seems to return http instead of https
                urlPath = it.replace("http", "https")
            }
        }
        return "${urlPath}/%s.${from.extension}"
    }

    override fun toItem(to: String): CharacterThumbnailNet {
        val urlPath = to.split("/").getOrNull(0)
        val extension = to.split(".").getOrNull(1)
        return CharacterThumbnailNet(urlPath, extension)
    }

    override fun fromList(from: List<CharacterThumbnailNet>): List<String> =
        from.map { fromItem(it) }

    override fun toList(to: List<String>): List<CharacterThumbnailNet> =
        to.map { toItem(it) }
}
