package app.iago.treinamento_android.infraestructure.storage.manager;

import app.iago.treinamento_android.domain.entity.AccessToken;
import app.iago.treinamento_android.domain.repository.GitHubOAuthRepository;
import app.iago.treinamento_android.infraestructure.storage.service.GitHubOAuthService;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GitHubOAuthManager implements GitHubOAuthRepository {

  private final GitHubOAuthService mGitHubOAuthService;

  public GitHubOAuthManager(GitHubOAuthService gitHubOAuthService) {
      mGitHubOAuthService = gitHubOAuthService;
  }

  @Override
  public Observable<AccessToken> accessToken(String clientId, String clientSecret, String code) {
      return mGitHubOAuthService.accessToken(clientId, clientSecret, code)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread());
  }
}