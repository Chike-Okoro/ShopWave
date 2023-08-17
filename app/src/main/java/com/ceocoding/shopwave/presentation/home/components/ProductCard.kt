package com.ceocoding.shopwave.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ceocoding.shopwave.domain.model.ProductItem
import java.util.*

@Composable
fun ProductCard(
    productItem: ProductItem,
    onItemClick: (ProductItem) -> Unit
) {
    Column {
        Card(
            elevation = 5.dp,
            modifier = Modifier
                .size(200.dp)
                .clickable { onItemClick(productItem) },
            shape = RoundedCornerShape(15.dp),
            backgroundColor = Color.White
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(productItem.image)
                        .crossfade(true)
                        .build(),
                    contentDescription = "ImageCard",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(10.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = productItem.title,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = productItem.category.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
            fontSize = 12.sp,
            color = Color.Gray,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$" + productItem.price.toString(),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(text = "Rating: ${productItem.rating.rate}")
        }

    }
    
}
