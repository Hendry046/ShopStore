package com.example.shopstore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.shopstore.Product
import com.example.shopstore.R

class ProductAdapter(
    private val context: Context,
    private var productList: List<Product>,
    private val showCartCallback: (Double) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    // ViewHolder class to hold the views within the item layout
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define the views within the item layout
        val productImage: ImageView = itemView.findViewById(R.id.productImage)
        val productName: TextView = itemView.findViewById(R.id.productName)
        val productQuantity: TextView = itemView.findViewById(R.id.productQuantity)
        val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        val addButton: Button = itemView.findViewById(R.id.addButton)
        val incrementButton: Button = itemView.findViewById(R.id.incrementButton)
        val decrementButton: Button = itemView.findViewById(R.id.decrementButton)
        val quantityDisplay: TextView = itemView.findViewById(R.id.quantityDisplay)
        val switcher: ViewSwitcher = itemView.findViewById(R.id.switcher)
        val incrementDecrementLayout: LinearLayout =
            itemView.findViewById(R.id.incrementDecrementLayout)

        // Bind data to views within the ViewHolder
        fun bind(product: Product) {
            // Bind data to views here
            productName.text = product.name
            productQuantity.text = product.quantity
            productPrice.text = product.price

            // Check if the item is in "ADD" mode or "INCREMENT/DECREMENT" mode
            val isAddMode = product.quantityInCart == 0

            // Set visibility and click listeners based on the mode
            if (isAddMode) {
                switcher.displayedChild = 0  // Show "+ADD" button
                addButton.setOnClickListener {
                    // Switch to "INCREMENT/DECREMENT" mode instantly with a minimum quantity of 1
                    product.quantityInCart = 1
                    switcher.displayedChild = 1
                    quantityDisplay.text = "1" // Set the quantity text to "1"
                    updateTotalAmount()
                }
            } else {
                switcher.displayedChild = 1  // Show increment and decrement buttons
                val currentQuantity = product.quantityInCart

                // Ensure that the quantity text is never "0"
                if (currentQuantity == 0) {
                    quantityDisplay.text = "1"
                    product.quantityInCart = 1
                } else {
                    quantityDisplay.text = currentQuantity.toString()
                }

                incrementButton.setOnClickListener {
                    handleQuantityChange(product, true)
                }
                decrementButton.setOnClickListener {
                    handleQuantityChange(product, false)
                }
            }

            // Set the correct image resource based on the product
            productImage.setImageResource(product.imageResourceId)
        }


        // Handle quantity change when the user clicks on "+" or "-" buttons
        private fun handleQuantityChange(product: Product, increment: Boolean) {
            val currentQuantity = quantityDisplay.text.toString().toInt()
            val newQuantity = if (increment) currentQuantity + 1 else currentQuantity - 1

            if (newQuantity >= 0) {
                quantityDisplay.text = newQuantity.toString()
                product.quantityInCart = newQuantity

                if (newQuantity == 0) {
                    // Switch back to "ADD" mode if quantity is 0
                    switcher.displayedChild = 0
                }

                // Update the total amount when the quantity changes
                updateTotalAmount()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the item layout
        val view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Bind data to the ViewHolder
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        // Return the total number of items in the list
        return productList.size
    }

    // Calculate and update the total amount of products in the cart
    private fun updateTotalAmount() {
        // Calculate the total amount and invoke the callback to show it
        val totalAmount = calculateTotalAmount()
        showCartCallback(totalAmount)
    }

    // Calculate the total amount by iterating through the product list
    private fun calculateTotalAmount(): Double {
        var cartTotal = 0.0
        for (product in productList) {
            val priceString = product.price.replace("Rs. ", "")
            cartTotal += priceString.toDouble() * product.quantityInCart
        }
        return cartTotal
    }

    // Update the product list and notify the adapter of the changes
    fun updateProductList(newProductList: List<Product>) {
        productList = newProductList
        notifyDataSetChanged()
    }
}
