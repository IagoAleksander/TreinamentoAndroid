package app.iago.treinamento_android.presentation.ui.auth;

import app.iago.treinamento_android.domain.entity.Status;
import app.iago.treinamento_android.domain.entity.User;

public interface AuthContract {

  interface View {
      void onLoadStatusType(Status.Type statusType);

      void onAuthSuccess(String credential, User entity);

      void showError(String message);
  }

  interface Presenter {
      void setView(AuthContract.View view);

      void loadStatus();

      void callGetUser(String authorization);

      void callAccessToken(String cliId, String cliSecret, String code);
  }
}