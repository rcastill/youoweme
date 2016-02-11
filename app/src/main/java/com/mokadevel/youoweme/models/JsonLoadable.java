package com.mokadevel.youoweme.models;

import org.json.JSONException;
import org.json.JSONObject;

interface JsonLoadable<T>
{
    T fromJson(JSONObject object) throws JSONException;
}