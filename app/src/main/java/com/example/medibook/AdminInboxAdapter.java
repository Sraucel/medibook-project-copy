package com.example.medibook;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import android.util.Log;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.medibook.classes.User;
public class AdminInboxAdapter extends RecyclerView.Adapter<AdminInboxViewHolder> {
    Context context;
    List<User> userList2;

    public AdminInboxAdapter(Context context, List<User> userList2){
        this.context = context;
        this.userList2 = userList2;
    }

    private OnClickListener mListener;
    private View lastClickedView;

    public void setOnClickListener(OnClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public AdminInboxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new AdminInboxViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_recycler_userinfo, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull AdminInboxViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.firstNameView.setText(userList2.get(position).getFirstName());
        holder.lastNameView.setText(userList2.get(position).getLastName());
        holder.emailView.setText(userList2.get(position).getEmail());
        holder.phoneNumberView.setText(userList2.get(position).getPhoneNumber());
        holder.addressView.setText(userList2.get(position).getAddress());
        holder.statusView.setText(userList2.get(position).getStatus());
        holder.relativeLayout.setTag(position);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                   int clickedPosition = (int) v.getTag();
                   mListener.onItemClick(clickedPosition);
                   lastClickedView = v;
                }
            }
        });

    }



    @Override
    public int getItemCount(){
        return userList2.size();
    }

    public View getLastClickedView() {
        return lastClickedView;
    }

    public interface OnClickListener {
        void onItemClick(int position);
    }





}
