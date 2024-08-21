package id.io.practice.splitbill.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import id.io.practice.splitbill.R

class PartnerAdapter(private val partnerList: List<Pair<String, Int>>) : RecyclerView.Adapter<PartnerAdapter.PartnerViewHolder>() {


    inner class PartnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage: ImageView = itemView.findViewById(R.id.profileImage)

        fun bind(imageRes: Int) {
            profileImage.setImageResource(imageRes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartnerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_teman_patungan, parent, false)
        return PartnerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PartnerViewHolder, position: Int) {
        val (_, imageResource) = partnerList[position]
        holder.bind(imageResource)
    }

    override fun getItemCount(): Int = partnerList.size
}
