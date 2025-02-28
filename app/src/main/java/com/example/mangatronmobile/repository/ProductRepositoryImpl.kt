package com.example.mangatronmobile.repository

import android.content.Context
import android.net.Uri
import com.example.mangatronmobile.model.ProductModel
import com.example.mangatronmobile.repository.ProductRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProductRepositoryImpl: ProductRepository {

    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val ref : DatabaseReference = database.reference.child("products")
    override fun addProduct(productModel: ProductModel, callback: (Boolean, String) -> Unit) {

        var id = ref.push().key.toString()
        productModel.productId = id

        ref.child(id).setValue(productModel).addOnCompleteListener{
            if(it.isSuccessful){
                callback(true,"Product Added Successfully")
            } else{
                callback(false,"${it.exception?.message.toString()}")
            }
        }
    }

    override fun updateProduct(
        productId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(productId).updateChildren(data).addOnCompleteListener{
            if(it.isSuccessful){
                callback(true,"Product Added Successfully")
            } else{
                callback(false,"${it.exception?.message}")
            }
        }
    }

    override fun deleteProduct(productId: String, callback: (Boolean, String) -> Unit) {
        ref.child(productId).removeValue().addOnCompleteListener{
            if(it.isSuccessful){
                callback(true,"Product Deleted Successfully")
            } else{
                callback(false,"${it.exception?.message}")
            }
        }
    }

    override fun getProductById(
        productId: String,
        callback: (ProductModel?, Boolean, String) -> Unit
    ) {
        ref.child(productId).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var model = snapshot.getValue(ProductModel::class.java)
                    callback(model,true,"Product Fetched Successfully")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.toString())
            }
        })

    }

    override fun getAllProduct(callback: (List<ProductModel>?, Boolean, String) -> Unit) {
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var products = mutableListOf<ProductModel>()
                if(snapshot.exists()){
                    for(eachProduct in snapshot.children){
                        var data = eachProduct.getValue(ProductModel::class.java)
                        if(data != null){
                            products.add(data)
                        }
                    }
                    callback(products,true,"Product Added Successfully")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null,false,error.toString())
            }
        })
    }

    override fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getFileNameFromUri(context: Context, uri: Uri): String? {
        TODO("Not yet implemented")
    }
}