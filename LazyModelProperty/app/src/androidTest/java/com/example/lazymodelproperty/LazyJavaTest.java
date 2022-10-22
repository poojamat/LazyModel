//package com.example.lazymodelproperty;
//
//import android.util.Log;
//import androidx.test.platform.app.InstrumentationRegistry;
//
//import com.amplifyframework.AmplifyException;
//import com.amplifyframework.api.aws.AWSApiPlugin;
//import com.amplifyframework.core.Amplify;
//import com.amplifyframework.core.model.query.Where;
//import com.amplifyframework.datastore.AWSDataStorePlugin;
//import com.amplifyframework.datastore.DataStoreConfiguration;
//import com.amplifyframework.datastore.generated.model.Blog;
//import com.amplifyframework.datastore.generated.model.Post;
//
//import org.junit.Test;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public final class LazyJavaTest {
//
//    @Test
//    public void getLazyPost() throws AmplifyException {
//
//        try {
//            Amplify.addPlugin(new AWSDataStorePlugin());
//            Amplify.addPlugin(new AWSApiPlugin());
//            Amplify.configure(InstrumentationRegistry.getInstrumentation().getTargetContext());
//            Log.i("MyAmplifyApp", "Initialized Amplify");
//        } catch (AmplifyException error) {
//            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
//        }
//        Date date = new Date() ;// This object contains the current date value
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        String currentDate = formatter.format(date);
//
//        Blog blog = Blog.Companion.builder()
//                .name("test blog")
//                .id(currentDate + Math.random() + "BlogId")
//                .build();
//        Amplify.addPlugin(AWSDataStorePlugin.builder().dataStoreConfiguration(
//                        DataStoreConfiguration.builder()
//                                .doSyncRetry(true)
//                                .build())
//                .build());
//        String postId = currentDate + Math.random() + "PostId";
//        Post post = Post.Companion.builder()
//                .title("my test")
//                .id(postId)
//                .blog(blog).build();
//        Amplify.DataStore.start(
//                () -> Log.i("MyAmplifyApp", "Datastore stated."),
//                error -> Log.i("MyAmplifyApp", "Could not start datastore."));
//
//        Amplify.DataStore.save(blog,
//                saved -> Log.i("MyAmplifyApp", "Saved a blog."),
//                error -> Log.i("MyAmplifyApp", "Failed to save a blog."));
//        Amplify.DataStore.save(post,
//                saved -> Log.i("MyAmplifyApp", "Saved a post."),
//                error -> Log.i("MyAmplifyApp", "Failed to save a post."));
//
//        sayHello(new Continuation<String>() {
//            @Override
//            public CoroutineContext getContext() {
//                return EmptyCoroutineContext.INSTANCE;
//            }
//
//            @Override
//            public void resume(String value) {
//                doSomethingWithResult(value);
//            }
//
//            @Override
//            public void resumeWithException(@NotNull Throwable throwable) {
//                doSomethingWithError(throwable);
//            }
//        });
//
//        Amplify.DataStore.query(Post.class, Where.identifier(Post.class, postId),
//                matches -> {
//                    if (matches.hasNext()) {
//                        Post original = matches.next();
//                       Blog blogQuery = original.blog();
//                    }
//                },
//                failure -> Log.e("MyAmplifyApp", "Query failed.", failure)
//        );
//    }
//}
