package app.iago.treinamento_android.presentation.ui.auth;

import app.iago.treinamento_android.domain.entity.Status;
import app.iago.treinamento_android.domain.repository.GitHubOAuthRepository;
import app.iago.treinamento_android.domain.repository.GitHubStatusRepository;
import app.iago.treinamento_android.domain.repository.GitHubUserRepository;

public class AuthPresenter implements AuthContract.Presenter {

  private AuthContract.View mView;
  private GitHubUserRepository mGitHubUserRepository;
  private GitHubStatusRepository mGitHubStatusRepository;
  private GitHubOAuthRepository mGitHubOAuthRepository;

  public AuthPresenter(GitHubUserRepository gitHubRepository,
                       GitHubStatusRepository gitHubStatusRepository, 
                       GitHubOAuthRepository gitHubOAuthRepository) {
      mGitHubUserRepository = gitHubRepository;
      mGitHubStatusRepository = gitHubStatusRepository;
      mGitHubOAuthRepository = gitHubOAuthRepository;
  }

  @Override
  public void setView(AuthContract.View view) {
      mView = view;
  }

  @Override
  public void loadStatus() {
      mGitHubStatusRepository.lastMessage()
              .subscribe(entity -> {
                  mView.onLoadStatusType(entity.status);
              }, error -> {
                  mView.onLoadStatusType(Status.Type.MAJOR);
              });
  }

  @Override
  public void callGetUser(String authorization) {
      mGitHubUserRepository.getUser(authorization)
              .subscribe(entity -> {
                  mView.onAuthSuccess(authorization, entity);
              }, error -> {
                  mView.showError(error.getMessage());
              });
  }

  @Override
  public void callAccessToken(String clientId, 
                             String clientSecret, 
                             String code) {
      mGitHubOAuthRepository.accessToken(clientId, clientSecret, code)
              .subscribe(entity -> {
                  callGetUser(entity.getAuthCredential());
              }, error -> {
                  mView.showError(error.getMessage());
              });
  }
}