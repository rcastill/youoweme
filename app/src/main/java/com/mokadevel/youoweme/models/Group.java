package com.mokadevel.youoweme.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Group implements JsonLoadable<Group>
{
    private static int TEST_IDS = 0;

    private ArrayList<User> members = new ArrayList<>();
    private String name;
    private long id;

    public Group()
    {

    }

    private Group(long id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Group asTest()
    {
        id = TEST_IDS++;
        name = "Test group " + id;

        return this;
    }

    @Override
    public Group fromJson(JSONObject object) throws JSONException
    {
        id = object.getLong("id");
        name = object.getString("name");

        // add all members.
        JSONArray members = object.getJSONArray("members");
        for (int i = 0; i < members.length(); i++) {
            this.members.add(new User().fromJson(members.getJSONObject(i)));
        }

        return this;
    }

    public long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public ArrayList<User> getMembers()
    {
        return members;
    }
}
