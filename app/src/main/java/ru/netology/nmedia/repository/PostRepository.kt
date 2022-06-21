package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.model.dto.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun shareById(id: Long)
    fun overlookById(id: Long)
    fun removeById(id: Long)
    fun save(post: Post)
    fun addVideoById(id: Long)
    fun remove(post: Post)
}