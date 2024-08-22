package id.io.practice.splitbill.ui.camera

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import id.io.practice.splitbill.BuildConfig
import id.io.practice.splitbill.R
import id.io.practice.splitbill.databinding.ActivityPreviewImageBinding
import id.io.practice.splitbill.ui.result.ResultActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PreviewImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreviewImageBinding
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnListener()
        previewImage()
    }

    private fun previewImage() {
        val imageUri = intent.getStringExtra("imageUri")
        if (imageUri != null) {
            currentImageUri = Uri.parse(imageUri)
            binding.previewImage.setImageURI(currentImageUri)
        } else {
            Toast.makeText(this, "Gambar tidak ditemukan.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun btnListener() {
        binding.apply {
            btnBack.setOnClickListener {
                onBackPressed()
            }
            btnPreviewLanjutkan.setOnClickListener {
                if (currentImageUri != null) {
                    binding.progressIndicator.visibility = View.VISIBLE
                    analyzeImage(currentImageUri!!)
                } else {
                    Toast.makeText(this@PreviewImageActivity, "Gambar tidak ditemukan.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun analyzeImage(uri: Uri) {
        binding.progressIndicator.visibility = View.VISIBLE

        val textRecognition = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val inputImage = InputImage.fromFilePath(this, uri)

        textRecognition.process(inputImage)
            .addOnSuccessListener { visionText: Text ->
                binding.progressIndicator.visibility = View.GONE
                val detectedText: String = visionText.text

            /* ===== TEXT RECOGNITION =====
                val intent = Intent(this@PreviewImageActivity, ResultActivity::class.java)
                intent.putExtra(ResultActivity.EXTRA_IMAGE_URI, uri.toString())
                intent.putExtra(ResultActivity.EXTRA_RESULT, detectedText)
                startActivity(intent)
            */

                // Panggil fungsi untuk mengirim hasil ke Gemini API
                CoroutineScope(Dispatchers.IO).launch {
                    val jsonResponse = generateJson(detectedText)
                    val partnerList = intent.getSerializableExtra("EXTRA_PARTNER_LIST") as? ArrayList<Pair<String, Int>>
                    withContext(Dispatchers.Main) {
                        val intent = Intent(this@PreviewImageActivity, ResultActivity::class.java)
                        intent.putExtra(ResultActivity.EXTRA_IMAGE_URI, uri.toString())
                        intent.putExtra(ResultActivity.EXTRA_RESULT, jsonResponse)

                        Log.d("PreviewImageActivity", "Partner list: $partnerList")
                        intent.putExtra("EXTRA_PARTNER_LIST", partnerList)
                        startActivity(intent)
                    }
                }
            }
            .addOnFailureListener { e ->
                binding.progressIndicator.visibility = View.GONE
                Toast.makeText(this, "Gagal mendeteksi teks: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // Fungsi untuk mengirim data ke Gemini API dan mendapatkan response JSON
    private suspend fun generateJson(detectedText: String): String {
        val config = generationConfig {
            temperature = 0.9f
            topK = 16
            topP = 0.1f
            responseMimeType = "application/json"
        }
        val generativeModel = GenerativeModel(
            modelName = "gemini-1.5-pro",
            apiKey = BuildConfig.GEMINI_API_KEY,  // Ganti dengan API Key Anda
            generationConfig = config
        )
        val prompt =
            "Find the number of order from each item with price from this raw text: $detectedText using this JSON Schema: Response = {\"taxFee\": str,\"serviceCharge\":str, \"discount\":str, \"totalPrice\":str, \"listOrder\":List[{\"quantity\":str, \"name\":str, \"price\":str}]} Return a 'ResponseItem'"
        val response = generativeModel.generateContent(prompt)
        return response.text ?: "{}"  // Return JSON or empty JSON object
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


}