package com.ceocoding.shopwave.domain.use_case.cart

import com.ceocoding.shopwave.domain.model.ProductItem
import com.ceocoding.shopwave.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCarts @Inject constructor(
    private val repository: CartRepository
) {
     operator fun invoke(): Flow<List<ProductItem>>{
        return repository.getAllCarts()
    }
}