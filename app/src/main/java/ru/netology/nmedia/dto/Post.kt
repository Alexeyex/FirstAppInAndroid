package ru.netology.nmedia.dto

data class Post(
        val id: Long,
        val author: String,
        val content: String,
        val published: String,
        val likedByMe: Boolean,
        val numberOfLikesToInt: Long,
        val numberOfSharedToInt: Long,
        val numberOfOverlookedToInt: Long
)