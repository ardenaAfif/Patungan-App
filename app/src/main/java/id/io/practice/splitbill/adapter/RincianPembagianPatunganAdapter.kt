package id.io.practice.splitbill.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.io.practice.splitbill.R
import id.io.practice.splitbill.response.OrderItem

class RincianPembagianPatunganAdapter(
    private val listRincian: List<OrderItem>,
    private val selectedItems: MutableList<OrderItem>
) : RecyclerView.Adapter<RincianPembagianPatunganAdapter.RincianViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RincianViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rincian_pembagian_patungan, parent, false)
        return RincianViewHolder(view)
    }

    override fun onBindViewHolder(holder: RincianViewHolder, position: Int) {
        val rincian = listRincian[position]
        holder.bind(rincian, selectedItems.contains(rincian))

        holder.itemView.findViewById<CheckBox>(R.id.checkBoxSplitBill)
            .setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedItems.add(rincian)
                } else {
                    selectedItems.remove(rincian)
                }
            }
    }

    override fun getItemCount(): Int = listRincian.size

    fun getSelectedItems(): List<OrderItem> {
        return selectedItems
    }

    class RincianViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val namaPesanan: TextView = itemView.findViewById(R.id.namaPesananSplitBill)
        private val jumlahPesanan: TextView = itemView.findViewById(R.id.jumlahPesananSplitBill)
        private val hargaPesanan: TextView = itemView.findViewById(R.id.hargaPesananSplitBill)
        private val cbSplitBill: CheckBox = itemView.findViewById(R.id.checkBoxSplitBill)
        private val rvPilihanMenuPatungan: RecyclerView = itemView.findViewById(R.id.rvPilihanMenuPatungan)

        fun bind(rincian: OrderItem, isSelected: Boolean) {
            namaPesanan.text = rincian.name
            jumlahPesanan.text = "x${rincian.quantity}"
            hargaPesanan.text = rincian.price
            cbSplitBill.isChecked = isSelected // Set status checklist
        }
    }
}