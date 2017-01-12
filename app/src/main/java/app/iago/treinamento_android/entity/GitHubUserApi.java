package app.iago.treinamento_android.entity;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;

/**
 * Created by IagoA on 10/01/2017.
 */

public interface GitHubUserApi {

    String BASE_URL = "https://api.github.com/";

    Retrofit RETROFIT = new Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(GitHubUserApi.BASE_URL)
            .build();

    @GET("user")
    Observable<User> basicAuth(
            @Header("Authorization") String credential);

}
