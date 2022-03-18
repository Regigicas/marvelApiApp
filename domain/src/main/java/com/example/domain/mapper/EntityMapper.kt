package com.example.domain.mapper

interface EntityMapper<F, T> {
    fun fromItem(from: F): T
    fun toItem(to: T): F
    fun fromList(from: List<F>): List<T>
    fun toList(to: List<T>): List<F>
}
