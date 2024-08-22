package id.io.practice.splitbill.ui.result

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import id.io.practice.splitbill.adapter.RincianPatunganAdapter
import id.io.practice.splitbill.databinding.ActivityResultBinding
import id.io.practice.splitbill.response.OrderItem
import id.io.practice.splitbill.response.PatunganItemResponse
import id.io.practice.splitbill.ui.patungan.PatunganActivity
import kotlinx.serialization.json.Json

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var rincianAdapter: RincianPatunganAdapter
    private val listRincian = mutableListOf<OrderItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvSetup()
        retrieveData()
        btnListener()

    }

    private fun btnListener() {
        binding.apply {
            btnBack.setOnClickListener {
                onBackPressed()
            }
            btnPreviewLanjutkan.setOnClickListener {
                Intent(this@ResultActivity, PatunganActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }

    private fun retrieveData() {
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
//        val imageUri = intent.getStringExtra(EXTRA_IMAGE_URI)?.let { Uri.parse(it) }
        }
    }

    private fun rvSetup() {
        rincianAdapter = RincianPatunganAdapter(listRincian)
        binding.rvRincianPatungan.apply {
            layoutManager = LinearLayoutManager(this@ResultActivity)
            adapter = rincianAdapter
        }

    }


    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_RESULT = "extra_result"
    }
}