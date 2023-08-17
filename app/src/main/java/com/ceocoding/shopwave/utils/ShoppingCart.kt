package com.ceocoding.shopwave.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.ceocoding.shopwave.domain.model.ProductItem

class ShoppingCart {
    private val productItem = mutableListOf<ProductItem>()

    var qty by mutableStateOf(1)

    fun calculatePrice(productItem: ProductItem): Double{
       return productItem.price * qty
    }



    fun calculateTotalPrice(): Double{
        var total = 0.0
        for (item in productItem){
            total += item.price
        }
        return total

    }
}