package com.ceocoding.shopwave.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ceocoding.shopwave.data.local.entity.CartEntity

@Database(
    entities = [CartEntity::class],
    version = 1
)
abstract class CartDatabase: RoomDatabase() {
    abstract val dao: CartDao
}