package com.mokadevel.renew;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import com.android.internal.util.Predicate;
import com.mokadevel.renew.models.Group;
import com.mokadevel.renew.models.User;
import com.mokadevel.renew.requests.GroupRequests;
import com.mokadevel.renew.requests.Requests;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class GroupRequestsTest extends ApplicationTestCase<Application>
{
    private User user;

    public GroupRequestsTest()
    {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception
    {
        Requests.initialize(getContext());

        user = new User().fromJson(new JSONObject("{\"id\": 1, \"name\": \"Test User\"}"));
    }

    @LargeTest
    public void testGetAll() throws InterruptedException
    {
        final CountDownLatch signal = new CountDownLatch(1);
        GroupRequests.getAll(user, new Predicate<ArrayList<Group>>()
        {
            @Override
            public boolean apply(ArrayList<Group> groups)
            {
                assertTrue(groups.size() > 0);

                signal.countDown();
                return false;
            }
        });
        signal.await();
    }
}
