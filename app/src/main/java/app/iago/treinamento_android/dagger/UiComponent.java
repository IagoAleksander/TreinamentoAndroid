package app.iago.treinamento_android.dagger;

import app.iago.treinamento_android.dagger.Scope.PerActivity;
import app.iago.treinamento_android.dagger.module.presentation.PresenterModule;
import app.iago.treinamento_android.presentation.ui.auth.AuthActivity;
import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = {PresenterModule.class})
public interface UiComponent {
  void inject(AuthActivity activity);
}