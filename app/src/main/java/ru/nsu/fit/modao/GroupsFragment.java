package ru.nsu.fit.modao;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import ru.nsu.fit.modao.adapter.GroupAdapter;
import ru.nsu.fit.modao.databinding.FragmentGroupsBinding;
import ru.nsu.fit.modao.model.Group;
import ru.nsu.fit.modao.model.User;
import ru.nsu.fit.modao.repository.MainViewModel;
import ru.nsu.fit.modao.repository.MyApplication;


public class GroupsFragment extends Fragment {

    FragmentGroupsBinding binding;
    GroupAdapter groupAdapter;
    MyApplication app;
    MainViewModel mainViewModel;
    List<Group> groups = new LinkedList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGroupsBinding.inflate(inflater, container, false);
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

        mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.groupsRecycler.setLayoutManager(layoutManager);
        groups = mainViewModel.getUserLive().getValue().getGroups();
        groupAdapter = new GroupAdapter(getContext(), groups);
        binding.groupsRecycler.setAdapter(groupAdapter);
        app = (MyApplication) getActivity().getApplication();
        mainViewModel.getUserLive().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                groups = user.getGroups();
                groupAdapter.notifyDataSetChanged();
            }
        });
        binding.buttonAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = GroupsFragmentDirections.actionGroupsFragmentToCreateGroupFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });
    }
}