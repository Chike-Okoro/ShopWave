package com.ceocoding.shopwave.presentation.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ceocoding.shopwave.R
import com.ceocoding.shopwave.data.remote.ShopWaveApi
import com.ceocoding.shopwave.domain.use_case.cart.CartUseCases
import com.ceocoding.shopwave.domain.use_case.product_detail.GetProductDetailUseCase
import com.ceocoding.shopwave.utils.Resource
import com.ceocoding.shopwave.utils.Screen
import com.ceocoding.shopwave.utils.UiEvent
import com.ceocoding.shopwave.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase,
    savedStateHandle: SavedStateHandle,
    private val cartUseCases: CartUseCases
): ViewModel(){

    private val _state = mutableStateOf(ProductDetailState())
    val state: State<ProductDetailState> = _state

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val productId = savedStateHandle.get<Int>(ShopWaveApi.PARAM_PRODUCT_ID)!!
        if (productId != -1){
            getProduct(productId)
        }
    }

    fun onEvent(event: ProductDetailEvent){
        when(event){
            is ProductDetailEvent.OnAddToCartClick -> {
                addToCart(event, event.productItem.id)
            }
            is ProductDetailEvent.OnPopBackStack -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
            is ProductDetailEvent.OnProceedToCheckoutScreen -> {
                checkout(event)
            }
        }
    }



     private fun getProduct(productId: Int){
        viewModelScope.launch {
            getProductDetailUseCase(productId).onEach { result ->
                when(result){
                    is Resource.Loading -> {
                        _state.value = ProductDetailState(isLoading = true)
                    }

                    is Resource.Error -> {
                        _state.value = ProductDetailState(
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }

                    is Resource.Success -> {
                        _state.value = ProductDetailState(productItem = result.data)
                    }
                }
            }.launchIn(this)
        }
    }

    private fun addToCart(event: ProductDetailEvent.OnAddToCartClick, id: Int){
        viewModelScope.launch {
            val uiState = _state.value
            if (uiState.productItem == event.productItem){
                val existingCart = cartUseCases.getCartById(id)
                if(existingCart == null){
                    cartUseCases.insertCart(productItem = uiState.productItem)
                    _uiEvent.send(UiEvent.ShowSnackBar(
                        message = UiText.StringResource(R.string.product_inserted)
                    ))
                }
                else{
                    _uiEvent.send(UiEvent.ShowSnackBar(
                        message = UiText.StringResource(R.string.item_already_inserted)
                    ))
                }

            }
        }
    }

    private fun checkout(event: ProductDetailEvent.OnProceedToCheckoutScreen){
        viewModelScope.launch{
            val uiState = _state.value
            if (uiState.productItem == event.productItem){
                cartUseCases.insertCart(productItem = uiState.productItem)
                sendUiEvent(UiEvent.Navigate(Screen.CartScreen.route))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}