package com.example.lazymodelproperty

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.datastore.AWSDataStorePlugin
import com.amplifyframework.datastore.generated.model.Blog
import com.amplifyframework.datastore.generated.model.Post
import com.amplifyframework.kotlin.core.Amplify
import com.example.lazymodelproperty.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.toList
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding


    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        try {

            Amplify.addPlugin(AWSDataStorePlugin())
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.configure(this)
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
        var savedBlog: List<Blog>?
        runBlocking {
            Amplify.DataStore.save(blog)
            Amplify.DataStore.save(post)
            savedBlog = Amplify.DataStore.query(Blog::class).toList()

        }
        val blogPosts = blog.posts
        //val blogPostsLazy = blog.postsLazy
        var blogPostsLazy2: List<Post>?
        var savedBlogPostsLazy2: List<Post>?

        val savedBlogPosts = savedBlog?.get(0)?.posts
        // val savedBlogPostsLazy = savedBlog?.get(0)?.postsLazy


        binding.fab.setOnClickListener { view ->
                try {
                    lifecycleScope.launch {
//                        val postLazy2 = blog.postsLazy2(lifecycleScope)
//                        postLazy2.start()
//                        blogPostsLazy2 = postLazy2.await()
//                        blogPostsLazy2 = postLazy2.getCompleted()
//                        savedBlogPostsLazy2 = savedBlog?.get(0)?.postsLazy2?.await()

                        blogPostsLazy2 = blog.postsLazy()
                        savedBlogPostsLazy2 = savedBlog?.get(0)?.postsLazy()
                    }

                } catch (ex: Exception){
                    val message = ex.message
                    ex.stackTrace
                }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}