package com.mokadevel.renew;

import com.mokadevel.renew.models.Group;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GroupsUnitTest
{
    static Group group;

    @BeforeClass
    public static void before() throws JSONException
    {
        String json = "{\"id\": 1," +
                "\"members\": [{\"full_name\": \"Test User\", \"id\": 1}]," +
                "\"name\": \"Test Group\"}";

        group = new Group().fromJson(new JSONObject(json));
    }

    @Test
    public void fromJson_name() throws Exception
    {
        assertEquals("Test Group", group.getName());
    }

    @Test
    public void fromJson_id() throws Exception
    {
        assertEquals(1, group.getId());
    }

    @Test
    public void fromJson_membersCount() throws Exception
    {
        assertEquals(1, group.getMembers().size());
    }
}
