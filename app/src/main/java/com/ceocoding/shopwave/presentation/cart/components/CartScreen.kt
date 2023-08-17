package com.ceocoding.shopwave.presentation.cart.components

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ceocoding.shopwave.R
import com.ceocoding.shopwave.presentation.cart.CartEvent
import com.ceocoding.shopwave.presentation.cart.CartViewModel
import com.ceocoding.shopwave.utils.UiEvent

@Composable
fun CartScreen(
    scaffoldState: ScaffoldState,
    onPopBackStack: () -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: CartViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val cartItemsState by viewModel.cartItems.collectAsState()

    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event){
                is UiEvent.ShowSnackBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context),
                        actionLabel = event.action
                    )
                    if (result == SnackbarResult.ActionPerformed){
                        viewModel.onEvent(CartEvent.OnUndoDeleteClick)
                    }
                }
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.PopBackStack -> onPopBackStack()
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        Column {
            TopBarSection()
            CartSection()
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        when{
            cartItemsState.isEmpty() ->{
                Text(
                    text = stringResource(id = R.string.no_items_in_cart),
                    textAlign = TextAlign.Center
                )
            }
        }

    }


}

@Composable
fun TopBarSection() {
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top,
        modifier = Modifier.padding(8.dp)
    ){
        IconButton(
            onClick = { onBackPressedDispatcher?.onBackPressed() },
            modifier = Modifier
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = CircleShape
                )
                .padding(10.dp)
                .size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back btn"
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = R.string.cart),
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CartSection(viewModel: CartViewModel = hiltViewModel()) {
    val cartItemsState by viewModel.cartItems.collectAsState()
    val totalPriceState by viewModel.totalPrice.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize()){
        items(cartItemsState){productItem ->
            CartItem(
                productItem = productItem,
                onDeleteClick = { viewModel.onEvent(CartEvent.OnDeleteCartClick(productItem)) },
                quantity = viewModel.getItemQuantity(productItem.id),
                onPlusClick = {viewModel.incrementQuantity(productItem.id)},
                onMinusClick = {viewModel.decrementQuantity(productItem.id)}
            )
        }
        if (cartItemsState.isNotEmpty()){
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(id = R.string.cart_total_price, totalPriceState),
                    style = MaterialTheme.typography.h1,
                    textAlign = TextAlign.End,
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { viewModel.onEvent(CartEvent.OnCheckOutClick) },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    contentPadding = PaddingValues(10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.check_out),
                        color = Color.White,
                        style = MaterialTheme.typography.body1
                    )
                }
            }


        }
    }




}



