package com.example.lazymodelproperty

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.datastore.AWSDataStorePlugin
import com.amplifyframework.datastore.generated.model.Blog
import com.amplifyframework.datastore.generated.model.Post
import com.amplifyframework.kotlin.core.Amplify
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.lazymodelproperty", appContext.packageName)
    }

    @DelicateCoroutinesApi
    @ExperimentalCoroutinesApi
    @Test
    fun getLazyPost() {
        try {

            Amplify.addPlugin(AWSDataStorePlugin())
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.configure(InstrumentationRegistry.getInstrumentation().targetContext)
            Log.i("MyAmplifyApp", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error)
        }
        val date = Date() // This object contains the current date value
        val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        val currentDate = formatter.format(date)

        val blog = Blog.builder()
            .name("test blog")
            .id(currentDate + Math.random() + "BlogId")
            .build()
        val post = Post.builder()
            .title("my test")
            .id(currentDate + Math.random() + "PostId")
            .blog(blog)
            .build()
        runBlocking {

            Amplify.DataStore.start()

            Amplify.DataStore.save(blog)
            Amplify.DataStore.save(post)
            Log.i("LazyModel","Successfully saved post")

            Amplify.DataStore.query(Post::class)
                .catch { Log.e("MyAmplifyApp", "Query failed", it) }
                .onEach { it.blog() }
                .catch { Log.e("MyAmplifyApp", "Update failed", it) }
                .collect { Log.i("MyAmplifyApp", "Updated a post") }


        }


        val blogPosts = blog.posts
        val blogPostsLazy = blog.postsLazy
        var blogPostsLazyDeferred: List<Post>?
        var savedBlogPostsLazyDeferred: List<Post>?
        var blogPostsLazyDeferredFun: List<Post>?
        var savedBlogPostsLazyDeferredFun: List<Post>?

        //val savedBlogPosts = savedBlog?.get(0)?.posts
       // val savedBlogPostsLazy = savedBlog?.get(0)?.postsLazy


        runBlocking {
            try {
                blogPostsLazyDeferred = blog.postsLazyDeferred.await()
                //savedBlogPostsLazyDeferred = savedBlog?.get(0)?.postsLazyDeferred?.await()
            } catch (ex: Exception){
                val message = ex.message
                ex.stackTrace
            }
            val blogPostsLazyFun = blog.postsLazy()
           // val savedBlogPostsLazyDeferred = savedBlog?.get(0)?.postsLazy()
        }
    }

    @Test
    fun getLazyBlog() {

    }
}