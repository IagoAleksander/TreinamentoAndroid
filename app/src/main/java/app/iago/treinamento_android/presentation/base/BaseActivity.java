package app.iago.treinamento_android.presentation.base;

import android.support.v7.app.AppCompatActivity;

import app.iago.treinamento_android.MyApplication;
import app.iago.treinamento_android.dagger.UiComponent;

/**
 * Created by IagoA on 12/01/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected MyApplication getMyApplication() {
        return (MyApplication) getApplication();
    }

    protected UiComponent getDaggerUiComponent() {
        return this.getMyApplication().getDaggerUiComponent();
    }

}
