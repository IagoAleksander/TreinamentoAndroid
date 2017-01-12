package app.iago.treinamento_android;

import android.support.v7.app.AppCompatActivity;

import app.iago.treinamento_android.dagger.DiComponent;

/**
 * Created by IagoA on 12/01/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected MyApplication getMyApplication() {
        return (MyApplication) getApplication();
    }

    protected DiComponent getDaggerDiComponent() {
        return this.getMyApplication().getDaggerDiComponent();
    }

}
