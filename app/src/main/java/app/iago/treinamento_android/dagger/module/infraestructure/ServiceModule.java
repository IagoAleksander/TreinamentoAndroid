package app.iago.treinamento_android.dagger.module.infraestructure;

import javax.inject.Named;
import javax.inject.Singleton;

import app.iago.treinamento_android.infraestructure.storage.service.GitHubOAuthService;
import app.iago.treinamento_android.infraestructure.storage.service.GitHubStatusService;
import app.iago.treinamento_android.infraestructure.storage.service.GitHubUserService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

import static app.iago.treinamento_android.dagger.module.infraestructure.NetworkModule.RETROFIT_GITHUB;
import static app.iago.treinamento_android.dagger.module.infraestructure.NetworkModule.RETROFIT_GITHUB_OAUTH;
import static app.iago.treinamento_android.dagger.module.infraestructure.NetworkModule.RETROFIT_GITHUB_STATUS;

@Module
public class ServiceModule {

  @Singleton
  @Provides
  GitHubUserService providesGitHub(
          @Named(RETROFIT_GITHUB) Retrofit retrofit) {
      return retrofit.create(GitHubUserService.class);
  }

  @Singleton
  @Provides
  GitHubStatusService providesGitHubStatus(
          @Named(RETROFIT_GITHUB_STATUS) Retrofit retrofit) {
      return retrofit.create(GitHubStatusService.class);
  }

  @Singleton
  @Provides
  GitHubOAuthService providesGitHubOAuth(
          @Named(RETROFIT_GITHUB_OAUTH) Retrofit retrofit) {
      return retrofit.create(GitHubOAuthService.class);
  }

}