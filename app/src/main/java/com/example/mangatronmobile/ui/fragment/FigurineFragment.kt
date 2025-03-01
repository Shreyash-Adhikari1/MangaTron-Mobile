import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mangatron.repository.ProductRepository
import com.example.mangatronmobile.databinding.FragmentFigurineBinding
import com.example.mangatronmobile.model.CartModel
import com.example.mangatronmobile.model.ProductModel
import com.example.mangatronmobile.repository.CartRepository
import com.example.mangatronmobile.repository.CartRepositoryImpl
import com.example.mangatronmobile.repository.ProductRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FigurineFragment : Fragment() {

    lateinit var binding: FragmentFigurineBinding
    lateinit var productAdapter: ProductAdapter
    lateinit var productRepository: ProductRepository
    lateinit var cartRepository: CartRepository
    var productList = ArrayList<ProductModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFigurineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productRepository = ProductRepositoryImpl()
        cartRepository = CartRepositoryImpl()

        productAdapter = ProductAdapter(requireContext(), productList) { product -> addToCart(product) }

        binding.figurineRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
        }

        // Fetch Figurine products
        fetchFigurines()
    }

    private fun fetchFigurines() {
        productRepository.getProductByCategory("Figurine") { products, success, message ->
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

    private fun getCurrentUserId(): String? {
        val sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("USER_ID", null)
    }
}
