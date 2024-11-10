package com.dicoding.jetreward.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.jetreward.data.ProductRepository
import com.dicoding.jetreward.model.OrderProduct
import com.dicoding.jetreward.model.Product
import com.dicoding.jetreward.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailProductViewModel(
    private val repository: ProductRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderProduct>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderProduct>>
        get() = _uiState

    fun getRewardById(productId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderRewardById(productId))
        }
    }

    fun addToCart(product: Product, count: Int) {
        viewModelScope.launch {
            repository.updateOrderReward(product.productId, count)
        }
    }
}