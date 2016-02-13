package com.mokadevel.youoweme.requests;

import android.util.Log;
import com.android.internal.util.Predicate;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mokadevel.youoweme.modelbase.ModelBase;
import com.mokadevel.youoweme.models.User;
import org.json.JSONObject;

/**
 * Handles user related requests to the server.
 */
public class UserRequests
{
    private static final String TAG = "DEVLOG:USER_REQUESTS";

    // URLS.
    private static final String SIGN_IN = "/users/sign_in/%s/%s/";

    /**
     * Asynchronously tries to authenticate an user. If the authentication succeeds, the
     * onResult callback will be called with the resulting User model object, otherwise,
     * if the process fails for whatever reason, the callback will be called with null.
     *
     * @param id            Facebook ID of the user to authenticate.
     * @param accessToken   Facebook access token.
     * @param onResult      Predicate that gets called with the result.
     */
    public static void authenticate(String id, String accessToken, final Predicate<User> onResult)
    {
        Requests.getInstance().add(new JsonObjectRequest(
                Request.Method.GET,
                Requests.makeUrl(SIGN_IN, id, accessToken),
                null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        // received a response, check that the response was actually successful.
                        if (Requests.isSuccessful(getClass(), response)) {
                            onResult.apply(ModelBase.loadModel(User.class, response, "user"));
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
                        Log.e(TAG, "authenticate(): error in listener.");
                        onResult.apply(null);
                    }
                }
        ));
    }
}
