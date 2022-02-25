package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post
import java.math.RoundingMode
import java.text.DecimalFormat

class PostRepositoryInMemoryImpl: PostRepository {

    private var post = Post(
            id = 1,
            author = "Нетология. Университет интернет - профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов.Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен - http://netolo.gy/fyb",
            published = "30 мая в 21:05",
            likedByMe = false,
            numberOfSharedToInt = 1999999,
            numberOfLikesToInt = 1999999,
            numberOfOverlookedToInt = 1999999
    )

    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data

    override fun like() {
        post = post.copy(likedByMe = !post.likedByMe, numberOfLikesToInt = if (post.likedByMe) {
            post.numberOfLikesToInt++
        } else {
           post.numberOfLikesToInt--

        })
        numberEditing(post.numberOfLikesToInt)
        data.value = post
    }


}

private val formatThousands = DecimalFormat("#.#").apply {
    roundingMode = RoundingMode.DOWN
}

fun numberEditing(number: Long): String =
        when (number) {
            in 1000..1099 -> "${number / 1000}K"
            in 1100..9999 -> "${formatThousands.format(number.toDouble() / 1000)}K"
            in 10_000..999_999 -> "${(number / 1000)}K"
            in 1_000_000..1_099_999 -> "${number / 1_000_000}M"
            in 1_100_000..999_999_999 -> "${formatThousands.format(number.toDouble() / 1_000_000)}M"
            else -> number.toString()
        }