package app.iago.treinamento_android.dagger.module.infraestructure;

import javax.inject.Singleton;

import app.iago.treinamento_android.domain.repository.GitHubOAuthRepository;
import app.iago.treinamento_android.domain.repository.GitHubStatusRepository;
import app.iago.treinamento_android.domain.repository.GitHubUserRepository;
import app.iago.treinamento_android.infraestructure.storage.manager.GitHubOAuthManager;
import app.iago.treinamento_android.infraestructure.storage.manager.GitHubStatusManager;
import app.iago.treinamento_android.infraestructure.storage.manager.GitHubUserManager;
import app.iago.treinamento_android.infraestructure.storage.service.GitHubOAuthService;
import app.iago.treinamento_android.infraestructure.storage.service.GitHubStatusService;
import app.iago.treinamento_android.infraestructure.storage.service.GitHubUserService;
import dagger.Module;
import dagger.Provides;

@Module
public class ManagerModule {

  @Singleton
  @Provides
  GitHubUserRepository providesGitHubRepository(
          GitHubUserService gitHubService) {
      return new GitHubUserManager(gitHubService);
  }

  @Singleton
  @Provides
  GitHubStatusRepository providesGitHubStatusRepository(
          GitHubStatusService gitHubStatusService) {
      return new GitHubStatusManager(gitHubStatusService);
  }

  @Singleton
  @Provides
  GitHubOAuthRepository providesGitHubOAuthRepository(
          GitHubOAuthService gitHubOAuthService) {
      return new GitHubOAuthManager(gitHubOAuthService);
  }

}