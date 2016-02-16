package com.mokadevel.renew;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.android.internal.util.Predicate;
import com.mokadevel.renew.models.Group;
import com.mokadevel.renew.models.User;
import com.mokadevel.renew.requests.GroupRequests;

public class GroupActivity extends AppCompatActivity implements Predicate<Group>
{
    public static final String EXTRA_GROUP_ID = "groupId";

    private ProgressDialog progressDialog;
    private Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new MembersAdapter());

        loadGroup();
    }

    private void loadGroup()
    {
        long groupId = getIntent().getLongExtra(EXTRA_GROUP_ID, 0);

        if (groupId == 0) {
            // TODO: handle this error properly.
            return;
        }

        // display a message that this activity will load something.
        progressDialog = ProgressDialog.show(this, "Loading", "Please wait...");

        // send the request.
        GroupRequests.get(groupId, this);
    }

    /**
     * SetUps the view to show the current group information.
     * Made for initialization and update.
     */
    private void setUp()
    {
        getSupportActionBar().setTitle(group.getName());
    }

    @Override
    public boolean apply(Group group)
    {
        this.group = group;

        setUp();

        progressDialog.dismiss();

        return false;
    }

    private class MembersAdapter extends ArrayAdapter<User>
    {
        public MembersAdapter()
        {
            super(getApplication(), 0);
        }

        @Override
        public int getCount()
        {
            return group == null ? 0 : group.getSize();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            User member = group.getMember(position);

            if (convertView == null) {
                convertView = getLayoutInflater()
                        .inflate(R.layout.group_member_item, parent, false);
            }

            TextView textViewName = (TextView) convertView.findViewById(R.id.name);
            textViewName.setText(member.getName());

            return convertView;
        }
    }
}
