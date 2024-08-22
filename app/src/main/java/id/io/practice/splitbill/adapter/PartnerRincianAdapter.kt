package id.io.practice.splitbill.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.io.practice.splitbill.R

class PartnerRincianAdapter(private val partnerList: List<Pair<String, Int>>) :
    RecyclerView.Adapter<PartnerRincianAdapter.PartnerViewHolder>() {

    inner class PartnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage: ImageView = itemView.findViewById(R.id.profilePatungan)
        val namePartner: TextView = itemView.findViewById(R.id.namePartner)

        fun bind(partner: Pair<String, Int>) {
            namePartner.text = partner.first
            profileImage.setImageResource(partner.second)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartnerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_partner_bill_rincian, parent, false)
        return PartnerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PartnerViewHolder, position: Int) {
        val partner = partnerList[position]
        holder.bind(partner)
    }

    override fun getItemCount(): Int = partnerList.size
}
