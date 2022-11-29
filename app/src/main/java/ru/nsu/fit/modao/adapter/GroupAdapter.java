package ru.nsu.fit.modao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.nsu.fit.modao.R;
import ru.nsu.fit.modao.model.Groups;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
    Context context;
    List<Groups> groups;

    public GroupAdapter(Context context, List<Groups> groups) {
        this.context = context;
        this.groups = groups;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View groupItem = LayoutInflater.from(context).inflate(R.layout.group_item, parent, false);
        return new GroupViewHolder(groupItem);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        holder.name.setText(groups.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public static final class GroupViewHolder extends RecyclerView.ViewHolder{
        TextView name;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nameGroup);
        }
    }
}
