package id.io.practice.splitbill.ui.result

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import id.io.practice.splitbill.adapter.PartnerRincianAdapter
import id.io.practice.splitbill.adapter.RincianPatunganAdapter
import id.io.practice.splitbill.databinding.ActivityResultBinding
import id.io.practice.splitbill.response.OrderItem
import id.io.practice.splitbill.response.PatunganItemResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    private lateinit var partnerAdapter: PartnerRincianAdapter
    private lateinit var rincianAdapter: RincianPatunganAdapter

    private val listRincian = ArrayList<OrderItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvRincianSetup()
        retrievePartnerData()
        retrieveMenuData()
        btnListener()
    }

    private fun btnListener() {
        binding.apply {
            toolbar.navBack.setOnClickListener {
                onBackPressed()
            }
            btnHitung.setOnClickListener {
//                Intent(this@ResultActivity, PatunganActivity::class.java).also {
//
//                    startActivity(it)
//                }
            }
        }
    }

    private fun retrievePartnerData() {
        // Retrive partner data
        val partnerList =
            intent.getSerializableExtra("EXTRA_PARTNER_LIST") as? ArrayList<Pair<String, Int>>
        if (partnerList != null) {
            setupPartnerBillRv(partnerList)
        } else {
            // Tangani jika data null
            Log.e("PatunganActivity", "Partner list tidak ditemukan")
        }
    }

    private fun retrieveMenuData() {
        val jsonResponse = intent.getStringExtra(EXTRA_RESULT)

        // Parsing JSON dan update RecyclerView
        jsonResponse?.let {
            val response = Json.decodeFromString<PatunganItemResponse>(it)
            listRincian.addAll(response.listOrder)
            rincianAdapter.notifyDataSetChanged()

            binding.apply {
                pajakPesanan.text = response.taxFee ?: "-"
                servicePesanan.text = response.serviceCharge ?: "-"
                diskonPesanan.text = if (response.discount != null) "- ${response.discount}" else "-"
                totalBayarPesanan.text = "Rp ${response.totalPrice}"
            }
        }
    }

    private fun rvRincianSetup() {
        rincianAdapter = RincianPatunganAdapter(listRincian)
        binding.rvRincianPatungan.apply {
            layoutManager = LinearLayoutManager(this@ResultActivity)
            adapter = rincianAdapter
        }
    }

    private fun setupPartnerBillRv(partnerList: ArrayList<Pair<String, Int>>) {
        partnerAdapter = PartnerRincianAdapter(partnerList)
        binding.rvPartnersBill.apply {
            layoutManager =
                LinearLayoutManager(this@ResultActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = partnerAdapter
        }
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_RESULT = "extra_result"
    }
}