package com.ceocoding.shopwave

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ceocoding.shopwave.presentation.cart.components.CartScreen
import com.ceocoding.shopwave.presentation.check_out.CheckOutScreen
import com.ceocoding.shopwave.presentation.detail.components.ProductDetailScreen
import com.ceocoding.shopwave.presentation.home.components.*
import com.ceocoding.shopwave.ui.theme.ShopWaveTheme
import com.ceocoding.shopwave.utils.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShopWaveTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val scaffoldState = rememberScaffoldState()
                    val navController = rememberNavController()
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        scaffoldState = scaffoldState
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = Screen.HomeScreen.route
                        ){
                            composable(route = Screen.HomeScreen.route){
                                HomeScreen(navController = navController)
                            }
                            composable(
                                route = Screen.ProductDetailScreen.route + "/{productId}",
                                arguments = listOf(navArgument("productId") {type = NavType.IntType})
                            ){
                                ProductDetailScreen(
                                    scaffoldState = scaffoldState,
                                    onNavigate = {navController.navigate(it.route)}
                                )
                            }
                            composable(route = Screen.CartScreen.route){
                                CartScreen(
                                    scaffoldState = scaffoldState,
                                    onPopBackStack = {navController.popBackStack()},
                                    onNavigate = {navController.navigate(it.route)},
                                )
                            }
                            composable(route = Screen.CheckOutScreen.route){
                                CheckOutScreen(
                                    onNavigate = {navController.navigate(it.route)},
                                    onPopBackStack = {navController.popBackStack()}
                                )
                            }
                        }

                    }


                }
            }
        }
    }
}

