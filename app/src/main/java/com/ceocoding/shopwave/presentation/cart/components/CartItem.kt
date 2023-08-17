package com.ceocoding.shopwave.presentation.cart.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ceocoding.shopwave.R
import com.ceocoding.shopwave.domain.model.ProductItem
import com.ceocoding.shopwave.presentation.cart.CartViewModel

@Composable
fun CartItem(
    productItem: ProductItem,
    onDeleteClick: () -> Unit,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit,
    quantity: Int
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(productItem.image)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.size(68.dp)
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = productItem.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.h2
            )
            Text(
                text = "$${productItem.price * quantity}",
                style = MaterialTheme.typography.body1
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Black
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = onMinusClick,
                    shape = CircleShape,
                    modifier = Modifier
                        .alignBy(LastBaseline)
                        .size(30.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = stringResource(id = R.string.remove),
                        tint = Color.White
                    )
                }
                Text(
                    text = quantity.toString(),
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier
                        .alignBy(LastBaseline)
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Center,
                )
                OutlinedButton(
                    onClick = onPlusClick,
                    shape = CircleShape,
                    modifier = Modifier
                        .alignBy(LastBaseline)
                        .size(30.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.add),
                        tint = Color.White
                    )
                }
            }

        }
    }


}

