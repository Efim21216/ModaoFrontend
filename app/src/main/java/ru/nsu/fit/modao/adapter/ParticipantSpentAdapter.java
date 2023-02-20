package ru.nsu.fit.modao.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.nsu.fit.modao.R;
import ru.nsu.fit.modao.model.CreateExpenseParticipant;

public class ParticipantSpentAdapter extends RecyclerView.Adapter<ParticipantSpentAdapter.ParticipantSpentViewHolder> {
    List<CreateExpenseParticipant> participants;
    private Listener listener;

    public void attachListener(Listener listener){
        this.listener = listener;
    }

    public void setParticipants(List<CreateExpenseParticipant> participants) {
        this.participants = participants;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ParticipantSpentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View participantItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.participant_spent_item,
                parent, false);
        return new ParticipantSpentViewHolder(participantItem, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantSpentViewHolder holder, int position) {
        holder.bind(participants.get(position));
    }


    @Override
    public int getItemCount() {
        return participants.size();
    }

    public static final class ParticipantSpentViewHolder extends RecyclerView.ViewHolder{
        TextView userName;
        RadioButton button;
        Listener listener;
        public ParticipantSpentViewHolder(@NonNull View itemView, Listener listener) {
            super(itemView);
            this.listener = listener;
            userName = itemView.findViewById(R.id.nameParticipant);
            button = itemView.findViewById(R.id.selectParticipant);
        }
        public void bind(CreateExpenseParticipant participant){
            userName.setText(participant.getUsername());
            button.setOnClickListener((v) -> listener.onClick((RadioButton) v, participant));
        }
    }
    public interface Listener{
        void onClick(RadioButton b, CreateExpenseParticipant participant);
    }
}
