package app.iago.treinamento_android.infraestructure.storage.service;

import app.iago.treinamento_android.domain.entity.User;
import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;

/**
 * Created by IagoA on 10/01/2017.
 */

public interface GitHubUserService {

    String BASE_URL = "https://api.github.com/";

    @GET("user")
    Observable<User> getUser(
            @Header("Authorization") String credential);

}
