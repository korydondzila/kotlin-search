package com.example.kotlin_search

import androidx.annotation.DrawableRes

data class CountryItem(
    val _id: Int,
    val title: String,
    @DrawableRes val image: Int?
)
