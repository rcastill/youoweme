package com.mokadevel.renew;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.internal.util.Predicate;
import com.mokadevel.renew.fragments.GroupsFragment;
import com.mokadevel.renew.models.Group;
import com.mokadevel.renew.models.User;
import com.mokadevel.renew.requests.GroupRequests;
import com.mokadevel.renew.services.FacebookService;

public class CreateGroupActivity extends AppCompatActivity
        implements Button.OnClickListener, Predicate<Group>
{
    public static final String EXTRA_GROUP_ID = "groupId";

    private ProgressDialog progressDialog;
    private TextView textViewName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("Create a group");

        textViewName = (TextView) findViewById(R.id.name);

        Button buttonSend = (Button) findViewById(R.id.send);
        buttonSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        progressDialog = ProgressDialog.show(this, "Creating group", "Please wait...");

        GroupRequests.create(textViewName.getText().toString(), User.testUser(), this);
    }

    @Override
    public boolean apply(Group group)
    {
        if (group != null) {
            Intent data = new Intent();
            data.putExtra(EXTRA_GROUP_ID, group.getId());

            setResult(GroupsFragment.CREATE_GROUP_SUCCESS, data);
        } else {
            setResult(GroupsFragment.CREATE_GROUP_ERROR);
        }

        progressDialog.dismiss();

        finish();
        return false;
    }
}
