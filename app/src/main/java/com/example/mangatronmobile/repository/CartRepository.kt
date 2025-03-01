package com.example.mangatronmobile.repository

import com.example.mangatronmobile.model.CartModel

interface CartRepository {

    fun addToCart(cartModel: CartModel, callback: (Boolean, String) -> Unit)

    fun removeFromCart(cartId: String, callback: (Boolean, String) -> Unit)

    fun updateCartItem(cartId: String, quantity: Int, callback: (Boolean, String) -> Unit)

    fun getCartItems(userId: String, callback: (List<CartModel>?, Boolean, String) -> Unit)
}