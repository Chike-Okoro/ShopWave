package com.ceocoding.shopwave.presentation.check_out

sealed class CheckOutEvent{
    object OnButtonClick: CheckOutEvent()
}
