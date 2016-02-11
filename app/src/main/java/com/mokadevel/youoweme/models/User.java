package com.mokadevel.youoweme.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements JsonLoadable<User>
{
    private String name;
    private String id;
    private String accessToken;

    @Override
    public User fromJson(JSONObject object) throws JSONException
    {
        name = object.getString("name");
        id = object.getString("id");

        if (object.has("access_token")) {
            accessToken = object.getString("access_token");
        }

        return this;
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getAccessToken()
    {
        return accessToken;
    }
}
