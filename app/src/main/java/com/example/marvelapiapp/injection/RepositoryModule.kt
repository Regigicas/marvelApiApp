package com.example.marvelapiapp.injection

import com.example.data.datasource.database.dao.CharacterDao
import com.example.data.datasource.database.mapper.CharacterInfoCacheMapper
import com.example.data.datasource.database.mapper.CharacterParamCacheMapper
import com.example.data.datasource.database.model.character.CharacterInfoCache
import com.example.data.datasource.network.manager.CharacterApi
import com.example.data.datasource.network.mapper.character.CharacterInfoNetMapper
import com.example.data.datasource.network.mapper.character.comic.CharacterComicNetMapper
import com.example.data.datasource.network.mapper.character.event.CharacterEventNetMapper
import com.example.data.datasource.network.mapper.character.series.CharacterSeriesNetMapper
import com.example.data.datasource.network.mapper.character.story.CharacterStoryNetMapper
import com.example.data.datasource.network.mapper.character.thumbnail.CharacterThumbnailNetMapper
import com.example.data.datasource.network.model.character.CharacterInfoNet
import com.example.data.datasource.network.model.character.comic.CharacterComicNet
import com.example.data.datasource.network.model.character.event.CharacterEventNet
import com.example.data.datasource.network.model.character.series.CharacterSeriesNet
import com.example.data.datasource.network.model.character.story.CharacterStoryNet
import com.example.data.datasource.network.model.character.thumbnail.CharacterThumbnailNet
import com.example.data.repository.character.CharacterRepositoryImpl
import com.example.domain.mapper.EntityMapper
import com.example.domain.model.character.CharacterInfo
import com.example.domain.model.character.CharacterParamInfo
import com.example.domain.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providesCharacterRepository(
        networkApi: CharacterApi,
        dao: CharacterDao,
        databaseMapper: EntityMapper<CharacterInfoCache, CharacterInfo>,
        networkMapper: EntityMapper<CharacterInfoNet, CharacterInfo>
    ): CharacterRepository =
        CharacterRepositoryImpl(networkApi, dao, databaseMapper, networkMapper)

    @Provides
    @Singleton
    fun providesCharacterInfoCacheMapper(
        paramMapper: EntityMapper<String, List<CharacterParamInfo>>
    ): EntityMapper<CharacterInfoCache, CharacterInfo> =
        CharacterInfoCacheMapper(paramMapper)

    @Provides
    @Singleton
    fun providesCharacterInfoNetMapper(
        thumbnailMapper: EntityMapper<CharacterThumbnailNet, String>,
        comicMapper: EntityMapper<CharacterComicNet, List<CharacterParamInfo>>,
        eventMapper: EntityMapper<CharacterEventNet, List<CharacterParamInfo>>,
        seriesMapper: EntityMapper<CharacterSeriesNet, List<CharacterParamInfo>>,
        storiesMapper: EntityMapper<CharacterStoryNet, List<CharacterParamInfo>>
    ): EntityMapper<CharacterInfoNet, CharacterInfo> =
        CharacterInfoNetMapper(thumbnailMapper, comicMapper, eventMapper, seriesMapper, storiesMapper)

    @Provides
    @Singleton
    fun providesCharacterParamCacheMapper(): EntityMapper<String, List<CharacterParamInfo>> =
        CharacterParamCacheMapper()

    @Provides
    @Singleton
    fun providesCharacterThumbnailNetMapper(): EntityMapper<CharacterThumbnailNet, String> =
        CharacterThumbnailNetMapper()

    @Provides
    @Singleton
    fun providesCharacterComicNetMapper(): EntityMapper<CharacterComicNet, List<CharacterParamInfo>> =
        CharacterComicNetMapper()

    @Provides
    @Singleton
    fun providesCharacterEventNetMapper(): EntityMapper<CharacterEventNet, List<CharacterParamInfo>> =
        CharacterEventNetMapper()

    @Provides
    @Singleton
    fun providesCharacterSeriesNetMapper(): EntityMapper<CharacterSeriesNet, List<CharacterParamInfo>> =
        CharacterSeriesNetMapper()

    @Provides
    @Singleton
    fun providesCharacterStoryNetMapper(): EntityMapper<CharacterStoryNet, List<CharacterParamInfo>> =
        CharacterStoryNetMapper()
}
