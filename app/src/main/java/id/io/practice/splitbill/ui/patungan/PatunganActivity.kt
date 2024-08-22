package id.io.practice.splitbill.ui.patungan

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import id.io.practice.splitbill.adapter.PartnerRincianAdapter
import id.io.practice.splitbill.databinding.ActivityPatunganBinding

class PatunganActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPatunganBinding
    private lateinit var partnerAdapter: PartnerRincianAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatunganBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrive partner data
        val partnerList = intent.getSerializableExtra("EXTRA_PARTNER_LIST") as? ArrayList<Pair<String, Int>>
        if (partnerList != null) {
            setupRv(partnerList)
        } else {
            // Tangani jika data null
            Log.e("PatunganActivity", "Partner list tidak ditemukan")
        }

    }

    private fun setupRv(partnerList: ArrayList<Pair<String, Int>>) {
        partnerAdapter = PartnerRincianAdapter(partnerList)
        binding.rvPartnersBill.apply {
            layoutManager = LinearLayoutManager(this@PatunganActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = partnerAdapter
        }
    }
}