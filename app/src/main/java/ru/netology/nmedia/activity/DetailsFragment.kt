package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.NumberEditor
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.LongDelegate
import ru.netology.nmedia.viewmodel.PostViewModel
import ru.netology.nmedia.activity.NewPostFragment.Companion.postArgument



class DetailsFragment : Fragment() {
    companion object {
        var Bundle.idArgument: Long? by LongDelegate
    }

    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = CardPostBinding.inflate(inflater, container, false)

        val id = requireNotNull(arguments?.idArgument)

        viewModel.data.map { posts ->
            posts.find { it.id == id }
        }.observe(viewLifecycleOwner) {
            val post = it ?: run {
                findNavController().navigateUp()
                return@observe
            }

            binding.apply {
                authorView.text = post.author
                contentView.text = post.content
                publishedView.text = post.published
                likes.text = NumberEditor.numberEditing(post.numberOfLikesToInt)
                shared.text = NumberEditor.numberEditing(post.numberOfSharedToInt)
                overlooked.text = NumberEditor.numberEditing(post.numberOfOverlookedToInt)

                likes.isChecked = post.likedByMe

                likes.setOnClickListener {
                    viewModel.likeById(post.id)
                }

                shared.setOnClickListener {
                    viewModel.shareById(post.id)

                    val intent = Intent(Intent.ACTION_SEND)
                        .setType("text/plain")
                        .putExtra(Intent.EXTRA_TEXT, post.content)
                        .let { intent ->
                            Intent.createChooser(intent, null)
                        }
                    if (intent.resolveActivity(requireContext().packageManager) != null) {
                        startActivity(intent)
                    }
                }

                menuPostView.setOnClickListener { view ->
                    PopupMenu(view.context, view).apply {
                        inflate(R.menu.options_post)
                        setOnMenuItemClickListener { item ->
                            when (item.itemId) {
                                R.id.removePost -> {
                                    viewModel.removeById(post.id)
                                    true
                                }
                                R.id.editPost -> {
                                    viewModel.edit(post)
                                    findNavController().navigate(R.id.action_detailsFragment_to_newPostFragment,
                                        Bundle().apply { postArgument = post })
                                    true
                                }
                                else -> false
                            }
                        }
                    }.show()
                }
            }
        }
        return binding.root
    }
}