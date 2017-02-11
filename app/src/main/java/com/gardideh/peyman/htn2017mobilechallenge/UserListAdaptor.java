package com.gardideh.peyman.htn2017mobilechallenge;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by Peyman on 2017-02-08.
 */

class UserListAdaptor extends BaseAdapter implements StickyListHeadersAdapter {

    Context context;
    UserProfile[] users;
    private static LayoutInflater inflater = null;

    public UserListAdaptor(Context context, UserProfile[] users) {
        this.context = context;
        this.users = users;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return users.length;
    }

    @Override
    public Object getItem(int position) {
        return users[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.user_row, null);
        TextView nameField = (TextView) vi.findViewById(R.id.name);
        nameField.setText(users[position].name);

        LinearLayout skillsLayout = (LinearLayout) vi.findViewById(R.id.skills);
        if (skillsLayout.getChildCount() == 0) {
            for (int i = 0; i < Math.min(2, users[position].skills.size()); i++) {
                View skillView = inflater.inflate(R.layout.user_profile_skill_text, skillsLayout, false);
                TextView skillText = (TextView) skillView.findViewById(R.id.skill_text);
                TextView ratingText = (TextView) skillView.findViewById(R.id.rating_text);
                skillText.setText(users[position].skills.get(i).name);
                skillText.setTextSize(16);
                ratingText.setText("" + users[position].skills.get(i).rating);
                skillsLayout.addView(skillView);

                if (i + 1 < Math.min(2, users[position].skills.size())) {
                    TextView seperator = new TextView(context);
                    seperator.setPadding(15, 0, 15, 0);
                    seperator.setText("•");
                    seperator.setGravity(Gravity.CENTER);
                    seperator.setTextSize(20);

                    skillsLayout.addView(seperator);
                }
            }
        }

//        TextView skillsField = (TextView) vi.findViewById(R.id.skills);
//        String topSkills = "";
//        if (users[position].skills.size() > 0) {
//            topSkills = users[position].skills.get(0).name + " "
//                    + users[position].skills.get(0).rating;
//        }
//        if (users[position].skills.size() > 1) {
//            topSkills += " • " + users[position].skills.get(1).name + " "
//                    + users[position].skills.get(1).rating;
//        }
//        skillsField.setText(topSkills);

        ImageView userImage = (ImageView) vi.findViewById(R.id.userImage);
        Picasso.with(context).load(users[position].pictureURL).into(userImage);
        return vi;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.user_list_section_header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        //set header text as first char in name
        String headerText = "" + users[position].name.subSequence(0, 1).charAt(0);
        holder.text.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        //return the first character of the user name as ID because this is what headers are based upon
        return users[position].name.charAt(0);
    }

    class HeaderViewHolder {
        TextView text;
    }
}
