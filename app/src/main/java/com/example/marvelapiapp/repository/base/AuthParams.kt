package com.example.marvelapiapp.repository.base

import com.example.marvelapiapp.util.MD5Generator

data class AuthParams(
    val apiKey: String,
    val ts: Long,
    val privateKey: String) {

    fun getHash(): String =
        MD5Generator.runFor(apiKey, ts.toString(), privateKey)
}
