package com.mokadevel.renew.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.internal.util.Predicate;
import com.mokadevel.renew.CreateGroupActivity;
import com.mokadevel.renew.GroupActivity;
import com.mokadevel.renew.R;
import com.mokadevel.renew.models.Group;
import com.mokadevel.renew.models.User;
import com.mokadevel.renew.requests.GroupRequests;

import java.util.ArrayList;


public class GroupsFragment extends Fragment
{
    public static final int CREATE_GROUP = 1;
    public static final int CREATE_GROUP_SUCCESS = 2;
    public static final int CREATE_GROUP_ERROR = 3;

    private ArrayList<Group> groups = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_groups, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        // set adapters and listeners.
        listView.setAdapter(new DashboardAdapter());
        listView.setOnItemClickListener(new ItemClickListener());

        // send requests.
        GroupRequests.getAll(User.testUser(), new GroupsRequestGetAllPredicate());

        // setup the fab button.
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new CreateGroupOnClickListener());

        return rootView;
    }

    private void toGroupDetail(long groupId)
    {
        Intent intent = new Intent(getActivity(), GroupActivity.class);
        intent.putExtra(GroupActivity.EXTRA_GROUP_ID, groupId);
        startActivity(intent);
    }

    // - OnClickListener.
    private class CreateGroupOnClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(getActivity(), CreateGroupActivity.class);
            startActivityForResult(intent, CREATE_GROUP);
        }
    }

    // - GroupRequests.
    private class GroupsRequestGetAllPredicate implements Predicate<ArrayList<Group>>
    {
        @Override
        public boolean apply(ArrayList<Group> groups)
        {
            GroupsFragment.this.groups.addAll(groups);
            return false;
        }
    }

    // - ListView.
    private class ItemClickListener implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            toGroupDetail(groups.get(position).getId());
        }
    }

    private class DashboardAdapter extends ArrayAdapter<Group>
    {
        public DashboardAdapter()
        {
            super(GroupsFragment.this.getContext(), 0, groups);
        }

        /**
         * Prepare each item in the group list.
         *
         * @param position    the index of the group.
         * @param convertView the view to reuse or create.
         * @param parent      the parent view.
         * @return the converted view.
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            Group group = getItem(position);

            // check if this is a view that we have to create.
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.groups_item, parent, false);
            }

            // set the group name.
            TextView textViewName = (TextView) convertView.findViewById(R.id.name);
            textViewName.setText(group.getName());

            // set the group count, formatting it's current text.
            TextView textViewMemberCount = (TextView) convertView.findViewById(R.id.memberCount);
            String text = getResources().getString(R.string.group_member_count);
            textViewMemberCount.setText(String.format(text, group.getSize()));

            return convertView;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CREATE_GROUP && resultCode == CREATE_GROUP_SUCCESS) {
            toGroupDetail(data.getLongExtra(CreateGroupActivity.EXTRA_GROUP_ID, 0));
        }
    }
}
