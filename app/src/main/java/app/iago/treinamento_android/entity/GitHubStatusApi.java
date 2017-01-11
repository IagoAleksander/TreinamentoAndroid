package app.iago.treinamento_android.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by IagoA on 09/01/2017.
 */

public interface GitHubStatusApi {

    String BASE_URL = "https://status.github.com/api/";

//    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();
//    //passar gson no retrofitBuilder para instanciar Date do payload
//
//    Retrofit RETROFIT = new Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .baseUrl(GitHubStatusApi.BASE_URL)
//            .build();

    Retrofit RETROFIT = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create
                    (new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                            .create()))
            .baseUrl(GitHubStatusApi.BASE_URL)
            .build();

    @GET("last-message.json")
    Call<Status> lastMessage();

}
