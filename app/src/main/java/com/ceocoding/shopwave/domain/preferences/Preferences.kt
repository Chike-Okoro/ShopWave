package com.ceocoding.shopwave.domain.preferences

interface Preferences {

    fun saveTotalPrice(totalPrice: Double)
    fun saveItemQuantity(id: Int, quantity: Int)
    fun loadItemQuantities(): Map<Int, Int>
    fun loadTotalPrice(): Double
    fun removeAllItemQuantities()

    companion object{
        const val KEY_TOTAL_PRICE = "total_price"
        const val KEY_ITEM_QUANTITY = "item_quantity"
    }
}