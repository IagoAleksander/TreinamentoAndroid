package app.iago.treinamento_android.dagger.module;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import app.iago.treinamento_android.R;
import dagger.Module;
import dagger.Provides;

@Module
public class PreferenceModule {

  @Provides
  @Singleton
//  @Named("shared")
  SharedPreferences providesSharedPreferences(Context context) {
      final String fileName = context.getString(R.string.sp_file);
      return context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
  }
}