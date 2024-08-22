package id.io.practice.splitbill.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.io.practice.splitbill.R

class PartnerRincianAdapter(private val partnerList: List<Pair<String, Int>>) :
    RecyclerView.Adapter<PartnerRincianAdapter.PartnerViewHolder>() {

    private var selectedPosition: Int = -1 // Track selected position

    inner class PartnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage: ImageView = itemView.findViewById(R.id.profilePatungan)
        val namePartner: TextView = itemView.findViewById(R.id.namePartner)

        fun bind(partner: Pair<String, Int>, position: Int) {
            namePartner.text = partner.first
            profileImage.setImageResource(partner.second)

            namePartner.setTypeface(null, Typeface.NORMAL)
            profileImage.setBackgroundColor(Color.TRANSPARENT)

            // Highlight selected item
            if (position == selectedPosition) {
                namePartner.setTypeface(null, Typeface.BOLD)
                profileImage.setBackgroundResource(R.drawable.profile_image_stroke)
            }

            itemView.setOnClickListener {
                selectedPosition = position
                notifyDataSetChanged() // Refresh RecyclerView to update UI
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartnerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_partner_bill_rincian, parent, false)
        return PartnerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PartnerViewHolder, position: Int) {
        val partner = partnerList[position]
        holder.bind(partner, position)
    }

    override fun getItemCount(): Int = partnerList.size
}
