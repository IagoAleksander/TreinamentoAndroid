package app.iago.treinamento_android.infraestructure.storage.service;

import app.iago.treinamento_android.domain.entity.AccessToken;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
     * Created by IagoA on 11/01/2017.
     */

public  interface GitHubOAuthService {
            String BASE_URL = "https://github.com/login/oauth/";

            Retrofit RETROFIT = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(GitHubStatusService.BASE_URL)
                    .build();

            @Headers({"Accept: application/json"})
            @FormUrlEncoded
            @POST("access_token")
            Observable<AccessToken> accessToken(
                    @Field("client_id") String clientId,
                    @Field("client_secret") String clientSecret,
                    @Field("code") String code);
    }