package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.numberEditing
import ru.netology.nmedia.viewmodel.PostViewModel
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        viewModel.data.observe(this) { post ->

            with(binding) {
                authorView.text = post.author
                contentView.text = post.content
                publishedView.text = post.published
                //numberOfSharedView.text = numberEditing(post.numberOfSharedToInt)
                numberOfLikesView.text = numberEditing(post.numberOfLikesToInt)
                //numberOfOverlookedView.text = numberEditing(post.numberOfOverlookedToInt)




                likes.setImageResource(
                        if (post.likedByMe) {
                            R.drawable.image_like
                        } else {
                            R.drawable.image_dislike
                        }
                )
            }
        }

                binding.likes.setOnClickListener {
                    viewModel.like()

                }

                /*shared.setOnClickListener {
                    post.numberOfSharedToInt++
                    numberOfSharedView.text = numberEditing(post.numberOfSharedToInt)
                }

                overlooked.setOnClickListener {
                    post.numberOfOverlookedToInt++
                    numberOfOverlookedView.text = numberEditing(post.numberOfOverlookedToInt)
                }*/

    }
}
