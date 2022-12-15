package devcode.android.starter.modules.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import devcode.android.starter.R
import devcode.android.starter.databinding.ActivityHomeBinding
import devcode.android.starter.modules.retrieve_data.RetrieveDataActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        initView()
    }

    private fun initView() {
        binding.routerButton.setOnClickListener {
            val intent = Intent(this, RetrieveDataActivity::class.java)
            startActivity(intent)
        }
    }
}