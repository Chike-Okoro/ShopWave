package com.ceocoding.shopwave.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ceocoding.shopwave.domain.use_case.product.GetMenClothingProductsUseCase
import com.ceocoding.shopwave.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenClothingViewModel @Inject constructor(
    private val getMenClothingProductsUseCase: GetMenClothingProductsUseCase
): ViewModel() {

    private val _state = mutableStateOf(ShopUiState())
    val state: State<ShopUiState> = _state

    init {
        getProducts()

    }

    private fun getProducts(){
        viewModelScope.launch {
            getMenClothingProductsUseCase().onEach { result ->
                when(result){
                    is Resource.Loading -> {
                        _state.value = ShopUiState(isLoading = true)
                    }

                    is Resource.Error -> {
                        _state.value = ShopUiState(
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }

                    is Resource.Success -> {
                        _state.value = ShopUiState(
                            products = result.data ?: emptyList()
                        )
                    }
                }
            }.launchIn(this)
        }
    }
}