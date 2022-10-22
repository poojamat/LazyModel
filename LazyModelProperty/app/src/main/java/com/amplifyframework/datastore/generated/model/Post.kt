package com.amplifyframework.datastore.generated.model

import androidx.core.util.ObjectsCompat
import com.amplifyframework.AmplifyException
import com.amplifyframework.core.Consumer
import com.amplifyframework.core.model.InMemoryLazyModel
import com.amplifyframework.core.model.LazyModel
import com.amplifyframework.core.model.Model
import com.amplifyframework.core.model.annotations.BelongsTo
import com.amplifyframework.core.model.annotations.HasMany
import com.amplifyframework.core.model.annotations.ModelConfig
import com.amplifyframework.core.model.annotations.ModelField
import com.amplifyframework.core.model.query.predicate.QueryField
import com.amplifyframework.core.model.temporal.Temporal
import java.util.*

/** This is an auto generated class representing the Post type in your schema.  */
@ModelConfig(pluralName = "Posts")
class Post private constructor(
    @ModelField(targetType = "ID", isRequired = true) val id: String,
    @ModelField(targetType = "TITLE", isRequired = true) val title: String,

    @BelongsTo(targetName = "blogPostsId", type = Blog::class)
    @ModelField(targetType = "Blog")
    private val blog: LazyModel<Blog>
) : Model {

    suspend fun blog(): Blog = blog.require()



    /**
     * Method to be consumed by Java users to get the lazy loading behavior
     */
    fun blog(onSuccess: Consumer<Blog>, onFailure: Consumer<AmplifyException>){
        blog.get( onSuccess, onFailure)
    }

    @ModelField(targetType = "Comment")
    @HasMany(associatedWith = "post", type = Comment::class)
    val comments: List<Comment>? = null

    @ModelField(targetType = "AWSDateTime", isReadOnly = true)
    val createdAt: Temporal.DateTime? = null

    @ModelField(targetType = "AWSDateTime", isReadOnly = true)

    val updatedAt: Temporal.DateTime? = null
    override fun equals(other: Any?): Boolean {
        return if (this === other) {
            true
        } else if (other == null || javaClass != other.javaClass) {
            false
        } else {
            val post = other as Post
            ObjectsCompat.equals(id, post.id) &&
                    ObjectsCompat.equals(title, post.title) &&
                    ObjectsCompat.equals(blog, post.blog) &&
                    ObjectsCompat.equals(createdAt, post.createdAt) &&
                    ObjectsCompat.equals(updatedAt, post.updatedAt)
        }
    }

    override fun hashCode(): Int {
        return StringBuilder()
            .append(id)
            .append(title)
            .append(blog.getValue())
            .append(createdAt)
            .append(updatedAt)
            .toString()
            .hashCode()
    }

    override fun toString(): String {
        return StringBuilder()
            .append("Post {")
            .append("id=$id, ")
            .append("title=$title, ")
            .append("blog=" + blog.getValue().toString() + ", ")
            .append("createdAt=" + createdAt.toString() + ", ")
            .append("updatedAt=" + updatedAt.toString())
            .append("}")
            .toString()
    }

    fun copyOfBuilder(): CopyOfBuilder {
        return CopyOfBuilder(
                id,
                title,
                blog.getValue()!!
            )
    }

    interface TitleStep {
        fun title(title: String): BuildStep
    }

    interface BuildStep {
        fun build(): Post
        fun id(id: String): BuildStep
        fun blog(blog: Blog): BuildStep
    }

    open class Builder : TitleStep, BuildStep {
        private lateinit var id: String
        private lateinit var title: String
        private lateinit var blog: LazyModel<Blog>
        override fun build(): Post {
            val id = id
            return Post(
                id,
                title,
                blog)
        }

        override fun title(title: String): BuildStep {
            Objects.requireNonNull(title)
            this.title = title
            return this
        }

        override fun blog(blog: Blog): BuildStep {
            this.blog = InMemoryLazyModel(blog)
            return this
        }

        /**
         * @param id id
         * @return Current Builder instance, for fluent method chaining
         */
        override fun id(id: String): BuildStep {
            this.id = id
            return this
        }
    }

    inner class CopyOfBuilder (id: String, title: String, blog: Blog) :
        Builder() {
        override fun title(title: String): CopyOfBuilder {
            return super.title(title) as CopyOfBuilder
        }

        override fun blog(blog: Blog): CopyOfBuilder {
            return super.blog(blog) as CopyOfBuilder
        }

        init {
            super.id(id)
            super.title(title)
                .blog(blog)
        }
    }

    companion object {
        val ID = QueryField.field("Post", "id")
        val TITLE = QueryField.field("Post", "title")
        val BLOG = QueryField.field("Post", "blogPostsId")
        fun builder(): TitleStep {
            return Builder()
        }

        /**
         * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
         * This is a convenience method to return an instance of the object with only its ID populated
         * to be used in the context of a parameter in a delete mutation or referencing a foreign key
         * in a relationship.
         * @param id the id of the existing item this instance will represent
         * @return an instance of this model with only ID populated
         */
//        fun justId(id: String?): Post {
//            return Post(
//                id,
//                null,
//                null
//            )
//        }
    }
}