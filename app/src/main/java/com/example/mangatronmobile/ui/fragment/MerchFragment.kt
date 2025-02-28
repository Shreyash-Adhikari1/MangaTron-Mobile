package com.example.mangatronmobile.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mangatron.repository.ProductRepository
import com.example.mangatronmobile.adapter.ProductAdapter
import com.example.mangatronmobile.databinding.FragmentMerchBinding
import com.example.mangatronmobile.model.ProductModel
import com.example.mangatronmobile.repository.ProductRepositoryImpl

class MerchFragment : Fragment() {
    lateinit var binding: FragmentMerchBinding
    lateinit var productAdapter: ProductAdapter
    lateinit var productRepository: ProductRepository
    var productList = ArrayList<ProductModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMerchBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        productRepository= ProductRepositoryImpl()

        productAdapter = ProductAdapter(requireContext(), productList)
        binding.merchRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
        }

        // Fetch products categorized as "Merch"
        fetchMerch()
    }

    private fun fetchMerch() {
        productRepository.getProductByCategory("Merch") { products, success, message ->
            if (success && products != null) {
                productList.clear()
                productList.addAll(products)
                productAdapter.notifyDataSetChanged()
            } else {

            }
        }
    }
}