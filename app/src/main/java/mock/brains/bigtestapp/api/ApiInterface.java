package mock.brains.bigtestapp.api;

import java.util.ArrayList;

import javax.inject.Inject;

import mock.brains.bigtestapp.model.Album;
import mock.brains.bigtestapp.model.Comment;
import mock.brains.bigtestapp.model.Photo;
import mock.brains.bigtestapp.model.Post;
import mock.brains.bigtestapp.model.Todo;
import mock.brains.bigtestapp.model.User;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

    /**
     * JSONPlaceholder API
     * http://http://jsonplaceholder.typicode.com/
     */
public interface ApiInterface {

    @GET("users")
    Observable<ArrayList<User>> getUserList();

    @GET("posts")
    Observable<ArrayList<Post>> getPostList(
            @Query("userId") int userId
    );

    @GET("comments")
    Observable<ArrayList<Comment>> getCommentList(
            @Query("postId") int postId
    );

    @GET("todos")
    Observable<ArrayList<Todo>> getTodoList(
            @Query("userId") int userId
    );

    @GET("albums")
    Observable<ArrayList<Album>> getAlbumList(
            @Query("userId") int userId
    );

    @GET("photos")
    Observable<ArrayList<Photo>> getPhotoList(
            @Query("albumId") int albumId
    );

    /**
     * Request for test
     *
     * @return 5000 elements with links
     */
    @GET("photos")
    Observable<ArrayList<Photo>> getAllPhotoList();
}
