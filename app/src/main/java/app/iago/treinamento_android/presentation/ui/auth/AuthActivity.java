package app.iago.treinamento_android.presentation.ui.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import app.iago.treinamento_android.R;
import app.iago.treinamento_android.domain.entity.Status;
import app.iago.treinamento_android.domain.entity.User;
import app.iago.treinamento_android.infraestructure.storage.service.GitHubOAuthService;
import app.iago.treinamento_android.presentation.base.BaseActivity;
import app.iago.treinamento_android.presentation.helper.AppHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Credentials;
import rx.android.schedulers.AndroidSchedulers;

import static com.jakewharton.rxbinding.widget.RxTextView.textChanges;

//import static app.iago.treinamento_android.entity.GitHubStatusRepository.RETROFIT;

public class AuthActivity extends BaseActivity implements AuthContract.View{

    private static final String TAG = AuthActivity.class.getSimpleName();

    @BindView(R.id.status_text)
    TextView statusView;

    @BindView(R.id.tilUsername)
    TextInputLayout usernameLayout;

    @BindView(R.id.tilPassword)
    TextInputLayout passwordLayout;

//    @BindView(R.id.login_button_basic)
//    Button basicLoginButton;

    @BindView(R.id.login_button_oAuth)
    Button oAuthlLoginButton;

    @Inject
    AuthContract.Presenter mPresenter;

    @Inject
//    @Named("secret")
    SharedPreferences mSharedPrefs;

    @Inject
    AppHelper mAppHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        super.getDaggerUiComponent().inject(this);
        mPresenter.setView(this);

        oAuthlLoginButton.setOnClickListener(view -> {
            final String baseUrl = GitHubOAuthService.BASE_URL + "authorize";
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
        if (mAppHelper.validateRequiredFields(usernameLayout, passwordLayout)) {
            String username1 = usernameLayout.getEditText().getText().toString();
            String password1 = passwordLayout.getEditText().getText().toString();
            final String credential = Credentials.basic(username1, password1);
            mPresenter.callGetUser(credential);
        }

        mSharedPrefs =  getSharedPreferences(getString(R.string.sp_file), MODE_PRIVATE);
        textChanges(usernameLayout.getEditText())
                .debounce(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .skip(1)
                .subscribe(text -> {
                    mAppHelper.validateRequiredFields(usernameLayout);
                });
//        Subscription subscribe = RxTextView.textChanges(passwordLayout.getEditText())
        RxTextView.textChanges(passwordLayout.getEditText())
                .debounce(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .skip(1)
                .subscribe(text -> {
                    mAppHelper.validateRequiredFields(passwordLayout);
                });
//        subscribe.unsubscribe();
    }

    private void changeBackgroundColor(int colorRes) {
        statusView.setBackgroundColor(ContextCompat.getColor(AuthActivity.this, colorRes));
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
                mPresenter.callAccessToken(clientId, clientSecret, code);
            }
            else if (uri.getQueryParameter("error") != null) {
                showError(uri.getQueryParameter("error"));
            }
            // Limpar os dados para evitar chamadas múltiplas
            getIntent().setData(null);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadStatus();
        processOAuthRedirectUri();
    }

    @Override
    public void onLoadStatusType(Status.Type statusType) {
        statusView.setText("GOOD");
        changeBackgroundColor(Status.Type.GOOD.getColorRes());
    }

    @Override
    public void onAuthSuccess(String credential, User user) {
        mSharedPrefs.edit()
                .putString(getString(R.string.sp_credential_key), credential)
                .apply();
        Snackbar.make(oAuthlLoginButton, credential, BaseTransientBottomBar.LENGTH_LONG).show();
    }

    @Override
    public void showError(String message) {

    }
}
