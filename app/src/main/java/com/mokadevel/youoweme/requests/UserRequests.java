package com.mokadevel.youoweme.requests;

import android.content.Context;
import com.android.internal.util.Predicate;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mokadevel.youoweme.models.User;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Handles user related requests to the server.
 */
public class UserRequests
{
    // Singleton instance.
    private static UserRequests instance;

    // URLS.
    private static final String SIGN_IN = "/users/sign_in/%s/%s/";

    // Request queue.
    private RequestQueue requestQueue;

    private UserRequests(Context context)
    {
        requestQueue = Volley.newRequestQueue(context);
    }

    /**
     * Asynchronously tries to authenticate an user. If the authentication succeeds, the
     * onResult callback will be called with the resulting User model object, otherwise,
     * if the process fails for whatever reason, the callback will be called with null.
     *
     * @param id            Facebook ID of the user to authenticate.
     * @param accessToken   Facebook access token.
     * @param onResult      Predicate that gets called with the result.
     */
    void authenticate(String id, String accessToken, final Predicate<User> onResult)
    {
        requestQueue.add(new JsonObjectRequest(
                Request.Method.GET,
                Requests.makeUrl(SIGN_IN, id, accessToken),
                null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try {
                            if (response.getBoolean("success")) {
                                onResult.apply(new User().fromJson(response.getJSONObject("user")));
                            } else {
                                onResult.apply(null);
                            }
                        } catch (JSONException e) {
                            onResult.apply(null);
                        }
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        onResult.apply(null);
                    }
                }
        ));
    }

    public UserRequests getInstance(Context context)
    {
        if (instance == null) {
            instance = new UserRequests(context);
        }

        return instance;
    }
}
