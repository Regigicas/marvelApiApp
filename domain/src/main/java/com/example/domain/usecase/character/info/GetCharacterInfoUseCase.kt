package com.example.domain.usecase.character.info

import com.example.domain.model.character.CharacterInfo
import com.example.domain.model.response.UseCaseResponse
import com.example.domain.usecase.base.UseCase

interface GetCharacterInfoUseCase : UseCase<Int, UseCaseResponse<CharacterInfo?>>
