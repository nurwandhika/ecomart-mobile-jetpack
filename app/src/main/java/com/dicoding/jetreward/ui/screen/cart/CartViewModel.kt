package com.dicoding.jetreward.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.jetreward.data.ProductRepository
import com.dicoding.jetreward.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: ProductRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<CartState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<CartState>>
        get() = _uiState

    fun getAddedOrderRewards() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedOrderRewards()
                .collect { orderReward ->
                    val totalprice =
                        orderReward.sumOf { it.product.price * it.count }
                    _uiState.value = UiState.Success(CartState(orderReward, totalprice))
                }
        }
    }

    fun updateOrderReward(productId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateOrderReward(productId, count)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedOrderRewards()
                    }
                }
        }
    }
}