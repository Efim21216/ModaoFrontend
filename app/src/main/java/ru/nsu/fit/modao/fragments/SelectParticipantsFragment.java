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

import java.util.LinkedList;
import java.util.List;

import ru.nsu.fit.modao.R;
import ru.nsu.fit.modao.adapter.ParticipantsAdapter;
import ru.nsu.fit.modao.databinding.FragmentSelectParticipantsBinding;
import ru.nsu.fit.modao.model.CreateExpenseParticipant;
import ru.nsu.fit.modao.model.User;


public class SelectParticipantsFragment extends Fragment {

    FragmentSelectParticipantsBinding binding;
    List<CreateExpenseParticipant> participants;
    CreateExpenseParticipant sponsor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSelectParticipantsBinding.inflate(inflater, container, false);
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


        participants  = new LinkedList<>();
        sponsor = SelectParticipantsFragmentArgs.fromBundle(getArguments()).getSponsor();
        participants.add(new CreateExpenseParticipant("Nikita", 1f, 1L, "1"));
        participants.add(new CreateExpenseParticipant("Efim", 1f,2L, "1"));
        participants.add(new CreateExpenseParticipant("Olga", 1f,3L, "1"));
        participants.add(new CreateExpenseParticipant("Peter", 1f,4L, "1"));
        ParticipantsAdapter adapter = new ParticipantsAdapter(getContext(), participants);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        binding.participantRecycler.setLayoutManager(layoutManager);
        binding.participantRecycler.setAdapter(adapter);
        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<CreateExpenseParticipant> list = adapter.getSelected();
                CreateExpenseParticipant[] users = new CreateExpenseParticipant[list.size()];
                list.toArray(users);
                NavDirections action = SelectParticipantsFragmentDirections
                        .actionSelectParticipantsFragmentToEnterCoefficientsFragment(users,
                                SelectParticipantsFragmentArgs.fromBundle(getArguments()).getCost(),
                                sponsor);
                Navigation.findNavController(view).navigate(action);
            }
        });
    }
}