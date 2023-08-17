package com.ceocoding.shopwave.presentation.cart

import com.ceocoding.shopwave.domain.model.ProductItem

sealed class CartEvent {
    data class OnDeleteCartClick(val productItem: ProductItem): CartEvent()
    object OnUndoDeleteClick: CartEvent()
    object OnCheckOutClick: CartEvent()
}