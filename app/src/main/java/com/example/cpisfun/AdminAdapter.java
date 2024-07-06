package com.example.cpisfun;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.example.cpisfun.R;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class AdminAdapter extends ArrayAdapter<Admin> {
    private Context mContext;
    private List<Admin> adminList;

    public AdminAdapter(@NonNull Context context, List<Admin> list) {
        super(context, 0 , list);
        mContext = context;
        adminList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.item_admin, parent, false);

        Admin currentAdmin = adminList.get(position);

        ImageView avatar = listItem.findViewById(R.id.adminAvatar);
        TextView name = listItem.findViewById(R.id.adminText);
        TextView adminValue = listItem.findViewById(R.id.adminTextValue);

        Glide.with(mContext)
                .load(currentAdmin.getAvatarUrl())
                .circleCrop()
                .into(avatar);

        name.setText(currentAdmin.getName());
        adminValue.setText(currentAdmin.getText());

        return listItem;
    }
}
