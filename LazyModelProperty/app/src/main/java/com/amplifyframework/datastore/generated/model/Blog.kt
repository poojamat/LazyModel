package com.amplifyframework.datastore.generated.model

import androidx.core.util.ObjectsCompat
import com.amplifyframework.core.model.LazyList
import com.amplifyframework.core.model.Model
import com.amplifyframework.core.model.annotations.HasMany
import com.amplifyframework.core.model.annotations.ModelConfig
import com.amplifyframework.core.model.annotations.ModelField
import com.amplifyframework.core.model.query.Where
import com.amplifyframework.core.model.query.predicate.QueryField
import com.amplifyframework.core.model.query.predicate.QueryField.field
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.kotlin.core.Amplify
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.toList
import java.util.*

/** This is an auto generated class representing the Blog type in your schema.  */
@ModelConfig(pluralName = "Blogs", version = 1)
public class Blog (
    @field:ModelField(
        targetType = "ID",
        isRequired = true
    ) val id: String, @field:ModelField(targetType = "String", isRequired = true)
    val name: String
) : Model {
    @ModelField(targetType = "Post")
    @HasMany(associatedWith = "blog", type = Post::class, targetNames = ["blogPostsId"])
    private val posts: LazyList<Post>? = null

    suspend fun posts(): List<Post>? = posts?.get()










    @DelicateCoroutinesApi
    @ExperimentalCoroutinesApi
    var postsRunBlocking: List<Post>?
        get() {
            runBlocking {
               _post = postsLazy()
           }
            return _post
        }
        private set(value) { _post = value}

    private var _post: List<Post>? = null


    @DelicateCoroutinesApi
    @ExperimentalCoroutinesApi
    val postsLazy: List<Post>? by lazy(LazyThreadSafetyMode.PUBLICATION) {
        var posts: List<Post>? = null
        runBlocking {
            posts = Amplify.DataStore.query(Post::class, Where.matches(Post.BLOG.eq(this)))
                    .toList()
        }
        posts
    }

    @ExperimentalCoroutinesApi
    @DelicateCoroutinesApi
    val postsLazyDeferred = GlobalScope.async( Dispatchers.Main, start = CoroutineStart.LAZY)
    {
        Amplify.DataStore.query(Post::class, Where.matches(Post.BLOG.eq(this))).toList()
    }


    @ExperimentalCoroutinesApi
    @DelicateCoroutinesApi
    fun postsLazyDeferredFun(scope: CoroutineScope): Deferred<List<Post>> {
        return scope.async( Dispatchers.Main, start = CoroutineStart.LAZY)
        {
            Amplify.DataStore.query(Post::class, Where.matches(Post.BLOG.eq(this))).toList()
        }
    }


    private var postsLazyCool: List<Post>? = null
    //How to handle wrappers which can handle data loading for datastore and api
    @ExperimentalCoroutinesApi
    @DelicateCoroutinesApi
    suspend fun  postsLazy() : List<Post> {
        if (postsLazyCool == null){
            postsLazyCool =  Amplify.DataStore.query(Post::class, Where.matches(Post.BLOG.eq(this)) )
                .toList()
        }
        return postsLazyCool as List<Post>
    }

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
            val blog = other as Blog
            ObjectsCompat.equals(id, blog.id) &&
                    ObjectsCompat.equals(name, blog.name) &&
                    ObjectsCompat.equals(createdAt, blog.createdAt) &&
                    ObjectsCompat.equals(updatedAt, blog.updatedAt)
        }
    }

    override fun hashCode(): Int {
        return StringBuilder()
            .append(id)
            .append(name)
            .append(createdAt)
            .append(updatedAt)
            .toString()
            .hashCode()
    }

    override fun toString(): String {
        return StringBuilder()
            .append("Blog {")
            .append("id=" + id.toString() + ", ")
            .append("name=" + name.toString() + ", ")
            .append("createdAt=" + java.lang.String.valueOf(createdAt) + ", ")
            .append("updatedAt=" + java.lang.String.valueOf(updatedAt))
            .append("}")
            .toString()
    }

    fun copyOfBuilder(): CopyOfBuilder {
        return CopyOfBuilder(
            id,
            name
        )
    }

    interface NameStep {
        fun name(name: String): BuildStep
    }

    interface BuildStep {
        fun build(): Blog
        fun id(id: String): BuildStep
    }

   public open class Builder : NameStep, BuildStep {
        private var id: String? = null
       private var name: String? = null
       override fun build(): Blog {
            val id = id
            return Blog(
                id.orEmpty(),
                name.orEmpty()
            )
        }

        override fun name(name: String): BuildStep {
            Objects.requireNonNull(name)
            this.name = name
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

    inner class CopyOfBuilder(id: String, name: String) : Builder() {
        override fun name(name: String): CopyOfBuilder {
            return super.name(name) as CopyOfBuilder
        }

        init {
            super.id(id)
            super.name(name)
        }
    }

    companion object {
        val ID: QueryField = field("Blog", "id")
        val NAME: QueryField = field("Blog", "name")
        fun builder(): NameStep {
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
//        fun justId(id: String): Blog {
//            return Blog(
//                id,
//                null
//            )
//        }
    }
}


