package com.dicoding.jetreward.model

data class Product(
    val productId: Long,
    val image: Int,
    val title: String,
    val price: Int,
    val description: String
)