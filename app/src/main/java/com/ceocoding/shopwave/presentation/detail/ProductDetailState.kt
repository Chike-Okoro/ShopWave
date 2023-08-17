package com.ceocoding.shopwave.presentation.detail

import com.ceocoding.shopwave.domain.model.ProductItem

data class ProductDetailState(
    val isLoading: Boolean = false,
    val productItem: ProductItem? = null,
    val error: String = ""
)
