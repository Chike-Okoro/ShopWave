package com.ceocoding.shopwave.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.ceocoding.shopwave.data.local.CartDatabase
import com.ceocoding.shopwave.data.preferences.DefaultPreferences
import com.ceocoding.shopwave.data.remote.ShopWaveApi
import com.ceocoding.shopwave.data.repository.CartRepositoryImpl
import com.ceocoding.shopwave.data.repository.ProductRepositoryImpl
import com.ceocoding.shopwave.domain.preferences.Preferences
import com.ceocoding.shopwave.domain.repository.CartRepository
import com.ceocoding.shopwave.domain.repository.ProductRepository
import com.ceocoding.shopwave.domain.use_case.cart.*
import com.ceocoding.shopwave.domain.use_case.product.*
import com.ceocoding.shopwave.domain.use_case.product_detail.GetProductDetailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        app: Application
    ): SharedPreferences{
        return app.getSharedPreferences("shared_pref", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePreferences(sharedPreferences: SharedPreferences): Preferences{
        return DefaultPreferences(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideShopWaveApi(): ShopWaveApi{
        return Retrofit.Builder()
            .baseUrl(ShopWaveApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ShopWaveApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(api: ShopWaveApi): ProductRepository{
        return ProductRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesProductUseCases(repository: ProductRepository): ProductUseCases {
        return ProductUseCases(
            getProductUseCase = GetProductUseCase(repository),
            getProductsUseCase = GetProductsUseCase(repository),
            getElectronicsProductsUseCase = GetElectronicsProductsUseCase(repository),
            getJeweleryProductsUseCase = GetJeweleryProductsUseCase(repository),
            getMenClothingProductsUseCase = GetMenClothingProductsUseCase(repository),
            getWomenClothingProductsUseCase = GetWomenClothingProductsUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun providesProductDetailUseCase(repository: ProductRepository): GetProductDetailUseCase{
        return GetProductDetailUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesCartDatabase(app: Application): CartDatabase{
        return Room.databaseBuilder(
            app,
            CartDatabase::class.java,
            "cart_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesCartRepository(db: CartDatabase): CartRepository{
        return CartRepositoryImpl(db.dao)
    }

    @Provides
    @Singleton
    fun provideCartUseCases(repository: CartRepository): CartUseCases{
        return CartUseCases(
            insertCart = InsertCart(repository),
            deleteCart = DeleteCart(repository),
            getAllCarts = GetAllCarts(repository),
            getCartById = GetCartById(repository),
            deleteAll = DeleteAll(repository)
        )
    }





}