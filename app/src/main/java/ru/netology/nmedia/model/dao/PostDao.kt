package ru.netology.nmedia.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.netology.nmedia.model.entity.PostEntity


@Dao
interface PostDao {
    @Query("SELECT * FROM PostEntity ORDER BY id")
    fun getAll(): LiveData<List<PostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(post: PostEntity)

    @Query(
        """
            UPDATE PostEntity SET
               numberOfLikesToInt = numberOfLikesToInt + CASE WHEN likedByMe THEN -1 ELSE 1 END,
               likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
           WHERE id = :id
           """
    )
    fun likeById(id: Long)

    @Delete
    fun remove(post: PostEntity)

    @Query("DELETE FROM PostEntity WHERE id = :id")
    fun removeById(id: Long)

    @Query(
        """
           UPDATE PostEntity SET
               numberOfSharedToInt = numberOfSharedToInt + 1
           WHERE id = :id
        """
    )
    fun shareById(id: Long)

    @Query(
        """
           UPDATE PostEntity SET
               numberOfOverlookedToInt = numberOfLikesToInt + 1
           WHERE id = :id
        """
    )
    fun overlookById(id: Long)
}