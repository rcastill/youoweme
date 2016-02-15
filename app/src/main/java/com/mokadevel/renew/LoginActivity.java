package com.mokadevel.renew;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.login.widget.LoginButton;
import com.mokadevel.renew.models.User;
import com.mokadevel.renew.services.FacebookService;
import com.mokadevel.renew.util.ConnectionListener;

public class LoginActivity extends AppCompatActivity implements ConnectionListener
{
    private FacebookService facebookService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        facebookService = FacebookService.getInstance();

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile");

        /* Register FacebookService callbacks */
        loginButton.registerCallback(facebookService.getCallbackManager(), facebookService);
        facebookService.registerConnectionListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookService.getCallbackManager().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void eventPerformed(User user) {
        setResult((user != null) ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        finish();
    }
}
