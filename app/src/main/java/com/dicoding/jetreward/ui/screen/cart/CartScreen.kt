package com.dicoding.jetreward.ui.screen.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.jetreward.R
import com.dicoding.jetreward.di.Injection
import com.dicoding.jetreward.model.OrderProduct
import com.dicoding.jetreward.model.Product
import com.dicoding.jetreward.ui.ViewModelFactory
import com.dicoding.jetreward.ui.common.UiState
import com.dicoding.jetreward.ui.components.CartItem
import com.dicoding.jetreward.ui.components.OrderButton
import com.dicoding.jetreward.ui.theme.LightGreen

@Composable
fun CartScreen(
    viewModel: CartViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    onOrderButtonClicked: (String) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAddedOrderRewards()
            }

            is UiState.Success -> {
                CartContent(
                    uiState.data,
                    onProductCountChanged = { productId, count ->
                        viewModel.updateOrderReward(productId, count)
                    },
                    onOrderButtonClicked = onOrderButtonClicked
                )
            }

            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartContent(
    state: CartState,
    onProductCountChanged: (id: Long, count: Int) -> Unit,
    onOrderButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val shareMessage = stringResource(
        R.string.share_message,
        state.orderProduct.count(),
        state.totalprice
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightGreen)
    ) {
//        CenterAlignedTopAppBar(
//            title = {
//                Text(
//                    text = stringResource(R.string.menu_cart),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 12.dp),
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 18.sp,
//                    textAlign = TextAlign.Center,
//                    color = Color.White
//                )
//            }
//        )
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(weight = 1f)
        ) {
            items(state.orderProduct, key = { it.product.productId }) { item ->
                CartItem(
                    productId = item.product.productId,
                    image = item.product.image,
                    title = item.product.title,
                    totalPoint = item.product.price * item.count,
                    count = item.count,
                    onProductCountChanged = onProductCountChanged,
                )
                Divider(color = Color.White)
            }
        }
        OrderButton(
            text = stringResource(R.string.total_order, state.totalprice),
            enabled = state.orderProduct.isNotEmpty(),
            onClick = {
                onOrderButtonClicked(shareMessage)
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    val sampleState = CartState(
        orderProduct = listOf(
            OrderProduct(
                product = Product(
                    productId = 1,
                    title = "Reusable Bamboo Toothbrush",
                    image = R.drawable.product_1,
                    price = 35000,
                    description = "A sustainable toothbrush made from bamboo. This eco-friendly toothbrush is biodegradable and helps reduce plastic waste. The soft bristles are gentle on your gums and provide effective cleaning."
                ),
                count = 2
            ),
            OrderProduct(
                product = Product(
                    productId = 2,
                    title = "Eco-friendly Stainless Steel Water Bottle",
                    image = R.drawable.product_2,
                    price = 70000,
                    description = "A reusable water bottle made from high-quality stainless steel. It keeps your drinks cold for up to 24 hours and hot for up to 12 hours. Perfect for staying hydrated on the go while reducing single-use plastic bottles."
                ),
                count = 1
            )
        ),
        totalprice = 140000
    )

    CartContent(
        state = sampleState,
        onProductCountChanged = { _, _ -> },
        onOrderButtonClicked = {}
    )
}