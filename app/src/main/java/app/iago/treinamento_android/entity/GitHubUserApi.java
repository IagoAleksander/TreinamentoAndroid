package app.iago.treinamento_android.entity;

import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;

/**
 * Created by IagoA on 10/01/2017.
 */

public interface GitHubUserApi {

    String BASE_URL = "https://api.github.com/";

    Retrofit RETROFIT = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(GitHubUserApi.BASE_URL)
            .build();

    @GET("user")
    Call<User> basicAuth(
            @Header("Authorization") String credential);

}
