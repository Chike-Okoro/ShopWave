package com.ceocoding.shopwave.data.remote

import com.ceocoding.shopwave.data.remote.dto.ProductDto
import com.ceocoding.shopwave.data.remote.dto.ProductDtoItem
import retrofit2.http.GET
import retrofit2.http.Path

interface ShopWaveApi {


    //Products
    @GET("/products")
    suspend fun getProducts(): ProductDto

    @GET("/products/{productId}")
    suspend fun getProduct(@Path("productId") productId: Int): ProductDtoItem


    //Categories
    @GET("/products/category/jewelery")
    suspend fun getJeweleryProducts(): ProductDto

    @GET("/products/category/electronics")
    suspend fun getElectronicsProducts(): ProductDto

    @GET("/products/category/men's clothing")
    suspend fun getMenClothingProducts(): ProductDto

    @GET("/products/category/women's clothing")
    suspend fun getWomenClothingProducts(): ProductDto



    companion object{
        const val BASE_URL = "https://fakestoreapi.com/"
        const val PARAM_PRODUCT_ID = "productId"
    }
}