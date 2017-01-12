package app.iago.treinamento_android.entity;

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

public class AccessToken {
    public String access_token;
    public String token_type;

    public String getAuthCredential() {
        final char firstChar = token_type.charAt(0);
        if (!Character.isUpperCase(firstChar)) {
            final String first = Character.toString(firstChar).toUpperCase();
            token_type = first + token_type.substring(1);
        }
        return token_type + " " + access_token;
    }

    /**
     * Created by IagoA on 11/01/2017.
     */

    public static interface GitHubOAuthApi {
            String BASE_URL = "https://github.com/login/oauth/";

            Retrofit RETROFIT = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(GitHubStatusApi.BASE_URL)
                    .build();

            @Headers({"Accept: application/json"})
            @FormUrlEncoded
            @POST("access_token")
            Observable<AccessToken> accessToken(
                    @Field("client_id") String clientId,
                    @Field("client_secret") String clientSecret,
                    @Field("code") String code);
    }
}