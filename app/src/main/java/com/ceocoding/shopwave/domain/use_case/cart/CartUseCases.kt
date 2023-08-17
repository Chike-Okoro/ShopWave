package com.ceocoding.shopwave.domain.use_case.cart

data class CartUseCases(
    val insertCart: InsertCart,
    val deleteCart: DeleteCart,
    val getAllCarts: GetAllCarts,
    val getCartById: GetCartById,
    val deleteAll: DeleteAll
)