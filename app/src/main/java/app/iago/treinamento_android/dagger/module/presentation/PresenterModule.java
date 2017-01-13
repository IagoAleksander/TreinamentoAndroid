package app.iago.treinamento_android.dagger.module.presentation;

import app.iago.treinamento_android.dagger.Scope.PerActivity;
import app.iago.treinamento_android.domain.repository.GitHubOAuthRepository;
import app.iago.treinamento_android.domain.repository.GitHubStatusRepository;
import app.iago.treinamento_android.domain.repository.GitHubUserRepository;
import app.iago.treinamento_android.presentation.ui.auth.AuthContract;
import app.iago.treinamento_android.presentation.ui.auth.AuthPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

  @PerActivity
  @Provides
  AuthContract.Presenter provideMainPresenter(
          GitHubUserRepository gitHubUserRepository,
          GitHubStatusRepository gitHubStatusRepository,
          GitHubOAuthRepository gitHubOAuthRepository) {
      return new AuthPresenter(gitHubUserRepository,
              gitHubStatusRepository, 
              gitHubOAuthRepository);
  }
}