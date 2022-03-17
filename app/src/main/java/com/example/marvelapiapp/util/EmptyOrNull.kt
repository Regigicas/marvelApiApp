package com.example.marvelapiapp.util

object EmptyOrNull {

    @JvmStatic
    fun checkStringNotEmpty(value: String?):
            Boolean = !value.isNullOrEmpty()

    @JvmStatic
    fun checkListHasData(value: List<*>?):
            Boolean = !value.isNullOrEmpty()
}
