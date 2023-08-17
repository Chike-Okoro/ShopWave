package com.ceocoding.shopwave.domain.use_case.product

import com.ceocoding.shopwave.domain.model.ProductItem
import com.ceocoding.shopwave.domain.repository.ProductRepository
import com.ceocoding.shopwave.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMenClothingProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<ProductItem>>> {
        return repository.getMenClothingProducts()
    }
}