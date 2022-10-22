package com.example.lazymodelproperty;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Blog;
import com.amplifyframework.datastore.generated.model.Post;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Java {

    public void getAmplify(Context context) throws AmplifyException {
        try {
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(context);
            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }
        Date date = new Date() ;// This object contains the current date value
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDate = formatter.format(date);

        Blog blog = Blog.Companion.builder()
                .name("test blog")
                .id(currentDate + Math.random() + "BlogId")
                .build();
        Amplify.addPlugin(new AWSDataStorePlugin());
        String postId = currentDate + Math.random() + "PostId";
        Post post = Post.Companion.builder()
                .title("my test")
                .id(postId)
                .blog(blog).build();
        Amplify.DataStore.start(
                () -> Log.i("MyAmplifyApp", "Datastore stated."),
                error -> Log.i("MyAmplifyApp", "Could not start datastore."));

        Amplify.DataStore.save(blog,
                saved -> Log.i("MyAmplifyApp", "Saved a blog."),
                error -> Log.i("MyAmplifyApp", "Failed to save a blog."));
        Amplify.DataStore.save(post,
                saved -> Log.i("MyAmplifyApp", "Saved a post."),
                error -> Log.i("MyAmplifyApp", "Failed to save a post."));

        Amplify.DataStore.query(Post.class, Where.identifier(Post.class, postId),
                matches -> {
                    if (matches.hasNext()) {
                        Post original = matches.next();
//                        Blog blogQuery = original
                    }
                },
                failure -> Log.e("MyAmplifyApp", "Query failed.", failure)
        );
    }


}
