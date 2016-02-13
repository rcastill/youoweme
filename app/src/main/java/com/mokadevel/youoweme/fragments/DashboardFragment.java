package com.mokadevel.youoweme.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.mokadevel.youoweme.R;
import com.mokadevel.youoweme.models.Group;

import java.util.List;


public class DashboardFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(new DashboardAdapter(getContext(),
                new Group[] {
                        new Group().asTest(),
                        new Group().asTest(),
                        new Group().asTest(),
                        new Group().asTest(),
                        new Group().asTest(),
                        new Group().asTest(),
                        new Group().asTest(),
                        new Group().asTest(),
                        new Group().asTest(),
                        new Group().asTest(),
                }));

        return rootView;
    }

    private class DashboardAdapter extends ArrayAdapter<Group>
    {
        public DashboardAdapter(Context context, Group[] objects)
        {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            Group group = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.list_dashboard_item, parent, false);
            }

            TextView textViewName = (TextView) convertView.findViewById(R.id.name);

            textViewName.setText(group.getName());

            return convertView;
        }
    }
}
