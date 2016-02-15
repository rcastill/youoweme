package com.mokadevel.renew;

import com.mokadevel.renew.models.User;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UsersUnitTest
{
    static User user;

    @BeforeClass
    public static void before() throws JSONException
    {
        String json = "{\"id\": \"1\", \"name\": \"Test User\", \"access_token\": \"Test Token\"}";

        user = new User().fromJson(new JSONObject(json));
    }

    @Test
    public void fromJson_name() throws Exception
    {
        assertEquals("Test User", user.getName());
    }

    @Test
    public void fromJson_id() throws Exception
    {
        assertEquals("1", user.getId());
    }

    @Test
    public void fromJson_accessToken() throws Exception
    {
        assertEquals("Test Token", user.getAccessToken());
    }
}
