package app.iago.treinamento_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import app.iago.treinamento_android.entity.GitHubStatusApi;
import app.iago.treinamento_android.entity.Status;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(GitHubStatusApi.BASE_URL)
                .build();

        GitHubStatusApi statusApiImpl = retrofit.create(GitHubStatusApi.class);

            statusApiImpl.lastMessage().enqueue(new Callback<Status>() {
                @Override
                public void onResponse(Call<Status> call, Response<Status> response) {
                    if (response.isSuccessful()) {
                        Status status = response.body();
                        Toast.makeText(MainActivity.this, status.status, Toast.LENGTH_LONG).show();
                    }
                    else {
                        //TODO: tratar erro
                    }
                }

                @Override
                public void onFailure(Call<Status> call, Throwable t) {

                }
            });
    }
}
