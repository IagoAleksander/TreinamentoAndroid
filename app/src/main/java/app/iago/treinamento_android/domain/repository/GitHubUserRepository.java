package app.iago.treinamento_android.domain.repository;

import app.iago.treinamento_android.domain.entity.User;
import rx.Observable;

/**
 * Created by IagoA on 10/01/2017.
 */

public interface GitHubUserRepository {

    Observable<User> getUser(
            String credential);

}
