package com.ceocoding.shopwave.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ceocoding.shopwave.data.local.entity.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cartEntity: CartEntity)

    @Delete
    suspend fun deleteCart(cartEntity: CartEntity)

    @Query(
        """
            SELECT * FROM cartentity
        """
    )
    fun getAllCarts(): Flow<List<CartEntity>>

    @Query(
        """
            SELECT * FROM cartentity WHERE id = :id
        """
    )
    suspend fun getCartById(id: Int): CartEntity?

    @Query(
        """
            DELETE FROM cartentity
        """
    )
    suspend fun deleteAll()



}