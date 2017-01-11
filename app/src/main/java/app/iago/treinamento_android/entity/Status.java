package app.iago.treinamento_android.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import app.iago.treinamento_android.R;

/**
 * Created by IagoA on 09/01/2017.
 */

public class Status {
    public Type status;
    public String body;
    public Date created_on;

    public enum Type {
        @SerializedName("good")
        GOOD(R.color.green),
        @SerializedName("minor")
        MINOR(R.color.orange),
        @SerializedName("major")
        MAJOR(R.color.red);

        private int colorRes;

        Type(int colorRes) {
            this.colorRes = colorRes;
        }

        public int getColorRes() {
            return colorRes;
        }
    }
}
