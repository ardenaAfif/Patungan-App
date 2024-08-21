package id.io.practice.splitbill.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.io.practice.splitbill.R

class PartnerListDialogAdapter(
    private val partnerList: MutableList<Pair<String, Int>>,
    private val onPartnerEdited: (Int, String) -> Unit,
    private val onPartnerDeleted: (Int) -> Unit
) : RecyclerView.Adapter<PartnerListDialogAdapter.PartnerDialogViewHolder>() {

    inner class PartnerDialogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPartnerName: TextView = itemView.findViewById(R.id.tvPartnerName)
        val btnEditPartner: ImageView = itemView.findViewById(R.id.btnEditPartner)
        val btnDeletePartner: ImageView = itemView.findViewById(R.id.btnDeletePartner)

        fun bind(position: Int) {
            val (name, _) = partnerList[position]
            tvPartnerName.text = name

            btnEditPartner.setOnClickListener {
                val editText = EditText(itemView.context)
                editText.setText(name)
                val dialog = AlertDialog.Builder(itemView.context)
                    .setTitle("Edit Nama")
                    .setView(editText)
                    .setPositiveButton("Simpan") { _, _ ->
                        val newName = editText.text.toString().trim()
                        if (newName.isNotEmpty()) {
                            onPartnerEdited(adapterPosition, newName)
                        }
                    }
                    .setNegativeButton("Batal", null)
                    .create()
                dialog.show()
            }

            btnDeletePartner.setOnClickListener {
                onPartnerDeleted(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartnerDialogViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_partner_dialog, parent, false)
        return PartnerDialogViewHolder(view)
    }

    override fun onBindViewHolder(holder: PartnerDialogViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = partnerList.size
}