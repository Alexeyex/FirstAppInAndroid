package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import ru.netology.nmedia.model.dao.PostDao
import ru.netology.nmedia.model.dto.Post
import ru.netology.nmedia.model.entity.PostEntity

class PostRepositorySQLiteImpl(private val dao: PostDao) : PostRepository {

    override fun getAll(): LiveData<List<Post>> = Transformations.map(dao.getAll()) { list->
        list.map { entity ->
            entity.toPost()
        }

    }

    override fun save(post: Post) {
        dao.save(PostEntity.fromPost(post))
    }

    override fun likeById(id: Long) {
        dao.likeById(id)
    }

    override fun shareById(id: Long) {
        dao.shareById(id)
    }

    override fun overlookById(id: Long) {
        dao.overlookById(id)
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
    }

    override fun addVideoById(id: Long) {
    }

    override fun remove(post: Post) {
        dao.remove(PostEntity.fromPost(post))
    }
}