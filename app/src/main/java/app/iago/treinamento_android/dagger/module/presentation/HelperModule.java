package app.iago.treinamento_android.dagger.module.presentation;

import android.content.Context;

import app.iago.treinamento_android.presentation.helper.AppHelper;
import dagger.Module;
import dagger.Provides;
import dagger.Reusable;

@Module
public class HelperModule {
  @Provides
  @Reusable
  AppHelper provideTextHelper(Context context) {
      return new AppHelper(context);
  }
}