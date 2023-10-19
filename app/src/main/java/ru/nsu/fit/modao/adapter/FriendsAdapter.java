package ru.nsu.fit.modao.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import ru.nsu.fit.modao.R;
import ru.nsu.fit.modao.models.User;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsHolder> {
    private AdapterListener<User> listener;
    private User[] friendsList = {};

    @NonNull
    @Override
    public FriendsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item, parent, false);
        return new FriendsHolder(view);
    }

    public void setListener(AdapterListener<User> adapterListener) {
        listener = adapterListener;
    }

    public void setFriendsList(User[] friendsList) {
        this.friendsList = friendsList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsHolder holder, int position) {
        holder.bind(friendsList[position], listener);
    }

    @Override
    public int getItemCount() {
        return friendsList.length;
    }

    public static final class FriendsHolder extends RecyclerView.ViewHolder {
        View itemView;

        public void bind(User friend, AdapterListener<User> listener) {
            MaterialButton cell = itemView.findViewById(R.id.friendItem);
            cell.setOnClickListener(v -> listener.onClickItem(friend));
            cell.setText(friend.getUsername());
        }

        public FriendsHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }
}
