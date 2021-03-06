package ru.netology.nmedia.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.view.activity.DetailsFragment.Companion.idArgument
import ru.netology.nmedia.view.activity.NewPostFragment.Companion.postArgument
import ru.netology.nmedia.view.adapter.OnInteractionListener
import ru.netology.nmedia.view.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.model.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class FeedFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }

                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
            }

            override fun onOverlook(post: Post) {
                viewModel.overlookById(post.id)
            }

            override fun onAddVideo(post: Post) {
                viewModel.addVideoById(post.id)
                val intent = Intent(requireContext(), VideoFragment::class.java)
                    .putExtra(Intent.EXTRA_TEXT, post.video)
                startActivity(intent)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
                findNavController().navigate(
                    R.id.action_feedFragment_to_newPostFragment,
                    Bundle().apply { postArgument = post }
                )
            }

            override fun onClickPost(post: Post) {
                findNavController().navigate(R.id.action_feedFragment_to_detailsFragment,
                    Bundle().apply { idArgument = post.id })
            }


            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }
        })

        binding.listPostFeed.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        binding.addPost.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }
        return binding.root
    }
}



