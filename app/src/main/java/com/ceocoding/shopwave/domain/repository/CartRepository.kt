package com.ceocoding.shopwave.domain.repository

import com.ceocoding.shopwave.domain.model.ProductItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    suspend fun insertCart(productItem: ProductItem)

    suspend fun deleteCart(productItem: ProductItem)

    fun getAllCarts(): Flow<List<ProductItem>>

    suspend fun getCartById(id: Int): ProductItem?

    suspend fun deleteAll()
}