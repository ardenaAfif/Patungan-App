package id.io.practice.splitbill.ui.home

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.io.practice.splitbill.R
import id.io.practice.splitbill.adapter.PartnerAdapter
import id.io.practice.splitbill.adapter.PartnerListDialogAdapter
import id.io.practice.splitbill.databinding.ActivityMainBinding
import id.io.practice.splitbill.ui.camera.CameraActivity
import java.util.Calendar
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var partnerAdapter: PartnerAdapter

    private val partnerList = mutableListOf<Pair<String, Int>>() // List to store partner names

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRv()
        setGreetingTime()
        addSplitBill()
        addPartnerBill()
        listPartnerListener()
    }

    private fun listPartnerListener() {
        // Menampilkan dialog saat tv_total_partner diklik
        binding.tvTotalPartner.setOnClickListener {
            showPartnerListDialog()
        }
    }

    private fun showPartnerListDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_partner_list, null)
        val rvPartnerDialogList = dialogView.findViewById<RecyclerView>(R.id.rvPartnerDialogList)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setNegativeButton("Tutup", null)
            .create()

        val partnerDialogAdapter = PartnerListDialogAdapter(
            partnerList,
            onPartnerEdited = { position, newName ->
                partnerList[position] = partnerList[position].copy(first = newName)
                partnerAdapter.notifyDataSetChanged()
                dialog.dismiss()
            },
            onPartnerDeleted = { position ->
                partnerList.removeAt(position)
                partnerAdapter.notifyDataSetChanged()
                binding.tvTotalPartner.text = partnerList.size.toString()
                dialog.dismiss()
            }
        )

        rvPartnerDialogList.layoutManager = LinearLayoutManager(this)
        rvPartnerDialogList.adapter = partnerDialogAdapter

        dialog.show()
    }

    private fun initRv() {
        partnerAdapter = PartnerAdapter(partnerList)
        binding.rvPartnerList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPartnerList.adapter = partnerAdapter
    }

    private fun addPartnerBill() {
        binding.addPartner.setOnClickListener {
            if (partnerList.size < 10) {
                showAddPartnerDialog()
            } else{
                Toast.makeText(this, "Teman patungan maksimal sudah tercapai", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showAddPartnerDialog() {
        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet_add_partner, null)
        val etPartnerName = dialogView.findViewById<EditText>(R.id.et_partner_name)

        val btnAddPartner = dialogView.findViewById<Button>(R.id.btn_add_partner)

        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogView)

        btnAddPartner.setOnClickListener {
            val name = etPartnerName.text.toString().trim()
            if (name.isEmpty()) {
                Toast.makeText(this, "Nama teman patungan tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                addPartner(name)
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun getRandomProfileImage(): Int {
        // Mengambil 10 gambar profile random dari drawable
        val images = listOf(
            R.drawable.profile_1, R.drawable.profile_2, R.drawable.profile_3,
            R.drawable.profile_4, R.drawable.profile_5, R.drawable.profile_6,
            R.drawable.profile_7, R.drawable.profile_8, R.drawable.profile_9,
            R.drawable.profile_10
        )
        return images.random()
    }

    private fun addPartner(partnerName: String) {
        val profileImage = getRandomProfileImage()
        partnerList.add(Pair(partnerName, profileImage)) // Menyimpan nama dan gambar profil

        partnerAdapter.notifyDataSetChanged()
        binding.tvTotalPartner.text = partnerList.size.toString()
    }

    private fun addSplitBill() {
        binding.cardBuatPatunganBaru.setOnClickListener {
            if (partnerList.isEmpty()) {
                Toast.makeText(this, "Silakan tambahkan teman patungan terlebih dahulu", Toast.LENGTH_SHORT).show()
            } else if (partnerList.size < 2) {
                Toast.makeText(this, "Teman patungan minimal 2 orang", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, CameraActivity::class.java)
                intent.putExtra("EXTRA_PARTNER_LIST", partnerList as ArrayList<Pair<String, Int>>)
                startActivity(intent)
            }
        }
    }

    private fun setGreetingTime() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)

        val greeting = when (hour) {
            in 0..11 -> "Selamat Pagi"
            in 12..14 -> "Selamat Siang"
            in 15..20 -> "Selamat Sore"
            else -> "Selamat Malam"
        }

        binding.tvGreeting.text = greeting
    }
}