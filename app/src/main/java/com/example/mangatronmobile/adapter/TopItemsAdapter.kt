package com.example.mangatronmobile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mangatronmobile.R


class TopItemsAdapter(
    val context: Context,
    val imageList: ArrayList<Int>,
    val nameList: ArrayList<String>,
    val priceList: ArrayList<String>,
) : RecyclerView.Adapter<TopItemsAdapter.TopItemsViewHolder>() {
    class TopItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.topProductImage)
        var name: TextView = itemView.findViewById(R.id.topProductName)
        var price: TextView = itemView.findViewById(R.id.topProductPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopItemsViewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.sample_top, parent, false)
        return TopItemsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: TopItemsViewHolder, position: Int) {
        holder.image.setImageResource(imageList[position])
        holder.name.text = nameList[position]
        holder.price.text = priceList[position]
    }

}