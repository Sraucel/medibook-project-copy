package com.example.medibook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medibook.classes.User;

import java.util.List;

public class AdminRejectedListAdapter extends RecyclerView.Adapter<AdminRejectedListViewHolder> {

    Context context;
    List<User> userList1;


    public AdminRejectedListAdapter(Context context, List<User> userList1){
        this.context = context;
        this.userList1 = userList1;
    }


    private OnClickListener mListener;

    private View lastClickedView;

    public void setOnClickListener(OnClickListener listener) {
        mListener = listener;
    }
    @NonNull
    @Override
    public AdminRejectedListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdminRejectedListViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_recycler_rejected_userinfo,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdminRejectedListViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.firstNameView.setText(userList1.get(position).getFirstName());
        holder.LastnameView.setText(userList1.get(position).getLastName());
        holder.emailView.setText(userList1.get(position).getEmail());
        holder.phoneNumberView.setText(userList1.get(position).getPhoneNumber());
        holder.addressView.setText(userList1.get(position).getAddress());
        holder.statusView.setText(userList1.get(position).getStatus());
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
    public int getItemCount() {
        return userList1.size();
    }

    public View getLastClickedView() {
        return lastClickedView;
    }

    public interface OnClickListener {
        void onItemClick(int position);
    }


}
