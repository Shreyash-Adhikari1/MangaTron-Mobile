package com.example.mangatronmobile.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mangatronmobile.R
import com.example.mangatronmobile.model.ProductModel
import com.example.mangatronmobile.ui.fragment.UpdateProductFragment

class ProductAdapter(
    private val context: Context,
    private var data: ArrayList<ProductModel>
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pName: TextView = itemView.findViewById(R.id.displayProductName)
        val pPrice: TextView = itemView.findViewById(R.id.displayProductPrice)
        val pDesc: TextView = itemView.findViewById(R.id.displayProductDesc)
        val pImage: ImageView = itemView.findViewById(R.id.displayProductImage)
        val edit: TextView = itemView.findViewById(R.id.lblEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.sample_products, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = data[position] // Get the current product

        holder.pName.text = product.productName
        holder.pPrice.text = product.productPrice.toString()
        holder.pDesc.text = product.productDesc

        // Load the image using Glide
        Glide.with(context)
            .load(product.productImage)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error) // Image to show if loading fails
            .into(holder.pImage)

        holder.edit.setOnClickListener {
            val intent = Intent(context, UpdateProductFragment::class.java)
            intent.putExtra("productId", product.productId)
            context.startActivity(intent)
        }
    }


    fun updateData(products: List<ProductModel>) {
        data.clear()
        data.addAll(products)
        notifyDataSetChanged()
    }

    fun getProductId(position: Int): String {
        return data[position].productId
    }
}
