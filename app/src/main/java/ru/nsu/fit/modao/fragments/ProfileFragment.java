package ru.nsu.fit.modao.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.nsu.fit.modao.R;
import ru.nsu.fit.modao.databinding.FragmentProfileBinding;
import ru.nsu.fit.modao.repository.MainViewModel;
import ru.nsu.fit.modao.repository.MyApplication;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    NavController navController;
    MainViewModel mainViewModel;
    MyApplication app;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
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

        app = (MyApplication) getActivity().getApplication();
        mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        mainViewModel.getUserLive().observe(getViewLifecycleOwner(), user -> {
            binding.personName.setText(user.getUsername());
            binding.personBank.setText(user.getBank());
            binding.personPhone.setText(user.getPhone_number());
        });
        mainViewModel.getUser(app.getQuestApi(), app.getUserID());
        navController = Navigation.findNavController(view);
        binding.logOutLayout.setOnClickListener(v -> {
            app.setUserID(-1L);
            NavDirections action = ProfileFragmentDirections.actionProfileFragmentToAuthorizationFragment();
            getActivity().findViewById(R.id.bottomMenu).setVisibility(View.GONE);
            navController.navigate(action);
        });
    }
}