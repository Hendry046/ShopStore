package com.example.shopstore

data class Product(
    val name: String,
    val quantity: String,
    val price: String,
    val imageResourceId: Int, // Use this for product image (e.g., R.drawable.product1)
    val category: String, // Add a category property if needed
    var quantityInCart: Int = 0 // Default to 0, indicating not in cart
)
