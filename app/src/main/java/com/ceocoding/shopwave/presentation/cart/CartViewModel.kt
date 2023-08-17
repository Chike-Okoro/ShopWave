package com.ceocoding.shopwave.presentation.cart

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ceocoding.shopwave.domain.model.ProductItem
import com.ceocoding.shopwave.domain.use_case.cart.CartUseCases
import com.ceocoding.shopwave.utils.UiEvent
import com.ceocoding.shopwave.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.ceocoding.shopwave.R
import com.ceocoding.shopwave.domain.preferences.Preferences
import com.ceocoding.shopwave.utils.Screen
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.roundToInt

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartUseCases: CartUseCases,
    private val preferences: Preferences
): ViewModel(){

    private val _cartItems = MutableStateFlow<List<ProductItem>>(emptyList())
    val cartItems: StateFlow<List<ProductItem>> = _cartItems

    private val itemQuantities = mutableStateMapOf<Int, Int>()
    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> = _totalPrice

    var deletedCartItem: ProductItem? = null

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getCarts()
    }

    fun getItemQuantity(itemId: Int): Int {
        updateTotalPrice()
        return itemQuantities[itemId] ?: 1
    }

    private fun updateTotalPrice() {
        var totalPrice = 0.0
        _cartItems.value.forEach { cartItem ->
            totalPrice += (itemQuantities[cartItem.id] ?: 1) * cartItem.price
        }
         _totalPrice.value = BigDecimal(totalPrice).setScale(2, RoundingMode.HALF_UP).toDouble()
        preferences.saveTotalPrice(_totalPrice.value)
    }

    fun incrementQuantity(itemId: Int) {
        val currentQuantity = itemQuantities[itemId] ?: 1
        itemQuantities[itemId] = currentQuantity + 1
        updateTotalPrice()
        preferences.saveItemQuantity(itemId, currentQuantity + 1)
    }

    fun decrementQuantity(itemId: Int) {
        val currentQuantity = itemQuantities[itemId] ?: 1
        if (currentQuantity > 1) {
            itemQuantities[itemId] = currentQuantity - 1
            updateTotalPrice()
            preferences.saveItemQuantity(itemId, currentQuantity - 1)
        }
    }

    private fun removeCartItem(itemId: Int){
        itemQuantities.remove(itemId)
        updateTotalPrice()
        preferences.saveItemQuantity(itemId, 1)
    }

    fun removeAllCartItems(){
        itemQuantities.clear()
        updateTotalPrice()
        preferences.removeAllItemQuantities()
    }

    fun onEvent(event: CartEvent){
        when(event){
            is CartEvent.OnDeleteCartClick -> {
                viewModelScope.launch {
                    deletedCartItem = event.productItem
                    cartUseCases.deleteCart(event.productItem)
                    sendUiEvent(UiEvent.ShowSnackBar(
                        message = UiText.StringResource(R.string.product_deleted),
                        action = "Undo"
                    ))
                    removeCartItem(event.productItem.id)
                }
            }
            is CartEvent.OnCheckOutClick -> {
                viewModelScope.launch {
                    cartUseCases.deleteAll()
                }
                removeAllCartItems()
                sendUiEvent(UiEvent.PopBackStack)
                sendUiEvent(UiEvent.Navigate(Screen.CheckOutScreen.route))
            }
            is CartEvent.OnUndoDeleteClick -> {
                deletedCartItem?.let { cart ->
                    viewModelScope.launch {
                        cartUseCases.insertCart(cart)
                    }
                }
            }
        }
    }

    private fun getCarts(){
        cartUseCases.getAllCarts().onEach { result ->
            _totalPrice.value = preferences.loadTotalPrice()
            _cartItems.value = result
            result.forEach { item ->
                itemQuantities[item.id] = 1
                itemQuantities.putAll(preferences.loadItemQuantities())
            }
        }.launchIn(viewModelScope)
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }



}

