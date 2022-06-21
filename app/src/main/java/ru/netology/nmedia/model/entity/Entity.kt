package ru.netology.nmedia.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.model.dto.Post

@Entity
data class PostEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean,
    val video: String?,
    val numberOfLikesToInt: Long = 0,
    val numberOfSharedToInt: Long = 0,
    val numberOfOverlookedToInt: Long = 0
) {
    fun toPost(): Post = Post(id, author, content, published, likedByMe, video, numberOfLikesToInt, numberOfSharedToInt, numberOfOverlookedToInt)

    companion object {
        fun fromPost(post: Post): PostEntity =
            PostEntity(post.id, post.author, post.content, post.published, post.likedByMe,
            post.video, post.numberOfLikesToInt, post.numberOfSharedToInt, post.numberOfOverlookedToInt)
    }
}
