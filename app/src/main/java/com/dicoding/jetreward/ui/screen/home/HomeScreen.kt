package com.dicoding.jetreward.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.jetreward.di.Injection
import com.dicoding.jetreward.model.OrderProduct
import com.dicoding.jetreward.ui.ViewModelFactory
import com.dicoding.jetreward.ui.common.UiState
import com.dicoding.jetreward.ui.components.ProductItem
import com.dicoding.jetreward.ui.theme.DarkGreen
import com.dicoding.jetreward.ui.theme.LightGreen
import com.dicoding.jetreward.ui.theme.OliveGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllRewards()
            }

            is UiState.Success -> {
                Column(
                    modifier = modifier
                        .background(LightGreen)
                        .fillMaxSize()
                ) {
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        label = { Text("Search") },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = LightGreen,
                            unfocusedContainerColor = OliveGreen,
                            disabledContainerColor = OliveGreen,
                            focusedIndicatorColor = DarkGreen,
                            unfocusedIndicatorColor = LightGreen,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                    HomeContent(
                        orderProduct = uiState.data.filter {
                            it.product.title.contains(searchQuery, ignoreCase = true)
                        },
                        navigateToDetail = navigateToDetail,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    orderProduct: List<OrderProduct>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(130.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.testTag("RewardList")
    ) {
        items(orderProduct) { data ->
            Card(
                modifier = Modifier
                    .padding(2.dp)
                    .clickable { navigateToDetail(data.product.productId) },
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = OliveGreen)
            ) {
                ProductItem(
                    image = data.product.image,
                    title = data.product.title,
                    price = data.product.price,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        navigateToDetail = {}
    )
}