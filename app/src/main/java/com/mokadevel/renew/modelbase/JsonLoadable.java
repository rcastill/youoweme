package com.mokadevel.renew.modelbase;

import org.json.JSONException;
import org.json.JSONObject;

public interface JsonLoadable<T>
{
    T fromJson(JSONObject object) throws JSONException;
}
