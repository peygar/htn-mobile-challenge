package com.gardideh.peyman.htn2017mobilechallenge;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Peyman on 2017-02-09.
 */

class UserProfileInfoAdaptor extends BaseAdapter {
    private enum SectionType {
        CONTACT, MAP
    }

    private enum ContactInfo {
        PHONE, EMAIL, COMPANY
    }

    private Context context;
    private UserProfile userProfile;
    private static LayoutInflater inflater = null;

    UserProfileInfoAdaptor(Context context, UserProfile userProfile) {
        this.context = context;
        this.userProfile = userProfile;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return hasField(userProfile.phone)
                + hasField(userProfile.email)
                + hasField(userProfile.company)
                + hasLocation();
    }

    @Override
    public Object getItem(int position) {
        switch (SectionType.values()[getItemViewType(position)]) {
            case CONTACT:
                return  "Info Cell";
            case MAP:
                return "Map Cell";
            default:
                return "INVALID";
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 1 + hasLocation();
    }

    @Override
    public int getItemViewType(int position) {
        int infoMax = hasField(userProfile.phone)
                + hasField(userProfile.email)
                + hasField(userProfile.company);
        if (position < infoMax) {
            return SectionType.CONTACT.ordinal();
        } else if (position == infoMax && hasLocation() == 1) {
            return SectionType.MAP.ordinal();
        }
        return 0;
    }

    private ContactInfo getContactInfoType(int position) {
        position -= hasField(userProfile.phone);
        if (position == -1) {
            return ContactInfo.PHONE;
        }
        position -= hasField(userProfile.email);
        if (position == -1) {
            return ContactInfo.EMAIL;
        }
        position -= hasField(userProfile.company);
        if (position == -1) {
            return ContactInfo.COMPANY;
        }
        return ContactInfo.PHONE;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        switch (SectionType.values()[getItemViewType(position)]) {
            case CONTACT:
                if (vi == null)
                    vi = inflater.inflate(R.layout.user_profile_info_row, null);
                TextView fieldType = (TextView) vi.findViewById(R.id.field_type);
                TextView fieldValue = (TextView) vi.findViewById(R.id.field_value);
                ImageButton icon = (ImageButton) vi.findViewById(R.id.button_icon);
                switch (getContactInfoType(position)) {
                    case PHONE:
                        fieldType.setText("Phone");
                        fieldValue.setText(userProfile.phone);
                        icon.setImageResource(android.R.drawable.stat_sys_phone_call);
                        break;
                    case EMAIL:
                        fieldType.setText("Email");
                        fieldValue.setText(userProfile.email);
                        icon.setImageResource(android.R.drawable.sym_action_email);
                        break;
                    case COMPANY:
                        fieldType.setText("Company");
                        fieldValue.setText(userProfile.company);
                        icon.setImageResource(android.R.drawable.star_on);
                        break;
                }
                break;
            case MAP:
                if (vi == null)
                    vi = inflater.inflate(R.layout.user_profile_map_row, null);
                String mapUrl = "http://maps.google.com/maps/api/staticmap?center="
                        + userProfile.latitude + "," + userProfile.longitude +
                        "&zoom=15&size=320x180&sensor=false&markers=size:mid%7Ccolor:red%7C"
                        + userProfile.latitude + "," + userProfile.longitude;
                Log.d("map", mapUrl);
                ImageView mapImage = (ImageView) vi.findViewById(R.id.map_image);
                Picasso.with(context).load(mapUrl).into(mapImage);
                break;
            default:
                break;
        }
        return vi;
    }

    private int hasField(String field) {
        return field != null && field != "" ? 1 : 0;
    }

    private int hasField(double field) {
        return field != 0 ? 1 : 0;
    }

    private int hasLocation() {
        return userProfile.latitude != 0.0 && userProfile.longitude != 0.0 ? 1 : 0;
    }
}
