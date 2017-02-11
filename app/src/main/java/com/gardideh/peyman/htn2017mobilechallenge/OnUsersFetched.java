package com.gardideh.peyman.htn2017mobilechallenge;

import java.io.IOException;

public interface OnUsersFetched {
    void onUsersFetchSuccess(UserProfile[] users);
    void onUsersFetchFailure(Exception e);
}
