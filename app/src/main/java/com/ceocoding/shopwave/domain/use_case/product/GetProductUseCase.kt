package com.ceocoding.shopwave.domain.use_case.product

import com.ceocoding.shopwave.domain.model.ProductItem
import com.ceocoding.shopwave.domain.repository.ProductRepository
import com.ceocoding.shopwave.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(productId: Int): Flow<Resource<ProductItem>>{
        return repository.getProduct(productId)
    }
}