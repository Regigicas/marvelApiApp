package com.example.domain.constant

import java.util.Locale

enum class ThumbnailAspectRatio {
    PORTRAIT_SMALL,
    PORTRAIT_MEDIUM,
    PORTRAIT_XLARGE,
    PORTRAIT_FANTASTIC,
    PORTRAIT_UNCANNY,
    PORTRAIT_INCREDIBLE,
    STANDARD_SMALL,
    STANDARD_MEDIUM,
    STANDARD_LARGE,
    STANDARD_XLARGE,
    STANDARD_FANTASTIC,
    STANDARD_AMAZING,
    LANDSCAPE_SMALL,
    LANDSCAPE_MEDIUM,
    LANDSCAPE_LARGE,
    LANDSCAPE_XLARGE,
    LANDSCAPE_AMAZING,
    LANDSCAPE_INCREDIBLE;

    fun lowerCase(): String = this.toString().lowercase(Locale.ROOT)
}
