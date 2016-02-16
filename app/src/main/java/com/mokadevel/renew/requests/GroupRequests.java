package com.mokadevel.renew.requests;

import android.util.Log;
import com.android.internal.util.Predicate;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.mokadevel.renew.modelbase.ModelBase;
import com.mokadevel.renew.models.Group;
import com.mokadevel.renew.models.User;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GroupRequests
{
    private static final String TAG = "DEVLOG:GROUP_REQUESTS";

    private static final String URL_SAVE = "/groups/create/%d/";
    private static final String URL_ADD = "/groups/add/%d/%d/";
    private static final String URL_DELETE = "/groups/delete/%d/";
    private static final String URL_GET = "/groups/get/%d/";
    private static final String URL_GET_ALL = "/groups/get_all/%s/";

    /**
     * Creates a new group with the creator user already as a member.
     *
     * @param name     the name of the group.
     * @param creator  the creator user.
     * @param onResult the callback on result.
     */
    public static void create(final String name, User creator, final Predicate<Group> onResult)
    {
        Requests.getInstance().add(new StringRequest(
                Request.Method.POST,
                Requests.makeUrl(URL_SAVE, creator.getId()),
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            // received a response, check that the response was actually successful.
                            if (Requests.isSuccessful(getClass(), jsonResponse)) {
                                onResult.apply(ModelBase.loadModel(Group.class, jsonResponse, "group"));
                            } else {
                                onResult.apply(null);
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "onResponse: error parsing response to JSON.");
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
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String, String> params = new HashMap<>();
                params.put("name", name);

                return params;
            }
        });
    }

    /**
     * Requests for a group model object.
     *
     * @param id       the id of the group.
     * @param onResult the callback on result.
     */
    public static void get(long id, final Predicate<Group> onResult)
    {
        Requests.getInstance().add(new JsonObjectRequest(
                Request.Method.GET,
                Requests.makeUrl(URL_GET, id),
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

    /**
     * Retrieves all groups of the specified user.
     *
     * @param user      the user of which we want to know his groups.
     * @param onResult  the result predicate, receiving a list of those groups.
     */
    public static void getAll(User user, final Predicate<ArrayList<Group>> onResult)
    {
        Requests.getInstance().add(new JsonObjectRequest(
                Request.Method.GET,
                Requests.makeUrl(URL_GET_ALL, user.getId()),
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
