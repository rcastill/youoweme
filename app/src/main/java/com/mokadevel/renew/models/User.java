package com.mokadevel.renew.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements JsonLoadable<User>
{
    private String fullName;
    private long id;

    @Override
    public User fromJson(JSONObject object) throws JSONException
    {
        fullName = object.getString("full_name");
        id = object.getLong("id");

        return this;
    }

    public long getId()
    {
        return id;
    }

    public String getFullName()
    {
        return fullName;
    }
}
