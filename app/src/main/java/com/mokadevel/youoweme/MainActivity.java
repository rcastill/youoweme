package com.mokadevel.youoweme;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Hello 2");
    }

    public void toFbLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, ResultIds.FACEBOOK_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ResultIds.FACEBOOK_LOGIN) {
            TextView resultText = (TextView) findViewById(R.id.loginResult);

            if (resultCode == Activity.RESULT_CANCELED) {
                resultText.setText("Not logged in");

            } else if (resultCode == Activity.RESULT_OK) {

            }
        }
    }
}
