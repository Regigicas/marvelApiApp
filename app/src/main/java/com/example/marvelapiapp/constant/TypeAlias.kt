package com.example.marvelapiapp.constant

import com.example.common.characters.CharacterInfo
import com.example.marvelapiapp.repository.base.response.BaseResponse

typealias CharactersRequest = BaseResponse<List<CharacterInfo>?>
typealias CharacterInfoRequest = BaseResponse<CharacterInfo?>
