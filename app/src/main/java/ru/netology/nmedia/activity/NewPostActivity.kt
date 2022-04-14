package ru.netology.nmedia.activity

import android.app.Activity
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
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editPost.requestFocus()



        binding.savePost.setOnClickListener {
            //if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            val intent = Intent()
            if (binding.editPost.text.isNullOrBlank()) {
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                val content = binding.editPost.text.toString()
                intent.putExtra(Intent.EXTRA_TEXT, content)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }  /*else {
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
        }*/}


    }

