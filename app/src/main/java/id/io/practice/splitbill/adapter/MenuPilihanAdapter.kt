package id.io.practice.splitbill.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import id.io.practice.splitbill.R

class MenuPilihanAdapter(private val pilihanMenuList: List<Pair<String, Int>>) :
    RecyclerView.Adapter<MenuPilihanAdapter.MenuPilihanViewHolder>() {

    inner class MenuPilihanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val profileImage: ImageView = itemView.findViewById(R.id.profileImage)

        fun bind(item: Pair<String, Int>) {
            profileImage.setImageResource(item.second)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuPilihanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_teman_patungan, parent, false)
        return MenuPilihanViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuPilihanViewHolder, position: Int) {
        val item = pilihanMenuList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = pilihanMenuList.size
}