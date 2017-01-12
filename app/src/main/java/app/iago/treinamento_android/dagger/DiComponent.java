package app.iago.treinamento_android.dagger;

import javax.inject.Singleton;

import app.iago.treinamento_android.MainActivity;
import app.iago.treinamento_android.dagger.module.ApplicationModule;
import app.iago.treinamento_android.dagger.module.NetworkModule;
import app.iago.treinamento_android.dagger.module.PreferenceModule;
import app.iago.treinamento_android.dagger.module.ServiceModule;
import dagger.Component;

/**
 * Created by IagoA on 12/01/2017.
 */

@Singleton
@Component(modules = {
        ApplicationModule.class,
        PreferenceModule.class,
        NetworkModule.class,
        ServiceModule.class
})
public interface DiComponent {
    void inject(MainActivity activity);
}