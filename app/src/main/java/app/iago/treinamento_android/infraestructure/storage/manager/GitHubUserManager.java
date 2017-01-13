package app.iago.treinamento_android.infraestructure.storage.manager;

import app.iago.treinamento_android.domain.entity.User;
import app.iago.treinamento_android.domain.repository.GitHubUserRepository;
import app.iago.treinamento_android.infraestructure.storage.service.GitHubUserService;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GitHubUserManager implements GitHubUserRepository {

  private final GitHubUserService mGitHubService;

  public GitHubUserManager(GitHubUserService gitHubService) {
      mGitHubService = gitHubService;
  }

  @Override
  public Observable<User> getUser(String authorization) {
      return mGitHubService.getUser(authorization)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread());
  }
}