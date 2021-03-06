package ru.netology.nmedia.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean,
    val video: String?,
    val numberOfLikesToInt: Long,
    val numberOfSharedToInt: Long,
    val numberOfOverlookedToInt: Long
) : Parcelable