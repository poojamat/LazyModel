package com.example.lazymodelproperty

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.model.query.Where
import com.amplifyframework.datastore.AWSDataStorePlugin
import com.amplifyframework.datastore.generated.model.Blog
import com.amplifyframework.datastore.generated.model.Post
import com.amplifyframework.kotlin.core.Amplify
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

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
//        val date = Date() // This object contains the current date value
//        val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
//        val currentDate = formatter.format(date)
//
//        val blog = Blog.builder()
//            .name("test blog")
//            .id(currentDate + Math.random() + "BlogId")
//            .build()
//        val post = Post.builder()
//            .title("my test")
//            .id(currentDate + Math.random() + "PostId")
//            .blog(blog)
//            .build()
//        runBlocking {
//
//            Amplify.DataStore.start()
//
//            Amplify.DataStore.save(blog)
//            Amplify.DataStore.save(post)
//            Log.i("LazyModel","Successfully saved post")

//            Amplify.DataStore.query(Blog::class)
//                .catch { Log.e("MyAmplifyApp", "Query failed", it) }
//                .onEach { it.posts() }
//                .catch { Log.e("MyAmplifyApp", "Update failed", it) }
//                .collect { Log.i("MyAmplifyApp", "Updated a post") }
//
//
//        }

//        val blogPosts = blog.posts
//        val blogPostsLazy = blog.postsLazy
//        var blogPostsLazyDeferred: List<Post>?
//        var savedBlogPostsLazyDeferred: List<Post>?
//        var blogPostsLazyDeferredFun: List<Post>?
//        var savedBlogPostsLazyDeferredFun: List<Post>?

        //val savedBlogPosts = savedBlog?.get(0)?.posts
       // val savedBlogPostsLazy = savedBlog?.get(0)?.postsLazy


        runBlocking {
            try {
                //blogPostsLazyDeferred = blog.postsLazyDeferred.await()
                //savedBlogPostsLazyDeferred = savedBlog?.get(0)?.postsLazyDeferred?.await()
                Amplify.DataStore.query(Blog::class)
                    .catch { Log.e("MyAmplifyApp", "Query failed", it) }
                    .onEach {
                        it.posts()
                    }
                    .catch { Log.e("MyAmplifyApp", "Update failed", it) }
                    .collect { Log.i("MyAmplifyApp", "Updated a post") }

            } catch (ex: Exception){
                val message = ex.message
                ex.stackTrace
            }
           // val blogPostsLazyFun = blog.postsLazy()
           // val savedBlogPostsLazyDeferred = savedBlog?.get(0)?.postsLazy()
        }
    }

    @Before
    fun setup() {
        try {

            Amplify.addPlugin(AWSDataStorePlugin())
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.configure(InstrumentationRegistry.getInstrumentation().targetContext)
            Log.i("MyAmplifyApp", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getLazyBlog() {

        runBlocking {
            try {
                //blogPostsLazyDeferred = blog.postsLazyDeferred.await()
                //savedBlogPostsLazyDeferred = savedBlog?.get(0)?.postsLazyDeferred?.await()
                Amplify.DataStore.query(
                    Post::class, Where.identifier(
                        Post::class.java,
                        "16-09-2022 13:15:550.013714803931628716PostId"
                    )
                )
                    .catch { Log.e("MyAmplifyApp", "Query failed", it) }
                    .onEach {
                        it.blog()
                    }
                    .catch { Log.e("MyAmplifyApp", "Update failed", it) }
                    .collect { Log.i("MyAmplifyApp", "Updated a post") }

            } catch (ex: Exception) {
                val message = ex.message
                ex.stackTrace
            }
        }

    }

    @Test
    fun listPosts() {
        val latch = CountDownLatch(1)
        com.amplifyframework.core.Amplify.API.query(
            ModelQuery.list(Post::class.java),
            {
                Log.i("MyAmplifyApp", "Query results = ${it.data }")
                runBlocking {
                    try {
                        val blog = it.data.iterator().next().blog()
                    } catch (ex: Exception){
                        val message = ex.message
                        ex.stackTrace
                    }}
                latch.countDown()
            },
            { Log.e("MyAmplifyApp", "Query failed", it) }
        );
        Assert.assertTrue(latch.await(3000, TimeUnit.MINUTES))
    }
}