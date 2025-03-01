package com.example.mangatronmobile.ui.activity.admin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mangatronmobile.R
import com.example.mangatronmobile.databinding.ActivityUpdateProductBinding
import com.example.mangatronmobile.repository.ProductRepositoryImpl
import com.example.mangatronmobile.viewmodel.ProductViewModel


class UpdateProductActivity : AppCompatActivity() {

    lateinit var  binding: ActivityUpdateProductBinding
    lateinit var productViewModel: ProductViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding=ActivityUpdateProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var repo = ProductRepositoryImpl()
        productViewModel= ProductViewModel(repo)

        var productId: String = intent.getStringExtra("productId").toString()

        productViewModel.getProductById(productId)


        productViewModel.products.observe(this){
            binding.updateProductName.setText(it?.productName.toString())

            binding.updateProductPrice.setText(it?.productPrice.toString())

            binding.updateProductDesc.setText(it?.productDesc.toString())
        }
        binding.btnUpdateProduct.setOnClickListener{
            var name = binding.updateProductName.text.toString()
            var price = binding.updateProductPrice.text.toString().toInt()
            var desc = binding.updateProductDesc.text.toString()

            var updatedMap= mutableMapOf<String,Any>()

            updatedMap["productName"]=name
            updatedMap["productDesc"]=desc
            updatedMap["price"]=price

            productViewModel.updateProduct(productId,updatedMap){
                    success,message->
                if(success){
                    Toast.makeText(this@UpdateProductActivity,message,Toast.LENGTH_LONG).show()
                    finish()
                }else{
                    Toast.makeText(this@UpdateProductActivity,message,Toast.LENGTH_LONG).show()
                }
            }

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}