package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityVideoBinding

class VideoFragment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.playVideoBtn.setOnClickListener {
            startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse(intent.getStringExtra(Intent.EXTRA_TEXT)))
            )
        }
    }
}