package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
                //viewModel.changeContent(post.content)
                val intent = Intent(this@MainActivity, NewPostActivity::class.java)
                intent.putExtra(Intent.EXTRA_TEXT, post)
                startActivityForResult(intent, newPostRequestCode2)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newPostRequestCode1 && resultCode == RESULT_OK && data != null) {
            val post = data.getParcelableExtra<Post>(NewPostActivity.POST_KEY_CREATE) ?: return

            viewModel.edit(post)
            viewModel.save()
        } else if (requestCode == newPostRequestCode2 && resultCode == RESULT_OK && data != null) {
            val post = data.getParcelableExtra<Post>(NewPostActivity.POST_KEY_EDIT) ?: return

            viewModel.changeContent(post.content)
            viewModel.save()
        }
    }
}
