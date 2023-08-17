package com.ceocoding.shopwave.presentation.check_out

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ceocoding.shopwave.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ceocoding.shopwave.utils.UiEvent

@Composable
fun CheckOutScreen(
    modifier: Modifier = Modifier,
    onNavigate:(UiEvent.Navigate) -> Unit,
    onPopBackStack: () -> Unit,
    viewModel: CheckOutViewModel = viewModel()
) {
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event){
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }

        }
    }
    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.thanks_message),
                style = MaterialTheme.typography.h1,
                modifier = Modifier.padding(horizontal = 20.dp),
                textAlign = TextAlign.Center,
                lineHeight = 30.sp
            )
            Button(
                onClick = {viewModel.onEvent(CheckOutEvent.OnButtonClick)},
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                contentPadding = PaddingValues(10.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.continue_shopping),
                    style = MaterialTheme.typography.body1,
                    color = Color.White
                )
            }
        }

    }


}