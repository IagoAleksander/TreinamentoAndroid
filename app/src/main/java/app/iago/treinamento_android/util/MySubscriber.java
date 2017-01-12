package app.iago.treinamento_android.util;

import android.util.Log;

import rx.Subscriber;

/**
 * Created by IagoA on 12/01/2017.
 */

public abstract class MySubscriber<T> extends Subscriber<T> {

    private static final String TAG = MySubscriber.class.getSimpleName();

    @Override
    public void onCompleted() {}

    @Override
    public void onError(Throwable e) {
        Log.d(TAG, e.getMessage(), e.fillInStackTrace());
        onError(e.getMessage());
    }

    public abstract void onError(String message);

}
