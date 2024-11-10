package com.dicoding.jetreward.data

import com.dicoding.jetreward.model.FakeProductDataSource
import com.dicoding.jetreward.model.OrderProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class ProductRepository {

    private val orderProducts = mutableListOf<OrderProduct>()

    init {
        if (orderProducts.isEmpty()) {
            FakeProductDataSource.dummyProducts.forEach {
                orderProducts.add(OrderProduct(it, 0))
            }
        }
    }

    fun getAllRewards(): Flow<List<OrderProduct>> {
        return flowOf(orderProducts)
    }

    fun getOrderRewardById(productId: Long): OrderProduct {
        return orderProducts.first {
            it.product.productId == productId
        }
    }

    fun updateOrderReward(productId: Long, newCountValue: Int): Flow<Boolean> {
        val index = orderProducts.indexOfFirst { it.product.productId == productId }
        val result = if (index >= 0) {
            val orderReward = orderProducts[index]
            orderProducts[index] =
                orderReward.copy(product = orderReward.product, count = newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderRewards(): Flow<List<OrderProduct>> {
        return getAllRewards()
            .map { orderRewards ->
                orderRewards.filter { orderReward ->
                    orderReward.count != 0
                }
            }
    }

    companion object {
        @Volatile
        private var instance: ProductRepository? = null

        fun getInstance(): ProductRepository =
            instance ?: synchronized(this) {
                ProductRepository().apply {
                    instance = this
                }
            }
    }
}