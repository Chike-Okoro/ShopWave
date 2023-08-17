package com.ceocoding.shopwave.domain.model

import com.ceocoding.shopwave.data.remote.dto.Rating

data class ProductItem(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Rating,
    val title: String
)
