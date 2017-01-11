package app.iago.treinamento_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import app.iago.treinamento_android.entity.AccessToken;
import app.iago.treinamento_android.entity.GitHubOAuthApi;
import app.iago.treinamento_android.entity.GitHubStatusApi;
import app.iago.treinamento_android.entity.GitHubUserApi;
import app.iago.treinamento_android.entity.Status;
import app.iago.treinamento_android.entity.User;
import app.iago.treinamento_android.util.AppUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static app.iago.treinamento_android.entity.GitHubStatusApi.RETROFIT;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.status_text)
    TextView status;

    @BindView(R.id.tietUsername)
    TextInputEditText username;

    @BindView(R.id.tilUsername)
    TextInputLayout usernameLayout;

    @BindView(R.id.tietPassword)
    TextInputEditText password;

    @BindView(R.id.tilPassword)
    TextInputLayout passwordLayout;

//    @BindView(R.id.login_button_basic)
//    Button basicLoginButton;

    @BindView(R.id.login_button_oAuth)
    Button oAuthlLoginButton;

    GitHubStatusApi mGitHubStatusApi;
    GitHubUserApi mGitHubUserApi;
    GitHubOAuthApi mGitHubOAuthApi;
    private SharedPreferences mSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        GitHubStatusApi statusApiImpl = RETROFIT.create(GitHubStatusApi.class);
        mGitHubStatusApi = statusApiImpl;

        final GitHubUserApi userApiImpl = GitHubUserApi.RETROFIT.create(GitHubUserApi.class);
        mGitHubUserApi = userApiImpl;

        final GitHubOAuthApi oAuthApiImpl = GitHubOAuthApi.RETROFIT.create(GitHubOAuthApi.class);
        mGitHubOAuthApi = oAuthApiImpl;

            statusApiImpl.lastMessage().enqueue(new Callback<Status>() {
                @Override
                public void onResponse(Call<Status> call, Response<Status> response) {
                    if (response.isSuccessful()) {
                        status.setText(response.body().status.toString());
                        changeBackgroundColor(response.body().status.getColorRes());
                    }
                    else {
                        try {
                            String error = response.errorBody().string();
                            Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
                            changeBackgroundColor(response.body().status.MAJOR.getColorRes());
                        } catch (IOException e) {
                            Log.e(TAG, e.getMessage(), e);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Status> call, Throwable t) {
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    changeBackgroundColor(Status.Type.MAJOR.getColorRes());
                }
            });

        oAuthlLoginButton.setOnClickListener(view -> {
            final String baseUrl = GitHubOAuthApi.BASE_URL + "authorize";
            final String clientId = getString(R.string.oauth_client_id);
            final String redirectUri = getOAuthRedirectUri();
            final Uri uri = Uri.parse(baseUrl + "?client_id=" + clientId
                    + "&redirect_uri=" + redirectUri);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });


    }

    @OnClick(R.id.login_button_basic)
    protected void onBasicAuthClick(View view) {
        if (AppUtils.validateRequiredFields(MainActivity.this, usernameLayout, passwordLayout)) {
            String username1 = usernameLayout.getEditText().getText().toString();
            String password1 = passwordLayout.getEditText().getText().toString();
            final String credential = Credentials.basic(username1, password1);
            mGitHubUserApi.basicAuth(credential).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        String login = response.body().login;
                        mSharedPrefs.edit()
                                .putString(getString(R.string.sp_credential_key), credential)
                                .apply();
                        Snackbar.make(view, login, BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                    else {
                        try {
                            String error = response.errorBody().string();
                            Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            Log.e(TAG, e.getMessage(),  e);
                        }
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Snackbar.make(view, t.getMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                }
            });
        }

        mSharedPrefs =  getSharedPreferences(getString(R.string.sp_file), MODE_PRIVATE);
    }

    private void changeBackgroundColor(int colorRes) {
        status.setBackgroundColor(ContextCompat.getColor(MainActivity.this, colorRes));
    }

    private String getOAuthRedirectUri() {
        return getString(R.string.oauth_schema) + "://" + getString(R.string.oauth_host);
    }

    private void processOAuthRedirectUri() {
        // Os intent-filter's permitem a interação com o ACTION_VIEW
        final Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(this.getOAuthRedirectUri())) {
            String code = uri.getQueryParameter("code");
            if (code != null) {
                String clientId = getString(R.string.oauth_client_id);
                String clientSecret = getString(R.string.oauth_client_secret);
                mGitHubOAuthApi.accessToken(clientId, clientSecret, code).enqueue(new Callback<AccessToken>() {
                    @Override
                    public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                        if (response.isSuccessful()) {
                            AccessToken accessToken = response.body();
                            mSharedPrefs.edit()
                                    .putString(getString(R.string.sp_credential_key), accessToken.getAuthCredential())
                                    .apply();
                            Snackbar.make(oAuthlLoginButton, accessToken.access_token, Snackbar.LENGTH_LONG).show();
                        } else {
                            try {
                                String error = response.errorBody().string();
                                Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
                            } catch (IOException e) {
                                Log.e(TAG, e.getMessage(), e);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AccessToken> call, Throwable t) {
//                        Snackbar.make(view, t.getMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                });
            } else if (uri.getQueryParameter("error") != null) {
                //TODO Tratar erro
            }
            // Limpar os dados para evitar chamadas múltiplas
            getIntent().setData(null);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Call<Status> call =  mGitHubStatusApi.lastMessage();
        call.enqueue(new Callback<Status>(){
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if (response.isSuccessful()) {
                    status.setText(response.body().status.toString());
                    changeBackgroundColor(response.body().status.getColorRes());
                }
                else {
                    try {
                        String error = response.errorBody().string();
                        Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
                        changeBackgroundColor(response.body().status.MAJOR.getColorRes());
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage(), e);
                    }
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                changeBackgroundColor(Status.Type.MAJOR.getColorRes());
            }
        });

        processOAuthRedirectUri();
    }
}
