package com.ceocoding.shopwave.data.mappers

import com.ceocoding.shopwave.data.local.entity.CartEntity
import com.ceocoding.shopwave.data.remote.dto.ProductDtoItem
import com.ceocoding.shopwave.domain.model.ProductItem

fun ProductDtoItem.toProductItem(): ProductItem {
    return ProductItem(
        category = category,
        description = description,
        id = id,
        image = image,
        price = price,
        rating = rating,
        title = title
    )
}

fun ProductDtoItem.toCartEntity(): CartEntity{
    return CartEntity(
        image = image,
        title = title,
        price = price,
        id = id
    )
}


