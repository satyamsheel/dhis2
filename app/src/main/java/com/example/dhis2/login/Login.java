package com.example.dhis2.login;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dhis2.ActivityStarter;
import com.example.dhis2.MainActivity;
import com.example.dhis2.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import io.reactivex.disposables.Disposable;

public class Login extends AppCompatActivity {
    private TextInputEditText serverUrlEditText;
    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;
    private MaterialButton loginButton;
    private ProgressBar loadingProgressBar;
    private model model;
    private Disposable disposable;

    public static Intent getLoginActivityIntent(Context context) {
        return new Intent(context,Login.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        model = ViewModelProviders.of(this, new factory()).get(model.class);

        serverUrlEditText = findViewById(R.id.urlText);
        usernameEditText = findViewById(R.id.usernameText);
        passwordEditText = findViewById(R.id.passwordText);
        loginButton = findViewById(R.id.loginButton);
        loadingProgressBar = findViewById(R.id.loginProgressBar);

        //for errors related to wrong entry of data

        model.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            loginButton.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getServerUrlError() != null) {
                serverUrlEditText.setError(getString(loginFormState.getServerUrlError()));
            }
            if (loginFormState.getUsernameError() != null) {
                usernameEditText.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }
        });

        //Returns sucess or failure
        model.getLoginResult().observe(this, result -> {
            if (result == null) {
                return;
            }
            loadingProgressBar.setVisibility(View.GONE);
            if (result.getError() != null) {
                showLoginFailed(result.getError());
            }
            if (result.getSuccess() != null) {
                ActivityStarter.startActivity(this, MainActivity.getMainActivityIntent(this),true);
            }
            setResult(Activity.RESULT_OK);
        });
        //Picking up text from text field
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                model.loginDataChanged(
                        serverUrlEditText.getText().toString(),
                        usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };

        serverUrlEditText.addTextChangedListener(afterTextChangedListener);
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                login();
            }
            return false;
        });

        loginButton.setOnClickListener(v -> login());

    }
    private void login() {
        loadingProgressBar.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.INVISIBLE);
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String serverUrl = serverUrlEditText.getText().toString();

        disposable = model
                .login(username, password, serverUrl)
                .doOnTerminate(() -> loginButton.setVisibility(View.VISIBLE))
                .subscribe(u -> {}, t -> {});
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

}
