package com.example.marvelapiapp.util

import java.math.BigInteger
import java.security.MessageDigest

object MD5Generator {

    private const val RADIX = 16
    private const val PAD_LENGTH = 32

    fun runFor(publicKey: String, ts: String, privateKey: String): String {
        val md = MessageDigest.getInstance("MD5")
        val input = "$ts$privateKey$publicKey"
        return BigInteger(1, md.digest(input.toByteArray()))
            .toString(RADIX).padStart(PAD_LENGTH, '0')
    }
}
