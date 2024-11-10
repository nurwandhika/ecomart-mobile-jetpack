package com.dicoding.jetreward.ui.screen.cart

import com.dicoding.jetreward.model.OrderProduct

data class CartState(
    val orderProduct: List<OrderProduct>,
    val totalprice: Int
)