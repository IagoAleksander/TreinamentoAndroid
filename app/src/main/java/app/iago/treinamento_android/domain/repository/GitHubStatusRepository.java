package app.iago.treinamento_android.domain.repository;

import app.iago.treinamento_android.domain.entity.Status;
import rx.Observable;

/**
 * Created by IagoA on 09/01/2017.
 */

public interface GitHubStatusRepository {

    Observable<Status> lastMessage();

}
