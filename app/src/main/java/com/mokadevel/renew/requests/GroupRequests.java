package com.mokadevel.renew.requests;

import android.util.Log;
import com.android.internal.util.Predicate;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mokadevel.renew.modelbase.ModelBase;
import com.mokadevel.renew.models.Group;
import com.mokadevel.renew.models.User;
import org.json.JSONObject;

import java.util.ArrayList;

public class GroupRequests
{
    private static final String TAG = "DEVLOG:GROUP_REQUESTS";

    private static final String URL_SAVE = "/groups/save/";
    private static final String URL_ADD = "/groups/add/%d/%d/";
    private static final String URL_DELETE = "/groups/delete/%d/";
    private static final String URL_GET = "/groups/get/%d/";
    private static final String URL_BELONGS = "/groups/get_all/%s/";

    public static void get(Group group, final Predicate<Group> onResult)
    {
        Requests.getInstance().add(new JsonObjectRequest(
                Request.Method.GET,
                Requests.makeUrl(URL_GET, group.getId()),
                null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        // received a response, check that the response was actually successful.
                        if (Requests.isSuccessful(getClass(), response)) {
                            onResult.apply(ModelBase.loadModel(Group.class, response, "group"));
                        } else {
                            onResult.apply(null);
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.e(TAG, "get(): error in response.");
                        onResult.apply(null);
                    }
                }
        ));
    }

    public static void getAll(User user, final Predicate<ArrayList<Group>> onResult)
    {
        Requests.getInstance().add(new JsonObjectRequest(
                Request.Method.GET,
                Requests.makeUrl(URL_BELONGS, user.getId()),
                null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        // received a response, check that the response was actually successful.
                        if (Requests.isSuccessful(getClass(), response)) {
                            onResult.apply(ModelBase.loadModelList(Group.class, response, "groups"));
                        } else {
                            onResult.apply(null);
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.e(TAG, "get(): error in response.");
                        onResult.apply(null);
                    }
                }
        ));
    }
}
