package com.mokadevel.youoweme.modelbase;

import android.util.Log;
import com.mokadevel.youoweme.models.Group;
import com.mokadevel.youoweme.models.JsonLoadable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A layer of abstraction for easier model management.
 */
public class ModelBase
{
    /**
     * Loads a model given the class of the model (which needs to implement {@link JsonLoadable}) and
     * the JSONObject containing the object's data.
     *
     * @param modelClass the class of the model.
     * @param json       the json object data.
     * @return the object or null in case of error.
     */
    public static <T extends JsonLoadable> T loadModel(Class<T> modelClass, JSONObject json)
    {
        try {
            T object = modelClass.newInstance();
            object.fromJson(json);
            return object;

        } catch (InstantiationException e) {
            Log.e("DEVLOG", "Could not create model of type " + modelClass.getCanonicalName());
        } catch (IllegalAccessException e) {
            Log.e("DEVLOG", "Could not create model of type " + modelClass.getCanonicalName());
        } catch (JSONException e) {
            Log.e("DEVLOG", "Invalid json for model " + modelClass.getCanonicalName() + ": " + json);
        }

        return null;
    }

    /**
     * Loads a model given the class of the model (which needs to implement
     * {@link JsonLoadable}), a standardised response object, and the object
     * key inside that response.
     * <p>
     * This can be seen as a really specific case, but actually in our REST
     * server, this situation is quite common, enough to make an utility for
     * it.
     *
     * @param modelClass the class of the model.
     * @param json       the response object.
     * @param key        the key in the response object.
     * @return the object or null in case of error.
     */
    public static <T extends JsonLoadable> T loadModel(Class<T> modelClass, JSONObject json, String key)
    {
        try {
            return loadModel(modelClass, json.getJSONObject(key));
        } catch (JSONException e) {
            Log.e("DEVLOG", "No existing key: " + key);

            return null;
        }
    }

    /**
     * Loads a model given the class of the model (which needs to implement {@link JsonLoadable}) and
     * a string containing the not-yet-parsed json data.
     *
     * @param modelClass the class of the model.
     * @param json       the json string of the model data.
     * @return the object or null in case of error.
     */
    public static <T extends JsonLoadable> T loadModel(Class<T> modelClass, String json)
    {
        try {
            return loadModel(modelClass, new JSONObject(json));
        } catch (JSONException e) {
            Log.e("DEVLOG", "Not valid JSON: " + json);

            return null;
        }
    }

    public static <T extends JsonLoadable> ArrayList<T> loadModelList(Class<T> modelClass, JSONArray data)
    {
        ArrayList<T> objects = new ArrayList<>();

        try {
            for (int i = 0; i < data.length(); i++) {
                objects.add(loadModel(modelClass, data.getJSONObject(i)));
            }
        } catch (JSONException e) {
            Log.e("DEVLOG", "Error accessing array: " + data);

            return null;
        }

        return objects;
    }

    public static <T extends JsonLoadable> ArrayList<T> loadModelList(Class<T> modelClass,
            JSONObject response, String key)
    {
        try {
            return loadModelList(modelClass, response.getJSONArray(key));
        } catch (JSONException e) {
            Log.e("DEVLOG", "Error in response: " + response);

            return null;
        }
    }
}
