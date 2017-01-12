package app.iago.treinamento_android.dagger.module;

import javax.inject.Named;
import javax.inject.Singleton;

import app.iago.treinamento_android.entity.AccessToken;
import app.iago.treinamento_android.entity.GitHubStatusApi;
import app.iago.treinamento_android.entity.GitHubUserApi;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

import static app.iago.treinamento_android.dagger.module.NetworkModule.RETROFIT_GITHUB;
import static app.iago.treinamento_android.dagger.module.NetworkModule.RETROFIT_GITHUB_OAUTH;
import static app.iago.treinamento_android.dagger.module.NetworkModule.RETROFIT_GITHUB_STATUS;

@Module
public class ServiceModule {

  @Singleton
  @Provides
  GitHubUserApi providesGitHub(
          @Named(RETROFIT_GITHUB) Retrofit retrofit) {
      return retrofit.create(GitHubUserApi.class);
  }

  @Singleton
  @Provides
  GitHubStatusApi providesGitHubStatus(
          @Named(RETROFIT_GITHUB_STATUS) Retrofit retrofit) {
      return retrofit.create(GitHubStatusApi.class);
  }

  @Singleton
  @Provides
  AccessToken.GitHubOAuthApi providesGitHubOAuth(
          @Named(RETROFIT_GITHUB_OAUTH) Retrofit retrofit) {
      return retrofit.create(AccessToken.GitHubOAuthApi.class);
  }

}