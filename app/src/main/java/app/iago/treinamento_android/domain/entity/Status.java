package app.iago.treinamento_android.domain.entity;

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
        GOOD(R.color.green, R.string.status_good),
        @SerializedName("minor")
        MINOR(R.color.orange, R.string.status_minor),
        @SerializedName("major")
        MAJOR(R.color.red, R.string.status_major);

        private int colorRes;
        private int status;

        Type(int colorRes, int status) {
            this.colorRes = colorRes;
            this.status = status;
        }

        public int getColorRes() {
            return colorRes;
        }

        public int getStatus() {
            return status;
        }

    }
}
