package com.example.domain.usecase.character.info

import com.example.domain.model.character.CharacterInfo
import com.example.domain.model.response.UseCaseResponse
import com.example.domain.repository.CharacterRepository
import com.example.domain.usecase.base.BaseUseCase

class GetCharacterInfoUseCaseImpl(private val repository: CharacterRepository)
    : GetCharacterInfoUseCase, BaseUseCase<Int, UseCaseResponse<CharacterInfo?>>() {

    override suspend fun run(params: Int): UseCaseResponse<CharacterInfo?> =
        UseCaseResponse.success(repository.getCharacter(params))
}
