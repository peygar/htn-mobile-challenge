package com.gardideh.peyman.htn2017mobilechallenge;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Peyman on 2017-02-08.
 */

class ApiManager {
    private static ApiManager instance = null;
    private ApiManager() {
        // Exists only to defeat instantiation.
    }

    static ApiManager getInstance() {
        if(instance == null) {
            instance = new ApiManager();
        }
        return instance;
    }

    void fetchUserJson(final OnUsersFetched listener) {
        Log.d("getUserJson", "1");
        getJSONObjectFromURL("https://htn-interviews.firebaseio.com/users.json", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                listener.onUsersFetchFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonData = response.body().string();
                try {
                    JSONArray usersArray = new JSONArray(jsonData);
                    UserProfile users[] = new UserProfile[usersArray.length()];
                    for (int i = 0; i < usersArray.length(); i++) {
                        JSONObject userObject = usersArray.getJSONObject(i);
                        users[i] = new UserProfile(userObject);
                    }
                    listener.onUsersFetchSuccess(users);
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onUsersFetchFailure(e);
                }
            }
        });
        Log.d("getUserJson", "2");
    }

    private void getJSONObjectFromURL(String url, final Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request r = new Request.Builder()
                .url(url)
                .build();
        client.newCall(r).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.d("getJSONObjectFromURL", "Failed request");
                callback.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("getJSONObjectFromURL", response.toString());
                callback.onResponse(call, response);
            }
        });
    }
}