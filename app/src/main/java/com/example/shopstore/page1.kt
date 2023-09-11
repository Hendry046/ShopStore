package com.example.shopstore

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopstore.CartBottomSheetDialogFragment

class page1 : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var searchEditText: EditText

    private val categoryMap = mutableMapOf<String, MutableList<Product>>()
    private var selectedCategory = "Home"

    private lateinit var cartFragment: CartBottomSheetDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page1)

        val dialButton: Button = findViewById(R.id.contactButton)
        dialButton.setOnClickListener {
            val phoneNumber = "7710948927"
            val uri = Uri.parse("tel:$phoneNumber")
            val intent = Intent(Intent.ACTION_VIEW, uri)

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                // Handle the case where no app is available to handle the intent
                // (e.g., the device doesn't have a dialer app)
                // You can display a toast message or perform other actions.
            }
        }

        recyclerView = findViewById(R.id.recyclerView)

        // Initialize categoryMap with product lists for different categories
        categoryMap["Home"] = mutableListOf(
            Product("Table", "4 Set", "Rs. 800.00", R.drawable.table, "Home"),
            Product("Pillow", "1 Pair", "Rs. 500.00", R.drawable.pillow, "Home"),
            Product("Bed Sheet", "4 Sheets", "Rs. 750.00", R.drawable.bedsheet, "Home"),
            Product("Mattress", "1 Pair", "Rs. 1000.00", R.drawable.mattress, "Home"),
            Product("Window Sheets", "1 Pair", "Rs. 500.00", R.drawable.winsheet, "Home"),
            Product("Chair", "1 Pair", "Rs. 800.00", R.drawable.chair, "Home")
        )
        categoryMap["Kitchen"] = mutableListOf(
            Product("Blender", "600W", "Rs. 4500.00", R.drawable.blender, "Kitchen"),
            Product("Coffee Maker", "1L", "Rs. 1500.00", R.drawable.coffeemaker, "Kitchen"),
            Product("Cutting Board", "1 board", "Rs. 250.00", R.drawable.cutting, "Kitchen"),
            Product("Knife Set", "1 set", "Rs. 800.00", R.drawable.knifeset, "Kitchen"),
            Product("Spoon Set", "1 set", "Rs. 500.00", R.drawable.spoon, "Kitchen"),
            Product("Grater", "1 peice", "Rs. 200.00", R.drawable.grater, "Kitchen")
        )
        categoryMap["Electronics"] = mutableListOf(
            Product("Smartphone", "128GB", "Rs. 35000.00", R.drawable.phone, "Electronics"),
            Product("Laptop", "15-inch", "Rs. 55000.00", R.drawable.laptop, "Electronics"),
            Product("Earbuds", "1 bud", "Rs. 3000.00", R.drawable.ear, "Electronics"),
            Product("Smart Watch", "Android", "Rs. 10000.00", R.drawable.watch, "Electronics"),
            Product("Smart Ring", "Bluetooth", "Rs. 8000.00", R.drawable.ring, "Electronics"),
            Product("Wifi Router", "2.0 Ghz", "Rs. 1200.00", R.drawable.wifi, "Electronics")
        )
        categoryMap["Others"] = mutableListOf(
            Product("Backpack", "Large", "Rs. 1200.00", R.drawable.bag, "Others"),
            Product("Sunglasses", "UV Protection", "Rs. 500.00", R.drawable.glasses, "Others"),
            Product("Marvel T-Shirt", "Cotton", "Rs. 600.00", R.drawable.tshirt, "Others"),
            Product("Laptop Bag", "Leather", "Rs. 1000.00", R.drawable.lapbag, "Others"),
            Product("Flip FLops", "Bata", "Rs. 800.00", R.drawable.flipflop, "Others"),
            Product("Photo Frame", "Wooden", "Rs. 500.00", R.drawable.frame, "Others")
        )

        // Initialize the adapter with the Home category
        adapter = ProductAdapter(this, categoryMap[selectedCategory] ?: mutableListOf(), ::showCart)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize the search bar
        searchEditText = findViewById(R.id.searchEditText)

        // Add a TextWatcher to filter the product list based on user input
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not used in this example
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                filterProducts(query)
            }

            override fun afterTextChanged(s: Editable?) {
                // Not used in this example
            }
        })

        // Step 1: Create an instance of CartBottomSheetDialogFragment
        cartFragment = CartBottomSheetDialogFragment()

        // Step 2: Set the initial product list (Home category) using the setProductList method
        cartFragment.setProductList(categoryMap[selectedCategory] ?: mutableListOf())

        // Step 3: Show the CartBottomSheetDialogFragment
        cartFragment.show(supportFragmentManager, cartFragment.tag)

        // Connect the category TextViews to switch categories
        val categoryHomeTextView: TextView = findViewById(R.id.categoryHome)
        val categoryKitchenTextView: TextView = findViewById(R.id.categoryKitchen)
        val categoryElectronicsTextView: TextView = findViewById(R.id.categoryElectronics)
        val categoryOthersTextView: TextView = findViewById(R.id.categoryOthers)

        categoryHomeTextView.setOnClickListener {
            switchCategory("Home")
        }

        categoryKitchenTextView.setOnClickListener {
            switchCategory("Kitchen")
        }

        categoryElectronicsTextView.setOnClickListener {
            switchCategory("Electronics")
        }

        categoryOthersTextView.setOnClickListener {
            switchCategory("Others")
        }
    }

    // Callback function to show the cart
    private fun showCart(totalAmount: Double) {
        // Calculate the total amount and invoke the callback to show it
        cartFragment.setProductList(categoryMap[selectedCategory] ?: mutableListOf())

        // Show the cart and pass the total amount
        if (!cartFragment.isVisible) {
            cartFragment.updateTotalAmount() // Pass the total amount here
            cartFragment.show(supportFragmentManager, cartFragment.tag)
        }
    }

    // Function to filter products based on the search query
    private fun filterProducts(query: String) {
        val filteredList = if (query.isEmpty()) {
            // If the query is empty, show all products of the selected category
            categoryMap[selectedCategory] ?: mutableListOf()
        } else {
            // Filter products that match the query (case-insensitive)
            (categoryMap[selectedCategory] ?: mutableListOf()).filter { product ->
                product.name.contains(query, ignoreCase = true)
            }
        }

        // Update the adapter with the filtered list
        adapter.updateProductList(filteredList)
    }

    // Function to calculate the total amount
    private fun calculateTotalAmount(): Double {
        val productList = categoryMap[selectedCategory] ?: mutableListOf()
        var cartTotal = 0.0

        for (product in productList) {
            if (product.quantityInCart > 0) {
                val priceString = product.price.replace("Rs. ", "")
                val price = priceString.toDouble()
                cartTotal += price * product.quantityInCart
            }
        }

        return cartTotal
    }

    // Function to switch the category
    private fun switchCategory(category: String) {
        // Update the selected category
        selectedCategory = category

        // Get the product list for the selected category
        val categoryProducts = categoryMap[category] ?: mutableListOf()

        // Update the adapter with the new category's product list
        adapter.updateProductList(categoryProducts)

        // Update the CartBottomSheetDialogFragment with the new product list
        cartFragment.setCategory(category)
        cartFragment.setProductList(categoryProducts)
    }
}
