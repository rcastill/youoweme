package com.mokadevel.renew.models;

import com.mokadevel.renew.modelbase.JsonLoadable;
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
    private int size;

    public Group()
    {

    }

    public Group asTest()
    {
        id = TEST_IDS++;
        name = "Test group " + id;

        members.add(new User().asTest());

        return this;
    }

    @Override
    public Group fromJson(JSONObject object) throws JSONException
    {
        id = object.getLong("id");
        name = object.getString("name");
        size = object.getInt("size");

        // add all members if the object has them.
        if (object.has("members")) {
            JSONArray members = object.getJSONArray("members");
            for (int i = 0; i < members.length(); i++) {
                this.members.add(new User().fromJson(members.getJSONObject(i)));
            }

            // override size just in case.
            size = this.members.size();
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

    public User getMember(int index)
    {
        return members.get(index);
    }

    public int getSize()
    {
        return size;
    }
}
