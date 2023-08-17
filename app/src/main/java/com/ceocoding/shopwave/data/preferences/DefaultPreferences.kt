package com.ceocoding.shopwave.data.preferences

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.edit
import com.ceocoding.shopwave.domain.preferences.Preferences

class DefaultPreferences(
    private val sharedPref: SharedPreferences
): Preferences {
    override fun saveTotalPrice(totalPrice: Double) {
        sharedPref.edit()
            .putFloat(Preferences.KEY_TOTAL_PRICE, totalPrice.toFloat())
            .apply()
    }

    override fun saveItemQuantity(id: Int, quantity: Int) {
        sharedPref.edit()
            .putInt("${Preferences.KEY_ITEM_QUANTITY}${id}", quantity)
            .apply()
    }

    override fun removeAllItemQuantities() {
        sharedPref.edit {
            sharedPref.all.keys.filter { it.startsWith(Preferences.KEY_ITEM_QUANTITY) }.forEach {
                remove(it)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun loadItemQuantities(): Map<Int, Int> {
        val itemQuantitiesMap = mutableMapOf<Int, Int>()
        sharedPref.all.forEach { (key, value) ->
            if (key.startsWith(Preferences.KEY_ITEM_QUANTITY)){
                val itemId = key.removePrefix(Preferences.KEY_ITEM_QUANTITY).toInt()
                val quantity = (value as? Int) ?: 1
                itemQuantitiesMap[itemId] = quantity
            }
        }
        return itemQuantitiesMap
    }

    override fun loadTotalPrice(): Double {
        return sharedPref.getFloat(Preferences.KEY_TOTAL_PRICE, 0.0f).toDouble()
    }

}