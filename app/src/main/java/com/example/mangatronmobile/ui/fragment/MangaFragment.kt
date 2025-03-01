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
import com.example.mangatronmobile.databinding.FragmentMangaBinding
import com.example.mangatronmobile.model.CartModel
import com.example.mangatronmobile.model.ProductModel
import com.example.mangatronmobile.repository.ProductRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class MangaFragment : Fragment() {

    private lateinit var binding: FragmentMangaBinding
    private lateinit var productAdapter: ProductAdapter
    private lateinit var productRepository: ProductRepository
    private var productList = ArrayList<ProductModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMangaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productRepository = ProductRepositoryImpl()

        productAdapter = ProductAdapter(requireContext(), productList) { product -> addToCart(product) }

        binding.mangaRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
        }

        fetchManga()
    }

    private fun fetchManga() {
        productRepository.getProductByCategory("Manga") { products, success, message ->
            if (success && products != null) {
                productList.clear()
                productList.addAll(products)
                productAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), "Failed to load manga", Toast.LENGTH_SHORT).show()
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
    }
