package ru.netology.nmedia.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Attachment
import ru.netology.nmedia.dto.Post
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val authorId: Long,
    val author: String,
    val authorAvatar: String,
    val content: String,
    val published: Long,
    val likedByMe: Boolean,
    val likes: Int = 0,
    val hidden: Boolean = false,
    @Embedded
    val attachment: Attachment?,
) {
    fun toDto() = Post(
        id = id,
        authorId = authorId,
        author = author,
        authorAvatar = authorAvatar,
        content = content,
        published = OffsetDateTime.ofInstant(Instant.ofEpochSecond(published), ZoneId.systemDefault()),
        likedByMe = likedByMe,
        likes = likes,
        attachment = attachment,
    )

    companion object {
        fun fromDto(dto: Post) =
            PostEntity(
                id = dto.id,
                authorId = dto.authorId,
                author = dto.author,
                authorAvatar = dto.authorAvatar,
                content = dto.content,
                published = dto.published.toEpochSecond(),
                likedByMe = dto.likedByMe,
                likes = dto.likes,
                attachment = dto.attachment,
            )
    }
}

fun List<PostEntity>.toDto(): List<Post> = map(PostEntity::toDto)
fun List<Post>.toEntity(hidden: Boolean = false): List<PostEntity> = map(PostEntity::fromDto).map {
    it.copy(hidden = hidden)
}
