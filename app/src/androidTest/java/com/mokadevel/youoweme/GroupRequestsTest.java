package com.mokadevel.youoweme;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.SmallTest;
import com.android.internal.util.Predicate;
import com.mokadevel.youoweme.models.Group;
import com.mokadevel.youoweme.models.User;
import com.mokadevel.youoweme.requests.GroupRequests;
import com.mokadevel.youoweme.requests.Requests;
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
                assertEquals(1, groups.size());

                signal.countDown();
                return false;
            }
        });
        signal.await();
    }
}
