package com.ceocoding.shopwave.domain.use_case.product

data class ProductUseCases(
    val getProductUseCase: GetProductUseCase,
    val getProductsUseCase: GetProductsUseCase,
    val getElectronicsProductsUseCase: GetElectronicsProductsUseCase,
    val getJeweleryProductsUseCase: GetJeweleryProductsUseCase,
    val getMenClothingProductsUseCase: GetMenClothingProductsUseCase,
    val getWomenClothingProductsUseCase: GetWomenClothingProductsUseCase
)