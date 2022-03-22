package com.example.domain.usecase.character.list

import com.example.domain.model.character.CharacterInfo
import com.example.domain.model.response.UseCaseResponse
import com.example.domain.repository.CharacterRepository
import com.example.domain.usecase.base.BaseUseCase

class GetCharactersUseCaseImpl(private val repository: CharacterRepository)
    : GetCharactersUseCase, BaseUseCase<GetCharactersUseCase.GetCharactersParams, UseCaseResponse<List<CharacterInfo>>>() {

    override suspend fun run(
        params: GetCharactersUseCase.GetCharactersParams
    ): UseCaseResponse<List<CharacterInfo>> =
        UseCaseResponse.success(repository.getCharacters(params.limit, params.offset))
}
