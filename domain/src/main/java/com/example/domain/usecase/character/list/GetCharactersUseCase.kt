package com.example.domain.usecase.character.list

import com.example.domain.model.character.CharacterInfo
import com.example.domain.model.response.UseCaseResponse
import com.example.domain.usecase.base.UseCase

interface GetCharactersUseCase : UseCase<GetCharactersUseCase.GetCharactersParams, UseCaseResponse<List<CharacterInfo>>> {

    data class GetCharactersParams(
        val limit: Int,
        val offset: Int
    )
}
