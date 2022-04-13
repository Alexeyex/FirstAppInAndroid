package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.AndroidUtils
import ru.netology.nmedia.utils.AndroidUtils.showToast
import ru.netology.nmedia.viewmodel.PostViewModel

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private val newPostRequestCode1 = 1
    private val newPostRequestCode2 = 2
    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val editPostLauncher = registerForActivityResult(NewPostResultContract()) { result ->
            result ?: return@registerForActivityResult
            viewModel.changeContent(result)
            viewModel.save()
        }

        /*binding.addPost.setOnClickListener {
            editPostLauncher.launch()
        }*/

        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent(Intent.ACTION_SEND)
                    .setType("text/plain")
                    .putExtra(Intent.EXTRA_TEXT, post.content)
                    .let {
                        Intent.createChooser(it, null)
                    }
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                } else {
                    showToast(R.string.app_not_found_error)
                }
            }

            override fun onOverlook(post: Post) {
                viewModel.overlookById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
                editPostLauncher.launch()
            }


            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

        })

        binding.listPostFeed.adapter = adapter

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }


        binding.addPost.setOnClickListener {
            val intent = Intent(this, NewPostActivity::class.java)
            startActivityForResult(intent, newPostRequestCode1)
        }
    }

}
