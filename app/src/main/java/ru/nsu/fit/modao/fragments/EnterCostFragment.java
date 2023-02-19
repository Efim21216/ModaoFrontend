package ru.nsu.fit.modao.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.nsu.fit.modao.R;
import ru.nsu.fit.modao.databinding.FragmentEnterCostBinding;


public class EnterCostFragment extends Fragment {

    FragmentEnterCostBinding binding;
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
                NavDirections action = EnterCostFragmentDirections.actionEnterCostFragmentToSelectParticipantsFragment(cost);
                Navigation.findNavController(view).navigate(action);
            }
        });
    }
}