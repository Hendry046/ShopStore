//package com.example.shopstore
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.DialogFragment
//import kotlinx.android.synthetic.main.bottom_sheet_cart.*
//
//
//class bottom_sheet_cart : DialogFragment() {
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.bottom_sheet_cart, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Set the total amount in the TextView
//        totalAmountTextView.text = "Total Amount: Rs. ${calculateTotalAmount()}"
//
//        // Set click listener for "View Cart" button
//        viewCartButton.setOnClickListener {
//            // Handle the "View Cart" button click (e.g., navigate to the cart activity)
//            dismiss()
//        }
//    }
//
//    // Replace this with your logic to calculate the total amount
//    private fun calculateTotalAmount(): Double {
//        // Calculate the total amount based on the products in the cart
//        // Return the total amount as a Double
//        return 0.00
//    }
//}
