package com.example.nn0lumesther.javadev.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nn0lumesther.javadev.R;
import com.example.nn0lumesther.javadev.activity.DetailActivity;
import com.example.nn0lumesther.javadev.activity.MainActivity;
import com.example.nn0lumesther.javadev.model.Developer;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by NN0LUM ESTHER on 02/09/2017.
 */
public class DeveloperAdapter extends RecyclerView.Adapter<DeveloperAdapter.DeveloperHolder> {

    public static String DEVELOPER_KEY = "developer";
    private Context mContext;
    private List<Developer> developers;

    public DeveloperAdapter(Context context, List<Developer> developers) {
        this.mContext = context;
        this.developers = developers;
    }

    @Override
    public DeveloperHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.java_developer_item, parent, false);

        return new DeveloperHolder(rootView);
    }

    @Override
    public void onBindViewHolder(DeveloperHolder holder, int position) {
        final Developer developer = developers.get(position);

        holder.username.setText(developer.getLogin());
        Glide.with(mContext)
                .load(developer.getAvatarUrl())
                .into(holder.profileImage);
    }

    @Override
    public int getItemCount() {
        return developers.size();
    }

    public class DeveloperHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView profileImage;
        private TextView username;

        public DeveloperHolder(View itemView) {
            super(itemView);
            profileImage = (CircleImageView) itemView.findViewById(R.id.profile_image);
            username = (TextView) itemView.findViewById(R.id.username);
            itemView.setOnClickListener(this);
        }

        //intent to the DetailActivity on clicking the username textview or imageview
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, DetailActivity.class);
            intent.putExtra(DEVELOPER_KEY, developers.get(getAdapterPosition()));
            mContext.startActivity(intent);
        }

    }

}