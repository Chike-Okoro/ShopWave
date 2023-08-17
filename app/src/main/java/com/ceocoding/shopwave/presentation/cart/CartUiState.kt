package com.ceocoding.shopwave.presentation.cart

import com.ceocoding.shopwave.domain.model.ProductItem

data class CartUiState(
    val productItem: List<ProductItem> = emptyList()
)
