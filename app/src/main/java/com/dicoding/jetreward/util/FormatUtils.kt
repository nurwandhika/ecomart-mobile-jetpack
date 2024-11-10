package com.dicoding.jetreward.util

import java.text.NumberFormat
import java.util.Locale

fun formatRupiah(number: Int): String {
    val format = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    return format.format(number)
}