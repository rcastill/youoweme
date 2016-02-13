package com.mokadevel.youoweme.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements JsonLoadable<User>
{
    private static int TEST_IDS = 0;

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

    public User asTest()
    {
        id = String.valueOf(TEST_IDS++);
        name = "Test User " + id;

        return null;
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
