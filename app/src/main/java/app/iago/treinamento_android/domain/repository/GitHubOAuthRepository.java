package app.iago.treinamento_android.domain.repository;

import app.iago.treinamento_android.domain.entity.AccessToken;
import rx.Observable;

/**
 * Created by IagoA on 11/01/2017.
 */

public interface GitHubOAuthRepository {

            Observable<AccessToken> accessToken(
                    String clientId,
                    String clientSecret,
                    String code);
    }
