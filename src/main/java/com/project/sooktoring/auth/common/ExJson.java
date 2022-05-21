package com.project.sooktoring.auth.common;

public class ExJson {

    public static final String GOOGLE_ID_TOKEN = "{\n  \"status\": 400, \n  \"message\": \"Google Id Token is unauthorized\", \n  \"redirectUri\": \"/auth/login\"\n}";
    public static final String GOOGLE_RESOURCE_SERVER = "{\n  \"status\": 500, \n  \"message\": \"Google Resource Server Access Denied\", \n  \"redirectUri\": \"\"\n}";
    public static final String EXPIRED_ACCESS_TOKEN = "{\n  \"status\": 400, \n  \"message\": \"Expired Access Token\", \n  \"redirectUri\": \"/auth/refresh\"\n}";
    public static final String EXPIRED_REFRESH_TOKEN = "{\n  \"status\": 400, \n  \"message\": \"Expired Refresh Token\", \n  \"redirectUri\": \"/auth/login\"\n}";
    public static final String INVALID_REFRESH_TOKEN = "{\n  \"status\": 400, \n  \"message\": \"Invalid Refresh Token\", \n  \"redirectUri\": \"/auth/login\"\n}";
    public static final String INVALID_JWT_TOKEN = "{\n  \"status\": 500, \n  \"message\": \"각 Exception의 기본 메시지\", \n  \"redirectUri\": \"/auth/login\"\n}";
}
