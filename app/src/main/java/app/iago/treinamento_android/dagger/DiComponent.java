package app.iago.treinamento_android.dagger;

import javax.inject.Singleton;

import app.iago.treinamento_android.dagger.module.ApplicationModule;
import app.iago.treinamento_android.dagger.module.PreferenceModule;
import app.iago.treinamento_android.dagger.module.infraestructure.ManagerModule;
import app.iago.treinamento_android.dagger.module.infraestructure.NetworkModule;
import app.iago.treinamento_android.dagger.module.infraestructure.ServiceModule;
import app.iago.treinamento_android.dagger.module.presentation.HelperModule;
import dagger.Component;

/**
 * Created by IagoA on 12/01/2017.
 */

@Singleton
@Component(modules = {
        ApplicationModule.class,
        HelperModule.class,
        PreferenceModule.class,
        NetworkModule.class,
        ServiceModule.class,
        ManagerModule.class
})
public interface DiComponent {
    UiComponent uiComponent();
}