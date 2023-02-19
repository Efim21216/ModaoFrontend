package ru.nsu.fit.modao.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import ru.nsu.fit.modao.R;
import ru.nsu.fit.modao.adapter.CoefficientAdapter;
import ru.nsu.fit.modao.adapter.ParticipantSpentAdapter;
import ru.nsu.fit.modao.adapter.ParticipantsAdapter;
import ru.nsu.fit.modao.databinding.FragmentEnterCoefficientsBinding;
import ru.nsu.fit.modao.databinding.FragmentEnterCostBinding;
import ru.nsu.fit.modao.model.CreateExpenseParticipant;


public class EnterCoefficientsFragment extends Fragment implements CoefficientAdapter.Listener {
    FragmentEnterCoefficientsBinding binding;
    List<CreateExpenseParticipant> participants;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEnterCoefficientsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        participants = Arrays.asList(EnterCoefficientsFragmentArgs.fromBundle(getArguments()).getParticipants());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        CoefficientAdapter adapter = new CoefficientAdapter();
        adapter.attachListener(this);
        adapter.setParticipants(participants);
        binding.participantRecycler.setLayoutManager(layoutManager);
        binding.participantRecycler.setAdapter(adapter);
        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean correct = true;
                for (CreateExpenseParticipant participant: participants){
                    try {
                        participant.setCoefficient(Float.parseFloat(participant.getAssumedCoefficient()));
                    } catch (NumberFormatException e){
                        Toast.makeText(getContext(), R.string.enterData, Toast.LENGTH_LONG).show();
                        correct = false;
                        break;
                    }
                }
                if (correct){
                    Toast.makeText(getContext(), String.valueOf(participants.size()), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onEditText(String value, CreateExpenseParticipant participant) {
        int index = participants.indexOf(participant);
        participants.get(index).setAssumedCoefficient(value);
    }
}