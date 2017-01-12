package app.iago.treinamento_android.dagger.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by IagoA on 12/01/2017.
 */

@Module
public class ApplicationModule {
    private Application mApplication;

    public ApplicationModule(Application app) {
        mApplication = app;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    Context providesContext(Application app) {
        return app.getBaseContext();
    }
}
