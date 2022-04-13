package ru.netology.nmedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import ru.netology.nmedia.databinding.ActivityNewPostBinding
import ru.netology.nmedia.dto.Post

class NewPostActivity : AppCompatActivity() {

    companion object {
        const val POST_KEY_CREATE = "postCreate"
        const val POST_KEY_EDIT = "postEdit"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.savePost.setOnClickListener {
            val text = binding.editPost.text?.toString()
            if (text.isNullOrBlank()) {
                setResult(RESULT_CANCELED)
            } else {
                val intent = Intent()
                        .putExtra(
                                POST_KEY_CREATE, Post(
                                id = 0L,
                                author = "",
                                content = text,
                                published = "",
                                likedByMe = false,
                                numberOfOverlookedToInt = 1L,
                                numberOfSharedToInt = 0L,
                                numberOfLikesToInt = 0L
                        )
                        )
                setResult(RESULT_OK, intent)
            }
            finish()
        }

        binding.savePost.setOnClickListener {
            val intent = getIntent()
            var txt = binding.editPost.text?.toString()
            var text = intent.getStringExtra(Intent.EXTRA_TEXT)
            txt = text.toString()
            

            /*if (intent.hasExtra(Intent.EXTRA_TEXT)) {
                            val txt = intent.getStringExtra(Intent.EXTRA_TEXT)
                            var text = binding.editPost.text.toString()
                            text = txt.toString()

                        }*/
            /*.putExtra(POST_KEY_EDIT, text)
                setResult(RESULT_OK, intent)
            finish()*/
        }
    }
}
