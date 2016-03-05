package mock.brains.bigtestapp.api;

import mock.brains.bigtestapp.model.Album;
import mock.brains.bigtestapp.model.Comment;
import mock.brains.bigtestapp.model.Photo;
import mock.brains.bigtestapp.model.Post;
import mock.brains.bigtestapp.model.Todo;
import mock.brains.bigtestapp.model.User;
import rx.Observable;

public class ApiManager {

    ApiInterface apiInterface;

    public ApiManager(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    public Observable<User> getUsers() {
        return apiInterface.getUserList()
                .flatMap(Observable::from);
    }

    public Observable<Post> getPosts(int userId) {
        return apiInterface.getPostList(userId)
                .flatMap(Observable::from);
    }

    public Observable<Comment> getComments(int postId) {
        return apiInterface.getCommentList(postId)
                .flatMap(Observable::from);
    }

    public Observable<Todo> getTodos(int userId) {
        return apiInterface.getTodoList(userId)
                .flatMap(Observable::from);
    }

    public Observable<Album> getAlbums(int userId) {
        return apiInterface.getAlbumList(userId)
                .flatMap(Observable::from);
    }

    public Observable<Photo> getPhotos(int albumId) {
        return apiInterface.getPhotoList(albumId)
                .flatMap(Observable::from);
    }
}