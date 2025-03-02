import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mangatronmobile.R


class BestSellersAdapter(
    val context: Context,
    val bestImageList: ArrayList<Int>,
    val bestNameList: ArrayList<String>,
    val bestPriceList: ArrayList<String>,
) : RecyclerView.Adapter<BestSellersAdapter.BestViewHolder>() {
    class BestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var bestImage: ImageView = itemView.findViewById(R.id.bestProductImage)
        var bestName: TextView = itemView.findViewById(R.id.bestProductName)
        var bestPrice: TextView = itemView.findViewById(R.id.bestProductPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestViewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.sample_best, parent, false)
        return BestViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return bestImageList.size
    }

    override fun onBindViewHolder(holder: BestViewHolder, position: Int) {
        holder.bestImage.setImageResource(bestImageList[position])
        holder.bestName.text = bestNameList[position]
        holder.bestPrice.text = bestPriceList[position]
    }

}