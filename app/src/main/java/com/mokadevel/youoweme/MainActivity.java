package com.mokadevel.youoweme;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.mokadevel.youoweme.requests.Requests;
import com.mokadevel.youoweme.services.FacebookService;
import com.mokadevel.youoweme.util.ResultIds;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize services.
        FacebookService.initialize(getApplicationContext());
        Requests.initialize(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("MainActivity");

        // TODO: debug only.
        toApplicationActivity();
    }

    public void toFacebookLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, ResultIds.FACEBOOK_LOGIN);
    }

    private void toApplicationActivity()
    {
        Intent intent = new Intent(this, ApplicationActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ResultIds.FACEBOOK_LOGIN) {
            TextView resultText = (TextView) findViewById(R.id.loginResult);

            if (resultCode == Activity.RESULT_CANCELED) {
                // TODO: show an alert.
                resultText.setText("Not logged in");

            } else if (resultCode == Activity.RESULT_OK) {
                // resultText.setText(FacebookService.getInstance().getUser().getName());
                toApplicationActivity();
            }
        }
    }
}
