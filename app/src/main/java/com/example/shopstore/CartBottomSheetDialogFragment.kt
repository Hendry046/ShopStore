package com.example.shopstore

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.shopstore.Product
import com.example.shopstore.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CartBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var totalAmountTextView: TextView
    private lateinit var viewCartText: TextView

    private lateinit var productList: List<Product> // Property to store the product list
    private var currentCategory: String = "Home" // Property to store the current category

    // Setter method to set the product list
    fun setProductList(productList: List<Product>) {
        this.productList = productList
        // Ensure that totalAmountTextView is initialized before updating its text
        if (::totalAmountTextView.isInitialized) {
            updateTotalAmount()
        }
    }

    // Setter method to set the current category
    fun setCategory(category: String) {
        this.currentCategory = category
        // Update the total amount when the category is changed
        updateTotalAmount()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize your views using findViewById
        totalAmountTextView = view.findViewById(R.id.totalAmountValue)
        viewCartText = view.findViewById(R.id.viewCartText)

        // Log to check if the TextView is found
        Log.d("CartFragment", "totalAmountTextView initialized: $totalAmountTextView")

        // Set click listener for "View Cart" button
        viewCartText.setOnClickListener {
            // Handle the "View Cart" button click (e.g., navigate to the cart activity)
            dismiss()
        }
    }

    // Function to calculate and update the total amount
    fun updateTotalAmount() {
        // Calculate the total amount based on the products in the cart for the current category
        var cartTotal = 0.0

        // Ensure that the productList is not null before looping through it
        if (::productList.isInitialized) {
            for (product in productList) {
                // Convert product.category to string for comparison
                val productCategory = product.category.toString()

                if (productCategory == currentCategory && product.quantityInCart > 0) {
                    // Extract the numeric part of the price string and parse it to a double
                    val priceString = product.price.replace("Rs. ", "")
                    val price = priceString.toDouble()
                    cartTotal += price * product.quantityInCart
                }
            }
        }

        // Update the total amount TextView
        totalAmountTextView.text = "Total Amount: Rs. $cartTotal"
    }
}
