package app.iago.treinamento_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import app.iago.treinamento_android.entity.AccessToken;
import app.iago.treinamento_android.entity.GitHubStatusApi;
import app.iago.treinamento_android.entity.GitHubUserApi;
import app.iago.treinamento_android.entity.Status;
import app.iago.treinamento_android.entity.User;
import app.iago.treinamento_android.util.AppUtils;
import app.iago.treinamento_android.util.MySubscriber;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Credentials;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.jakewharton.rxbinding.widget.RxTextView.textChanges;

//import static app.iago.treinamento_android.entity.GitHubStatusApi.RETROFIT;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.status_text)
    TextView statusView;

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

    @Inject
    GitHubStatusApi mGitHubStatusApi;
    @Inject
    GitHubUserApi mGitHubUserApi;
    @Inject
    AccessToken.GitHubOAuthApi mGitHubOAuthApi;

    @Inject
//    @Named("secret")
    SharedPreferences mSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        super.getDaggerDiComponent().inject(this);

//        GitHubStatusApi statusApiImpl = RETROFIT.create(GitHubStatusApi.class);
//        mGitHubStatusApi = statusApiImpl;

        final GitHubUserApi userApiImpl = GitHubUserApi.RETROFIT.create(GitHubUserApi.class);
        mGitHubUserApi = userApiImpl;

        final AccessToken.GitHubOAuthApi oAuthApiImpl = AccessToken.GitHubOAuthApi.RETROFIT.create(AccessToken.GitHubOAuthApi.class);
        mGitHubOAuthApi = oAuthApiImpl;

//        statusApiImpl.lastMessage()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new MySubscriber<Status>() {
//                    public void onError(String message) {
//                        statusView.setText(Status.Type.MAJOR.getStatus());
//                        changeBackgroundColor(Status.Type.MAJOR.getColorRes());
//                    }
//
//                    @Override
//                    public void onNext(Status status) {
//                        statusView.setText(status.status.getStatus());
//                        changeBackgroundColor(Status.Type.GOOD.getColorRes());
//                    }
//                });


        oAuthlLoginButton.setOnClickListener(view -> {
            final String baseUrl = AccessToken.GitHubOAuthApi.BASE_URL + "authorize";
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

            mGitHubUserApi.basicAuth(credential)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new MySubscriber<User>() {
                        @Override
                        public void onError(String message) {

                        }

                        @Override
                        public void onNext(User user) {
                            String login = user.login;
                            mSharedPrefs.edit()
                                    .putString(getString(R.string.sp_credential_key), credential)
                                    .apply();
                            Snackbar.make(view, login, BaseTransientBottomBar.LENGTH_LONG).show();
                        }
                    });
        }

        mSharedPrefs =  getSharedPreferences(getString(R.string.sp_file), MODE_PRIVATE);
        textChanges(usernameLayout.getEditText())
                .debounce(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .skip(1)
                .subscribe(text -> {
                    AppUtils.validateRequiredFields(this, usernameLayout);
                });
//        Subscription subscribe = RxTextView.textChanges(passwordLayout.getEditText())
        RxTextView.textChanges(passwordLayout.getEditText())
                .debounce(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .skip(1)
                .subscribe(text -> {
                    AppUtils.validateRequiredFields(this, passwordLayout);
                });
//        subscribe.unsubscribe();
    }

    private void changeBackgroundColor(int colorRes) {
        statusView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, colorRes));
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
                mGitHubOAuthApi.accessToken(clientId, clientSecret, code)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MySubscriber<AccessToken>() {
                            @Override
                            public void onError(String message) {

                            }

                            @Override
                            public void onNext(AccessToken accessToken) {
                                mSharedPrefs.edit()
                                        .putString(getString(R.string.sp_credential_key), accessToken.getAuthCredential())
                                        .apply();
                                Snackbar.make(oAuthlLoginButton, accessToken.access_token, Snackbar.LENGTH_LONG).show();
                            }
                        });

            }
            // Limpar os dados para evitar chamadas múltiplas
            getIntent().setData(null);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGitHubStatusApi.lastMessage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<Status>() {
                    @Override
                    public void onError(String message) {
                        changeBackgroundColor(Status.Type.MAJOR.getColorRes());
                    }

                    @Override
                    public void onNext(Status status) {
                        statusView.setText("GOOD");
                        changeBackgroundColor(Status.Type.GOOD.getColorRes());
                    }
                });

        processOAuthRedirectUri();
    }
}
