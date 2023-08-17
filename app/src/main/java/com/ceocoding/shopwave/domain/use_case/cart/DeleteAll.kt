package com.ceocoding.shopwave.domain.use_case.cart

import com.ceocoding.shopwave.domain.repository.CartRepository
import javax.inject.Inject

class DeleteAll @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(){
        return repository.deleteAll()
    }
}