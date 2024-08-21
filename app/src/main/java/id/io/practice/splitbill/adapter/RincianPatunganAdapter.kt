package id.io.practice.splitbill.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.io.practice.splitbill.R
import id.io.practice.splitbill.response.OrderItem

class RincianPatunganAdapter(private val listRincian: List<OrderItem>) :
    RecyclerView.Adapter<RincianPatunganAdapter.RincianViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RincianViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rincian_patungan, parent, false)
        return RincianViewHolder(view)
    }

    override fun onBindViewHolder(holder: RincianViewHolder, position: Int) {
        val rincian = listRincian[position]
        holder.bind(rincian)
    }

    override fun getItemCount(): Int = listRincian.size

    class RincianViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val namaPesanan: TextView = itemView.findViewById(R.id.namaPesanan)
        private val jumlahPesanan: TextView = itemView.findViewById(R.id.jumlahPesanan)
        private val hargaPesanan: TextView = itemView.findViewById(R.id.hargaPesanan)

        fun bind(rincian: OrderItem) {
            namaPesanan.text = rincian.name
            jumlahPesanan.text = "x${rincian.quantity}"
            hargaPesanan.text = rincian.price
        }
    }
}