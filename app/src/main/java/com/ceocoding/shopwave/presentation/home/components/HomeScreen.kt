package com.ceocoding.shopwave.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ceocoding.shopwave.R
import com.ceocoding.shopwave.presentation.cart.CartViewModel
import com.ceocoding.shopwave.presentation.home.*
import com.ceocoding.shopwave.utils.Screen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ){
        Column {
            GreetingSection(navController = navController)
            ChipSection(navController = navController)
        }

    }

}

@Composable
fun GreetingSection(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel()
) {
    val cartItemsState by viewModel.cartItems.collectAsState()
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = "Welcome to ShopWave",
                style = MaterialTheme.typography.h2
            )
            Text(
                text = "Your Ultimate Shopping Companion",
                style = MaterialTheme.typography.body1
            )
        }
        if (cartItemsState.isNotEmpty()){
            BadgedBox(
                badge = {
                    Badge(
                        content = {
                            Text(text = "${cartItemsState.size}")
                        }
                    )
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.cart_icon),
                    contentDescription = stringResource(id = R.string.cart),
                    tint = Color.Black,
                    modifier = Modifier
                        .size(29.dp)
                        .clickable { navController.navigate(Screen.CartScreen.route) }
                )
            }
        }
        else {
            Icon(
                painter = painterResource(id = R.drawable.cart_icon),
                contentDescription = stringResource(id = R.string.cart),
                tint = Color.Black,
                modifier = Modifier
                    .size(29.dp)
                    .clickable { navController.navigate(Screen.CartScreen.route) }
            )
        }
    }
}

@Composable
fun ChipSection(navController: NavController) {

    val chips = listOf("All Items", "Men Clothing", "Women Clothing", "Electronics", "Jewelery")
    var selectedChip by rememberSaveable { mutableStateOf(0) }

    LazyRow{
        items(chips.size){
            Box(
                modifier = Modifier
                    .padding(start = 15.dp, top = 15.dp, bottom = 15.dp, end = 8.dp)
                    .clickable {
                        selectedChip = it
                    }
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .background(
                        if (selectedChip == it) Color.Black
                        else Color.White
                    )
                    .padding(15.dp)
            ){
                Text(
                    text = chips[it],
                    color = if (selectedChip == it) Color.White else Color.Black
                )
            }
        }
        
    }

    when(selectedChip){
        0 -> AllItemsScreen(navController = navController)
        1 -> MenClothingScreen(navController = navController)
        2 -> WomenClothingScreen(navController = navController)
        3 -> ElectronicsScreen(navController = navController)
        4 -> JeweleryScreen(navController = navController)
    }

}

@Composable
fun AllItemsScreen(
    viewModel: AllProductsViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.value
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ){
            items(state.products){ product ->
                ProductCard(
                    productItem = product,
                    onItemClick = {navController.navigate(Screen.ProductDetailScreen.route + "/${product.id}")}
                )
            }
        }
        if(state.error.isNotBlank()){
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if(state.isLoading){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

    }

}

@Composable
fun MenClothingScreen(
    viewModel: MenClothingViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.value
    Box(modifier = Modifier.fillMaxSize()){
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            items(state.products){ product ->
                ProductCard(
                    productItem = product,
                    onItemClick = {navController.navigate(Screen.ProductDetailScreen.route + "/${product.id}")}
                )
            }
        }
        if(state.error.isNotBlank()){
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if(state.isLoading){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

    }

}

@Composable
fun WomenClothingScreen(
    viewModel: WomenClothingViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.value
    Box(modifier = Modifier.fillMaxSize()){
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            items(state.products){ product ->
                ProductCard(
                    productItem = product,
                    onItemClick = {navController.navigate(Screen.ProductDetailScreen.route + "/${product.id}")}
                )
            }
        }
        if(state.error.isNotBlank()){
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if(state.isLoading){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

    }

}

@Composable
fun ElectronicsScreen(
    viewModel: ElectronicsViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.value
    Box(modifier = Modifier.fillMaxSize()){
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            items(state.products){ product ->
                ProductCard(
                    productItem = product,
                    onItemClick = {navController.navigate(Screen.ProductDetailScreen.route + "/${product.id}")}
                )
            }
        }
        if(state.error.isNotBlank()){
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if(state.isLoading){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

    }
}

@Composable
fun JeweleryScreen(
    viewModel: JeweleryViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.value
    Box(modifier = Modifier.fillMaxSize()){
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            items(state.products){ product ->
                ProductCard(
                    productItem = product,
                    onItemClick = {navController.navigate(Screen.ProductDetailScreen.route + "/${product.id}")}
                )
            }
        }
        if(state.error.isNotBlank()){
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if(state.isLoading){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

    }

}