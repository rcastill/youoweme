package com.mokadevel.youoweme.requests;

import java.util.Objects;

public class Requests
{
    public static final String TARGET_URL = "192.168.0.11";

    public static String makeUrl(String action, Object... args)
    {
        return String.format(TARGET_URL + action, args);
    }
}
