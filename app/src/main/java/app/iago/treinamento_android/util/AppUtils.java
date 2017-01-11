package app.iago.treinamento_android.util;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.text.TextUtilsCompat;
import android.text.TextUtils;
import android.widget.EditText;

import app.iago.treinamento_android.R;

/**
 * Created by IagoA on 11/01/2017.
 */

public final class AppUtils {
    private AppUtils() {}

    public static boolean validateRequiredFields(Context context, TextInputLayout... fields) {
        for (TextInputLayout field : fields) {
            EditText editText = field.getEditText();
            if (editText != null) {
                if (TextUtils.isEmpty(editText.getText())) {
                    field.setErrorEnabled(true);
                    field.setError(context.getString(R.string.txt_required));
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
