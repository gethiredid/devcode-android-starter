package devcode.android.starter.modules.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import devcode.android.starter.R
import devcode.android.starter.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
    }
}