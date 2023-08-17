package com.ceocoding.shopwave.data.repository

import com.ceocoding.shopwave.data.local.CartDao
import com.ceocoding.shopwave.data.mappers.toCartEntity
import com.ceocoding.shopwave.data.mappers.toProductItem
import com.ceocoding.shopwave.data.remote.ShopWaveApi
import com.ceocoding.shopwave.domain.model.ProductItem
import com.ceocoding.shopwave.domain.repository.ProductRepository
import com.ceocoding.shopwave.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ShopWaveApi
): ProductRepository {
    override suspend fun getProducts(): Flow<Resource<List<ProductItem>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val products = api.getProducts().map { it.toProductItem() }
                emit(Resource.Success(products))
            } catch (e: HttpException){
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException){
                emit(Resource.Error("Couldn't reach server. Check your connection"))
            }

        }
    }

    override suspend fun getProduct(productId: Int): Flow<Resource<ProductItem>> {
        return flow {
            try {
                emit(Resource.Loading())
                val product = api.getProduct(productId).toProductItem()
                emit(Resource.Success(product))
            } catch (e: HttpException){
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException){
                emit(Resource.Error("Couldn't reach server. Check your connection"))
            }
        }
    }

    override suspend fun getJeweleryProducts(): Flow<Resource<List<ProductItem>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val products = api.getJeweleryProducts().map { it.toProductItem() }
                emit(Resource.Success(products))
            } catch (e: HttpException){
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException){
                emit(Resource.Error("Couldn't reach server. Check your connection"))
            }

        }
    }

    override suspend fun getElectronicsProducts(): Flow<Resource<List<ProductItem>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val products = api.getElectronicsProducts().map { it.toProductItem() }
                emit(Resource.Success(products))
            } catch (e: HttpException){
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException){
                emit(Resource.Error("Couldn't reach server. Check your connection"))
            }

        }
    }

    override suspend fun getMenClothingProducts(): Flow<Resource<List<ProductItem>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val products = api.getMenClothingProducts().map { it.toProductItem() }
                emit(Resource.Success(products))
            } catch (e: HttpException){
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException){
                emit(Resource.Error("Couldn't reach server. Check your connection"))
            }

        }
    }

    override suspend fun getWomenClothingProducts(): Flow<Resource<List<ProductItem>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val products = api.getWomenClothingProducts().map { it.toProductItem() }
                emit(Resource.Success(products))
            } catch (e: HttpException){
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException){
                emit(Resource.Error("Couldn't reach server. Check your connection"))
            }

        }
    }


}

