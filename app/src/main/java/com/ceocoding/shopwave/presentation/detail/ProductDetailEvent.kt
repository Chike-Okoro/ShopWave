package com.ceocoding.shopwave.presentation.detail

import com.ceocoding.shopwave.domain.model.ProductItem

sealed class ProductDetailEvent{
    data class OnAddToCartClick(val productItem: ProductItem): ProductDetailEvent()
    object OnPopBackStack: ProductDetailEvent()
    data class OnProceedToCheckoutScreen(val productItem: ProductItem): ProductDetailEvent()
}
