package com.ceocoding.shopwave.presentation.home

import com.ceocoding.shopwave.domain.model.ProductItem

data class ShopUiState(
    val isLoading: Boolean = false,
    val products: List<ProductItem> = emptyList(),
    val error: String = ""
)
