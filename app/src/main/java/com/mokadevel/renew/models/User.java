package com.mokadevel.renew.models;

import com.mokadevel.renew.modelbase.JsonLoadable;
import org.json.JSONException;
import org.json.JSONObject;

public class User implements JsonLoadable<User>
{
    private static int TEST_IDS = 0;

    private long id;
    private String name;
    private String accessToken;

    @Override
    public User fromJson(JSONObject object) throws JSONException
    {
        name = object.getString("name");
        id = object.getLong("id");

        if (object.has("access_token")) {
            accessToken = object.getString("access_token");
        }

        return this;
    }

    public User asTest()
    {
        id = TEST_IDS++;
        name = "Test User " + id;

        return null;
    }

    public long getId()
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

    public static User testUser()
    {
        User user = new User();
        user.id = 1;
        user.name = "Test User";

        return user;
    }
}
