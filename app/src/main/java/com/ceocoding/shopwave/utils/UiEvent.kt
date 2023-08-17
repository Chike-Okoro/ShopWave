package com.ceocoding.shopwave.utils

sealed class UiEvent{
    object PopBackStack: UiEvent()
    data class Navigate(val route: String): UiEvent()
    data class ShowSnackBar(
        val message: UiText,
        val action: String? = null
    ): UiEvent()

}