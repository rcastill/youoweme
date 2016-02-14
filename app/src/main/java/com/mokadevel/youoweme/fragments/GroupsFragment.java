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
import com.mokadevel.youoweme.R;
import com.mokadevel.youoweme.models.Group;

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

        // fill with test groups.
        for (int i = 0; i < 10; i++) {
            groups.add(new Group().asTest());
        }

        // set adapters and listeners.
        listView.setAdapter(new DashboardAdapter());
        listView.setOnItemClickListener(new ItemClickListener());

        return rootView;
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
            textViewMemberCount.setText(String.format(text, group.getMembers().size()));

            return convertView;
        }
    }
}
