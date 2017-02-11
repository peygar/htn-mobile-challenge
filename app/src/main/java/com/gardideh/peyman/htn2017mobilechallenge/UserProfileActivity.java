package com.gardideh.peyman.htn2017mobilechallenge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.BlurTransformation;

/**
 * Created by Peyman on 2017-02-09.
 */

public class UserProfileActivity extends Activity implements AbsListView.OnScrollListener {

    private ListView userInfo;
    private ImageView heroImage;
    private ImageView blurredImage;
    private ViewGroup heroHeader;
    private int lastTopValue = 0;
    UserProfile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Intent intent = getIntent();
        profile = (UserProfile) intent.getSerializableExtra("profile");

        // Hero
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        heroHeader = (ViewGroup) inflater.inflate(R.layout.user_profile_header_view, null);
        heroImage = (ImageView) heroHeader.findViewById(R.id.user_image);
        blurredImage = (ImageView) heroHeader.findViewById(R.id.blurred_user_image);
        Picasso.with(this).load(profile.pictureURL).into(heroImage);
        Picasso.with(this).load(profile.pictureURL)
                .transform(new BlurTransformation(this, 20, 2))
                .into(blurredImage);
        TextView nameField = (TextView) heroHeader.findViewById(R.id.name);
        nameField.setText(profile.name);

        // Info
        userInfo = (ListView) findViewById(R.id.user_profile);
        userInfo.addHeaderView(heroHeader);
        userInfo.setAdapter(new UserProfileInfoAdaptor(this, profile));
        userInfo.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Rect rect = new Rect();
        heroImage.getLocalVisibleRect(rect);
        if (lastTopValue != rect.top) {
            heroImage.setAlpha((float) Math.min(Math.abs(1 - rect.top / 600.0), 1.0));
            blurredImage.setAlpha((float) Math.min(rect.top / 600.0, 0.7));
            lastTopValue = rect.top;
            heroImage.setY((float) (rect.top / 1.5));
        }
    }
}
