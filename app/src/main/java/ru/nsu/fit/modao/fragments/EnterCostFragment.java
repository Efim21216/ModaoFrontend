package ru.nsu.fit.modao.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.util.LinkedList;
import java.util.List;

import ru.nsu.fit.modao.R;
import ru.nsu.fit.modao.adapter.ParticipantSpentAdapter;
import ru.nsu.fit.modao.databinding.FragmentEnterCostBinding;
import ru.nsu.fit.modao.model.CreateExpenseParticipant;


public class EnterCostFragment extends Fragment implements ParticipantSpentAdapter.Listener {

    FragmentEnterCostBinding binding;
    RadioButton lastChecked = null;
    CreateExpenseParticipant sponsor;
    List<CreateExpenseParticipant> participants;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEnterCostBinding.inflate(inflater, container, false);
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

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float cost = Float.parseFloat(binding.editCost.getText().toString());
                NavDirections action = EnterCostFragmentDirections
                        .actionEnterCostFragmentToSelectParticipantsFragment(cost, sponsor);
                Navigation.findNavController(view).navigate(action);
            }
        });

        participants  = new LinkedList<>();
        participants.add(new CreateExpenseParticipant("Nikita", 1L));
        participants.add(new CreateExpenseParticipant("Efim", 2L));
        participants.add(new CreateExpenseParticipant("Olga", 3L));
        participants.add(new CreateExpenseParticipant("Peter", 4L));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        ParticipantSpentAdapter adapter = new ParticipantSpentAdapter();
        adapter.setParticipants(participants);
        adapter.attachListener(this);
        binding.participantRecycler.setLayoutManager(layoutManager);
        binding.participantRecycler.setAdapter(adapter);
    }

    @Override
    public void onClick(RadioButton button, CreateExpenseParticipant user) {
        if (button.isChecked()){
            if (lastChecked != null){
                lastChecked.setChecked(false);
            }
            lastChecked = button;
            sponsor = user;
        }
        else {
            lastChecked = null;
            sponsor = null;
        }
    }
}