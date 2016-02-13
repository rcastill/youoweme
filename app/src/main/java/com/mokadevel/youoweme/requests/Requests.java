package com.mokadevel.youoweme.requests;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.mokadevel.youoweme.modelbase.ModelBase;
import com.mokadevel.youoweme.models.User;
import org.json.JSONException;
import org.json.JSONObject;

public class Requests
{
    public static final String TARGET_URL = "http://192.168.0.11:8000";

    private static final String TAG = "DEVLOG:REQUESTS";

    // Singleton instance.
    private static Requests instance;

    // Request queue.
    private RequestQueue requestQueue;

    private Requests(Context context)
    {
        requestQueue = Volley.newRequestQueue(context);
    }

    public static void initialize(Context context)
    {
        if (instance == null)
            instance = new Requests(context);
    }

    public void add(Request request)
    {
        requestQueue.add(request);
    }

    // - Static utilities.
    public static String makeUrl(String action, Object... args)
    {
        return String.format(TARGET_URL + action, args);
    }

    /**
     * Checks if a response was successful. This automatically outputs the error
     * to the Android log.
     *
     * @param source        class who asks.
     * @param response      response to verify.
     * @return              true if success.
     */
    public static boolean isSuccessful(Class source, JSONObject response)
    {
        try {
            if (response.getBoolean("success")) {
                return true;
            } else {
                logError(source, "response was unsuccessful: %s", response.getString("message"));
                return false;
            }
        } catch (JSONException e) {
            logError(source, "JSON error: %s", response);
            return false;
        }
    }

    private static void logError(Class source, String format, Object... args)
    {
        Log.e(TAG, source.getCanonicalName() + ", " + String.format(format, args));
    }

    // Singleton.
    public static Requests getInstance()
    {
        return instance;
    }
}
