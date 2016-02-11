package com.mokadevel.youoweme.requests;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Requests
{
    public static final String TARGET_URL = "http://192.168.0.11:8000";

    // Singleton instance.
    private static Requests instance;

    // Request queue.
    private RequestQueue requestQueue;

    private Requests(Context context)
    {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void add(Request request)
    {
        requestQueue.add(request);
    }

    public static void initialize(Context context)
    {
        if (instance == null)
            instance = new Requests(context);
    }

    public static Requests getInstance()
    {
        return instance;
    }

    public static String makeUrl(String action, Object... args)
    {
        return String.format(TARGET_URL + action, args);
    }
}
