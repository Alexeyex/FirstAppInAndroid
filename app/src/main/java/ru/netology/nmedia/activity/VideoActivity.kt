package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityVideoBinding

class VideoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playVideoBtn.setOnClickListener {
            var intent = Intent()
            if (binding.url.text.isNullOrBlank()) {
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                val urlText = binding.url.text.toString()
                intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlText))
                startActivity(intent)
            }
        }
    }
}