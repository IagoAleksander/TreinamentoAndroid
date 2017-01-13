package app.iago.treinamento_android.infraestructure.storage.manager;

import app.iago.treinamento_android.domain.entity.Status;
import app.iago.treinamento_android.domain.repository.GitHubStatusRepository;
import app.iago.treinamento_android.infraestructure.storage.service.GitHubStatusService;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GitHubStatusManager implements GitHubStatusRepository {

  private final GitHubStatusService mGitHubStatusService;

  public GitHubStatusManager(GitHubStatusService gitHubStatusService) {
      mGitHubStatusService = gitHubStatusService;
  }

  @Override
  public Observable<Status> lastMessage() {
      return mGitHubStatusService.lastMessage()
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread());
  }
}