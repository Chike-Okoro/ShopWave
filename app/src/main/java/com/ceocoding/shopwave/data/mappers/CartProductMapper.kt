package com.ceocoding.shopwave.data.mappers

import com.ceocoding.shopwave.data.local.entity.CartEntity
import com.ceocoding.shopwave.data.remote.dto.Rating
import com.ceocoding.shopwave.domain.model.ProductItem


fun ProductItem.toCartEntity(): CartEntity{
    return CartEntity(
        image = image,
        title = title,
        price = price,
        id = id
    )
}

fun CartEntity.toProductItem(): ProductItem {
    return ProductItem(
        id = id!!,
        image = image,
        price = price,
        title = title,
        category = "",
        rating = Rating(0,0.0),
        description = ""
    )
}


