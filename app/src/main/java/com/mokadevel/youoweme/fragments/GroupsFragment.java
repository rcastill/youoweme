package com.mokadevel.youoweme.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.internal.util.Predicate;
import com.mokadevel.youoweme.R;
import com.mokadevel.youoweme.models.Group;
import com.mokadevel.youoweme.models.User;
import com.mokadevel.youoweme.requests.GroupRequests;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class GroupsFragment extends Fragment
{
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

        return rootView;
    }

    private class GroupsRequestGetAllPredicate implements Predicate<ArrayList<Group>>
    {
        @Override
        public boolean apply(ArrayList<Group> groups)
        {
            GroupsFragment.this.groups.addAll(groups);
            return false;
        }
    }

    private class ItemClickListener implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            Snackbar.make(parent, groups.get(position).getName(), Snackbar.LENGTH_LONG)
                    .show();
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
}
