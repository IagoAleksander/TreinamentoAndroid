package app.iago.treinamento_android.presentation.helper;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.EditText;

import javax.inject.Inject;

import app.iago.treinamento_android.R;

/**
 * Created by IagoA on 11/01/2017.
 */

public final class AppHelper {

    private final Context mContext;

    @Inject
    public AppHelper(Context context) {
        mContext = context;
    }

    public boolean validateRequiredFields(TextInputLayout... fields) {
        for (TextInputLayout field : fields) {
            EditText editText = field.getEditText();
            if (editText != null) {
                if (TextUtils.isEmpty(editText.getText())) {
                    field.setErrorEnabled(true);
                    field.setError(mContext.getString(R.string.txt_required));
                    return false;
                }
                else {
                    field.setErrorEnabled(false);
                    field.setError(null);
                }
            }
            else {
                throw new RuntimeException("O TextInputLayout deve possuir um EditText.");
            }
        }
        return true;
    }
}
