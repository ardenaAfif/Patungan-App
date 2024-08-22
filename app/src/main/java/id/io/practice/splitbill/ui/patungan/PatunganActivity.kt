package id.io.practice.splitbill.ui.patungan

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import id.io.practice.splitbill.R
import id.io.practice.splitbill.databinding.ActivityPatunganBinding

class PatunganActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPatunganBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatunganBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}