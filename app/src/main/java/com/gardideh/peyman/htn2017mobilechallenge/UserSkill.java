package com.gardideh.peyman.htn2017mobilechallenge;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Peyman on 2017-02-09.
 */

public class UserSkill implements Comparable<UserSkill>, Serializable {
    String name;
    int rating;

    UserSkill(JSONObject skillObject) throws JSONException {
        name = skillObject.getString("name");
        rating = (int) skillObject.getDouble("rating");
    }

    @Override
    public int compareTo(UserSkill o) {
        if (this.rating == o.rating) {
            return 0;
        } else if (this.rating > o.rating) {
            return -1;
        } else {
            return 1;
        }
    }
}
