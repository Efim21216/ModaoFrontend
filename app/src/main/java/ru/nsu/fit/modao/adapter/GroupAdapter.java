package ru.nsu.fit.modao.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.nsu.fit.modao.ExpensesActivity;
import ru.nsu.fit.modao.GroupInfo;
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
    public void onBindViewHolder(@NonNull GroupViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(groups.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GroupInfo.class);

                intent.putExtra("nameGroup", groups.get(position).getName());
                intent.putExtra("groupID", groups.get(position).getId());
                context.startActivity(intent);
            }
        });
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
