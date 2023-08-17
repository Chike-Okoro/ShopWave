package com.ceocoding.shopwave.domain.use_case.cart

import com.ceocoding.shopwave.domain.model.ProductItem
import com.ceocoding.shopwave.domain.repository.CartRepository
import javax.inject.Inject

class GetCartById @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(id: Int): ProductItem? {
        return repository.getCartById(id)
    }
}