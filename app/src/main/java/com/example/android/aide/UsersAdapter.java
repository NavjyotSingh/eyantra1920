package com.example.android.aide;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class UsersAdapter extends ArrayAdapter<Users> {
    public UsersAdapter(Context context, List<Users> users){
        super(context,0,users);
    }


    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        View listItemView = convertView;
        /*if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.user_profile, parent, false);
        }*/
        /*TextView nameTextView = (TextView)listItemView.findViewById(R.id.name);
        TextView unameTextView = (TextView) listItemView.findViewById(R.id.uname);
        TextView contactTextView = (TextView) listItemView.findViewById(R.id.contact);
        TextView userRoleTextView = (TextView) listItemView.findViewById(R.id.user_role);*/

        Users user = getItem(position);

        /*nameTextView.setText(user.getName());
        unameTextView.setText(user.getUname().toString());
        contactTextView.setText(user.getContactno());
        userRoleTextView.setText(user.getUserRole());*/

        return listItemView;
    }
}
