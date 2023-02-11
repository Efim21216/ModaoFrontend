package ru.nsu.fit.modao.repository;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ru.nsu.fit.modao.model.NewUser;
import ru.nsu.fit.modao.model.User;
import ru.nsu.fit.modao.model.UserIn;

public interface ApiService {
    @GET("/user/{id}")
    Single<User> getUser(@Path("id") long id);
    @POST("/user")
    Single<Long> createUser(@Body NewUser user);
    @POST("/user/in")
    Single<Long> login(@Body UserIn user);
}
