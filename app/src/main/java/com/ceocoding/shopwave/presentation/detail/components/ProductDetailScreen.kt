package com.ceocoding.shopwave.presentation.detail.components

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ceocoding.shopwave.R
import com.ceocoding.shopwave.presentation.detail.ProductDetailEvent
import com.ceocoding.shopwave.presentation.detail.ProductDetailViewModel
import com.ceocoding.shopwave.utils.UiEvent

@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState,
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    val context = LocalContext.current
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event){
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context),
                        actionLabel = event.action
                    )
                }
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }
    val state = viewModel.state.value
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        state.productItem?.let { productItem ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 100.dp),
                verticalArrangement = Arrangement.Top
            ){
                item {
                    Card(
                        elevation = 5.dp,
                        modifier = Modifier
                            .size(380.dp),
                        shape = RoundedCornerShape(15.dp),
                        backgroundColor = Color.White
                    ) {
                        Box(modifier = Modifier.fillMaxSize()){
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(productItem.image)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "ImageCard",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(
                                        top = 30.dp,
                                        start = 30.dp,
                                        bottom = 30.dp,
                                        end = 30.dp
                                    )
                            )
                            IconButton(
                                onClick = { onBackPressedDispatcher?.onBackPressed() },
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(10.dp)
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

                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = productItem.title,
                        style = MaterialTheme.typography.h1,
                        lineHeight = 30.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "$" + "${productItem.price}",
                        style = MaterialTheme.typography.body1,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.h1
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = productItem.description,
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.body1,
                        lineHeight = 20.sp
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { viewModel.onEvent(
                        ProductDetailEvent.OnAddToCartClick(
                            productItem = productItem
                        )
                    ) },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.padding(20.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.cart_icon),
                        contentDescription = "Cart",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Add to Cart",
                        style = MaterialTheme.typography.body1,
                        color = Color.White
                    )
                }
                Button(
                    onClick = {
                    viewModel.onEvent(
                        ProductDetailEvent.OnProceedToCheckoutScreen(
                            productItem = productItem
                        )
                    )
                },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.padding(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Cart",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Proceed to Checkout",
                        style = MaterialTheme.typography.body1,
                        color = Color.White
                    )
                }

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