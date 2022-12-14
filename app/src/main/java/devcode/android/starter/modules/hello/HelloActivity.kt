package devcode.android.starter.modules.hello

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import devcode.android.starter.R
import devcode.android.starter.databinding.ActivityHelloBinding

class HelloActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHelloBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHelloBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}