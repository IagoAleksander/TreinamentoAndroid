package app.iago.treinamento_android;

import android.app.Application;

import app.iago.treinamento_android.dagger.DaggerDiComponent;
import app.iago.treinamento_android.dagger.DiComponent;
import app.iago.treinamento_android.dagger.module.ApplicationModule;

/**
 * Created by IagoA on 12/01/2017.
 */

public class MyApplication extends Application {
    private DiComponent mDiComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mDiComponent = DaggerDiComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public DiComponent getDaggerDiComponent() {
        return mDiComponent;
    }
}
