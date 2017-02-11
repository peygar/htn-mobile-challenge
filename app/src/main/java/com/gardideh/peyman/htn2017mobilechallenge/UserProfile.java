package com.gardideh.peyman.htn2017mobilechallenge;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Peyman on 2017-02-09.
 */

public class UserProfile implements Comparable<UserProfile>, Serializable {
    String name;
    String pictureURL;
    String company;
    String email;
    String phone;
    double latitude;
    double longitude;
    UserSkill[] skills;

    public UserProfile(JSONObject userJSON) {
        try {
            name = userJSON.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("parsing user", "name not found");
        }

        try {
            pictureURL = userJSON.getString("picture");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("parsing user", "pictureURL not found");
        }

        try {
            company = userJSON.getString("company");
        } catch (JSONException e) {
            Log.e("parsing user", "company not found");
            e.printStackTrace();
        }

        try {
            email = userJSON.getString("email");
        } catch (JSONException e) {
            Log.e("parsing user", "email not found");
            e.printStackTrace();
        }

        try {
            phone = userJSON.getString("phone");
        } catch (JSONException e) {
            Log.e("parsing user", "phone not found");
            e.printStackTrace();
        }

        try {
            latitude = userJSON.getDouble("latitude");
        } catch (JSONException e) {
            Log.e("parsing user", "latitude not found");
            e.printStackTrace();
        }

        try {
            longitude = userJSON.getDouble("longitude");
        } catch (JSONException e) {
            Log.e("parsing user", "longitude not found");
            e.printStackTrace();
        }

        try {
            JSONArray skillsObject = userJSON.getJSONArray("skills");
            skills = new UserSkill[skillsObject.length()];
            for (int i = 0; i < skillsObject.length(); i++) {
                skills[i] = new UserSkill(skillsObject.getJSONObject(i));
            }
            Arrays.sort(skills);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("parsing user", "skills not found");
        }
    }

    @Override
    public int compareTo(UserProfile o) {
        return this.name.toLowerCase().compareTo(o.name.toLowerCase());
    }
}
