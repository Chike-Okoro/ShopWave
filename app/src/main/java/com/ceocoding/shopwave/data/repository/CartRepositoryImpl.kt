package com.ceocoding.shopwave.data.repository

import com.ceocoding.shopwave.data.local.CartDao
import com.ceocoding.shopwave.data.mappers.toCartEntity
import com.ceocoding.shopwave.data.mappers.toProductItem
import com.ceocoding.shopwave.domain.model.ProductItem
import com.ceocoding.shopwave.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val dao: CartDao
): CartRepository {
    override suspend fun insertCart(productItem: ProductItem) {
        return dao.insertCart(productItem.toCartEntity())
    }

    override suspend fun deleteCart(productItem: ProductItem) {
        return dao.deleteCart(productItem.toCartEntity())
    }

    override fun getAllCarts(): Flow<List<ProductItem>> {
        return dao.getAllCarts().map { entities ->
            entities.map {it.toProductItem()}
        }
    }

    override suspend fun getCartById(id: Int): ProductItem? {
        return dao.getCartById(id)?.toProductItem()
    }

    override suspend fun deleteAll() {
        return dao.deleteAll()
    }
}