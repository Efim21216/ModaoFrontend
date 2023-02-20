package ru.nsu.fit.modao.adapter;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import ru.nsu.fit.modao.R;
import ru.nsu.fit.modao.model.CreateExpenseParticipant;
import ru.nsu.fit.modao.model.User;

public class CoefficientAdapter extends RecyclerView.Adapter<CoefficientAdapter.CoefficientViewHolder> {
    private List<CreateExpenseParticipant> participants = new LinkedList<>();
    private Listener listener;

    public void attachListener(Listener listener) {
        this.listener = listener;
    }

    public void setParticipants(List<CreateExpenseParticipant> participants) {
        this.participants = participants;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CoefficientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View coefficientItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.coefficient_item, parent, false);
        return new CoefficientViewHolder(coefficientItem, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CoefficientViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.bind(participants.get(position));
    }

    @Override
    public int getItemCount() {
        return participants.size();
    }

    public static final class CoefficientViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        EditText editCoefficient;
        Listener listener;

        public CoefficientViewHolder(@NonNull View itemView, Listener listener) {
            super(itemView);
            username = itemView.findViewById(R.id.nameParticipant);
            editCoefficient = itemView.findViewById(R.id.editCoefficient);
            this.listener = listener;
        }

        public void bind(CreateExpenseParticipant participant) {
            username.setText(participant.getUsername());
            editCoefficient.setText(participant.getCoefficient().toString());
            editCoefficient.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    listener.onEditText(s.toString(), participant);

                }
            });
        }
    }

    public interface Listener {
        void onEditText(String value, CreateExpenseParticipant participant);
    }
}
