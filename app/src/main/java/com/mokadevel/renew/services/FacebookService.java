package com.mokadevel.renew.services;

import android.content.Context;

import com.android.internal.util.Predicate;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.mokadevel.renew.util.ConnectionListener;
import com.mokadevel.renew.util.EventSource;
import com.mokadevel.renew.models.User;
import com.mokadevel.renew.requests.UserRequests;

/**
 * Created by rcastill on 2/11/16.
 */
public class FacebookService implements FacebookCallback<LoginResult> {
    private static FacebookService instance;

    private EventSource<User> connectionListeners;
    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private boolean loggedIn;

    private User user;

    private FacebookService(Context context) {
        FacebookSdk.sdkInitialize(context);
        callbackManager = CallbackManager.Factory.create();
        connectionListeners = new EventSource<>();
    }

    public static void initialize(Context context) {
        if (instance != null)
            return;

        instance = new FacebookService(context);
    }

    public static FacebookService getInstance() {
        return instance;
    }

    public void registerConnectionListener(ConnectionListener connectionListener) {
        connectionListeners.register(connectionListener);
    }

    public void authenticate() {
        if (!loggedIn)
            return;

        UserRequests.authenticate(accessToken.getUserId(), accessToken.getToken(), new Predicate<User>() {
            @Override
            public boolean apply(User user) {
                FacebookService.this.user = user;
                connectionListeners.notifyEvent(user);
                return false;
            }
        });
    }

    public User getUser() {
        return user;
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        accessToken = loginResult.getAccessToken();
        loggedIn = true;

        authenticate();
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {

    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }
}
