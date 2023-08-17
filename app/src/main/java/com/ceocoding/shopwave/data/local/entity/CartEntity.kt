package com.ceocoding.shopwave.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ceocoding.shopwave.data.remote.dto.Rating

@Entity
data class CartEntity(
    val image: String,
    val title: String,
    val price: Double,
    @PrimaryKey val id: Int? = null
)
