package app.iago.treinamento_android.entity;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by IagoA on 09/01/2017.
 */

public interface GitHubStatusApi {

    String BASE_URL = "https://status.github.com/api/";

    @GET("last-message.json")
    Observable<Status> lastMessage();

}
