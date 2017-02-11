package com.gardideh.peyman.htn2017mobilechallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.util.Arrays;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class MainActivity extends AppCompatActivity implements OnUsersFetched {

    StickyListHeadersListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (StickyListHeadersListView) findViewById(R.id.listview);
        ApiManager manager = ApiManager.getInstance();
        manager.fetchUserJson(this);
    }

    @Override
    public void onUsersFetchFailure(Exception e) {
        Log.d("main", "failed to fetch");
    }

    @Override
    public void onUsersFetchSuccess(final UserProfile[] users) {
        Arrays.sort(users);
        final UserListAdaptor adaptor = new UserListAdaptor(this, users);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listview.setAdapter(adaptor);
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("click", users[position].name);
                        Intent openProfile = new Intent(MainActivity.this, UserProfileActivity.class);
                        openProfile.putExtra("profile", users[position]);
                        MainActivity.this.startActivity(openProfile);
                    }
                });
            }
        });
    }
}
