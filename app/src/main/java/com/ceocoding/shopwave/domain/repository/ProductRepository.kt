package com.ceocoding.shopwave.domain.repository

import com.ceocoding.shopwave.domain.model.ProductItem
import com.ceocoding.shopwave.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(): Flow<Resource<List<ProductItem>>>

    suspend fun getProduct(productId: Int): Flow<Resource<ProductItem>>

    suspend fun getJeweleryProducts(): Flow<Resource<List<ProductItem>>>

    suspend fun getElectronicsProducts(): Flow<Resource<List<ProductItem>>>

    suspend fun getMenClothingProducts(): Flow<Resource<List<ProductItem>>>

    suspend fun getWomenClothingProducts(): Flow<Resource<List<ProductItem>>>



}