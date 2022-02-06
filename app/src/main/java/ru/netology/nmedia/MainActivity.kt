package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
                id = 1,
                author = "Нетология. Университет интернет - профессий будущего",
                content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов.Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен - http://netolo.gy/fyb",
                published = "30 мая в 21:05",
                likedByMe = false,
                numberOfSharedToInt = 9999

        )

        with (binding) {
            authorView.text = post.author
            contentView.text = post.content
            publishedView.text = post.published
            numberOfSharedView.text = post.numberOfSharedToInt.toString()
            var numberOfLikesToInt = (numberOfLikesView.text as String).toInt()
            if (post.likedByMe) {
                likes.setImageResource(R.drawable.image_dislike)
            }



            likes.setOnClickListener {
                post.likedByMe = !post.likedByMe
                if (post.likedByMe) {
                    numberOfLikesToInt++
                    numberOfLikesView.text = numberOfLikesToInt.toString()
                } else {
                    numberOfLikesToInt--
                numberOfLikesView.text = numberOfLikesToInt.toString()
                }
                likes.setImageResource(
                        if(post.likedByMe) {
                            R.drawable.image_like
                        }
                        else {
                            R.drawable.image_dislike
                        }
                )
            }

            shared.setOnClickListener {
                post.numberOfSharedToInt++
                numberOfSharedView.text = numberEditing(post.numberOfSharedToInt)
            }
        }
    }
}

fun numberEditing(number: Long): String =
        when (number) {
            in 1000..1099 -> "${number/1000}k"
            in 1100..9999 -> "${number.toDouble()/1000}k"
            in 10_000..999_999 -> "${number.toDouble()/1000}k"
            in 1_000_000..1_099_999 -> "${number/1_000_000}m"
            in 1_100_000..999_999_999 -> "${number.toDouble()/1_000_000}m"
            else -> number.toString()
        }


