package com.example.mangatronmobile.ui.fragment

import ProductAdapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mangatron.repository.ProductRepository
import com.example.mangatronmobile.databinding.FragmentMerchBinding
import com.example.mangatronmobile.model.CartModel
import com.example.mangatronmobile.model.ProductModel
import com.example.mangatronmobile.model.WishlistModel
import com.example.mangatronmobile.repository.CartRepository
import com.example.mangatronmobile.repository.CartRepositoryImpl
import com.example.mangatronmobile.repository.ProductRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MerchFragment : Fragment() {

    lateinit var binding: FragmentMerchBinding
    lateinit var productAdapter: ProductAdapter
    lateinit var productRepository: ProductRepository
    lateinit var cartRepository: CartRepository
    var productList = ArrayList<ProductModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMerchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productRepository = ProductRepositoryImpl()
        cartRepository = CartRepositoryImpl()

        productAdapter =
            ProductAdapter(requireContext(), productList, { product -> addToCart(product) }, { product -> addToWishlist(product) })

        binding.merchRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
        }

        // Fetch Merch products
        fetchMerch()
    }

    private fun fetchMerch() {
        productRepository.getProductByCategory("Merch") { products, success, message ->
            if (success && products != null) {
                productList.clear()
                productList.addAll(products)
                productAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun addToCart(product: ProductModel) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val cartRef = FirebaseDatabase.getInstance().getReference("Cart").child(userId ?: return)

        product.productId?.let { productId ->  // Ensure productId is non-null
            cartRef.child(productId).get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val existingCartItem = snapshot.getValue(CartModel::class.java)
                    val updatedQuantity = (existingCartItem?.quantity ?: 0) + 1
                    cartRef.child(productId).child("quantity").setValue(updatedQuantity)
                } else {
                    val newCartItem = CartModel(
                        cartId = System.currentTimeMillis().toString(),
                        productId = productId,
                        productName = product.productName ?: "Unknown",
                        productImage = product.productImage ?: "",
                        price = product.productPrice ?: 0,
                        quantity = 1
                    )
                    cartRef.child(productId).setValue(newCartItem)
                }
                Toast.makeText(requireContext(), "Added to cart", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to add to cart", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addToWishlist(product: ProductModel) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        val wishlistRef = FirebaseDatabase.getInstance().getReference("Wishlist").child(userId)

        product.productId?.let { productId ->
            wishlistRef.child(productId).get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        Toast.makeText(requireContext(), "Already in Wishlist", Toast.LENGTH_SHORT).show()
                        println("DEBUG: Item already exists in Wishlist")
                    } else {
                        val wishlistId = System.currentTimeMillis().toString()
                        val newWishlistItem = WishlistModel(
                            wishlistId = wishlistId,
                            productId = productId,
                            productName = product.productName ?: "Unknown",
                            productImage = product.productImage ?: "",
                        )

                        wishlistRef.child(wishlistId).setValue(newWishlistItem)
                            .addOnSuccessListener {
                                Toast.makeText(requireContext(), "Added to Wishlist", Toast.LENGTH_SHORT).show()
                                println("DEBUG: Successfully added to Wishlist")
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(requireContext(), "Failed to add to Wishlist", Toast.LENGTH_SHORT).show()
                                println("DEBUG: Error adding to Wishlist: ${e.message}")
                            }
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Failed to check Wishlist", Toast.LENGTH_SHORT).show()
                    println("DEBUG: Error checking Wishlist: ${e.message}")
                }
        }
    }

    private fun getCurrentUserId(): String? {
        val sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("USER_ID", null)
    }
}
