package ru.nsu.fit.modao.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.nsu.fit.modao.R;
import ru.nsu.fit.modao.fragments.GroupsFragmentDirections;
import ru.nsu.fit.modao.model.ShortInfoGroup;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
    Context context;
    List<ShortInfoGroup> groups;

    public GroupAdapter(Context context, List<ShortInfoGroup> groups) {
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
        holder.name.setText(groups.get(position).getGroupName());

        holder.itemView.setOnClickListener(view -> {
            ShortInfoGroup shortGroup = new ShortInfoGroup(groups.get(position).getGroupName(), groups.get(position).getId());
            NavDirections action = GroupsFragmentDirections.actionGroupsFragmentToGroupInfoFragment(shortGroup);
            Navigation.findNavController(view).navigate(action);
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
