package ru.nsu.fit.modao.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ru.nsu.fit.modao.R;
import ru.nsu.fit.modao.fragments.EnterCoefficientsFragment;
import ru.nsu.fit.modao.model.CreateExpenseParticipant;
import ru.nsu.fit.modao.model.User;

public class ParticipantsAdapter extends RecyclerView.Adapter<ParticipantsAdapter.ParticipantsViewHolder> {
    Context context;
    List<CreateExpenseParticipant> participants;
    List<CreateExpenseParticipant> selected;

    public ParticipantsAdapter(Context context, List<CreateExpenseParticipant> participants) {
        this.context = context;
        this.participants = participants;
        selected = new LinkedList<>();
    }

    @NonNull
    @Override
    public ParticipantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View participantItem = LayoutInflater.from(context).inflate(R.layout.participant_item, parent, false);
        return new ParticipantsAdapter.ParticipantsViewHolder(participantItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.nameParticipant.setText(participants.get(position).getUsername());
        holder.checkName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkName.isChecked()){
                    selected.add(participants.get(position));
                }
                else{
                    selected.remove(participants.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return participants.size();
    }

    public static final class ParticipantsViewHolder extends RecyclerView.ViewHolder{

        TextView nameParticipant;
        CheckBox checkName;

        public ParticipantsViewHolder(@NonNull View itemView) {
            super(itemView);

            nameParticipant = itemView.findViewById(R.id.nameParticipant);
            checkName = itemView.findViewById(R.id.checkParticipant);
        }
    }
    public List<CreateExpenseParticipant> getSelected(){
        return selected;
    }
}
