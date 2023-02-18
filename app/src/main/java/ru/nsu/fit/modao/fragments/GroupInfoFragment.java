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

import ru.nsu.fit.modao.databinding.FragmentGroupInfoBinding;


public class GroupInfoFragment extends Fragment {
    FragmentGroupInfoBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGroupInfoBinding.inflate(inflater, container, false);
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

        binding.nameGroup.setText(GroupInfoFragmentArgs.fromBundle(getArguments()).getGroup().getGroupName());
        binding.buttonGroupExpenses.setOnClickListener(v -> {
            NavDirections action = GroupInfoFragmentDirections.actionGroupInfoFragmentToGroupExpensesFragment();
            Navigation.findNavController(view).navigate(action);
        });
    }
}