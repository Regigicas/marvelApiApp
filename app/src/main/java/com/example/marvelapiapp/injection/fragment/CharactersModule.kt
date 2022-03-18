package com.example.marvelapiapp.injection.fragment

import com.example.data.datasource.database.mapper.CharacterInfoCacheMapper
import com.example.data.datasource.database.model.character.CharacterInfoCache
import com.example.domain.mapper.EntityMapper
import com.example.domain.model.character.CharacterInfo
import com.example.domain.model.character.CharacterParamInfo
import com.example.domain.repository.CharacterRepository
import com.example.domain.usecase.character.list.GetCharactersUseCase
import com.example.domain.usecase.character.list.GetCharactersUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class CharactersModule {

    @Provides
    fun providesGetCharactersUseCase(repository: CharacterRepository): GetCharactersUseCase =
        GetCharactersUseCaseImpl(repository)

    @Provides
    @Singleton
    fun providesCharacterInfoCacheMapper(
        paramMapper: EntityMapper<String, List<CharacterParamInfo>>
    ): EntityMapper<CharacterInfoCache, CharacterInfo> =
        CharacterInfoCacheMapper(paramMapper)
}
