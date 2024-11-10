package com.dicoding.jetreward.di

import com.dicoding.jetreward.data.ProductRepository


object Injection {
    fun provideRepository(): ProductRepository {
        return ProductRepository.getInstance()
    }
}