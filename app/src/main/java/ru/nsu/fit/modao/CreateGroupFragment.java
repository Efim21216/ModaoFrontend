package ru.nsu.fit.modao;

import android.os.Binder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.navigation.NavigationView;

import ru.nsu.fit.modao.databinding.FragmentCreateGroupBinding;
import ru.nsu.fit.modao.model.Group;
import ru.nsu.fit.modao.repository.MainViewModel;
import ru.nsu.fit.modao.repository.MyApplication;

public class CreateGroupFragment extends Fragment {
    FragmentCreateGroupBinding binding;
    MainViewModel mainViewModel;
    MyApplication app;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreateGroupBinding.inflate(inflater, container, false);
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
        app = (MyApplication) getActivity().getApplication();
        mainViewModel.getNewGroup().observe(getViewLifecycleOwner(), new Observer<Group>() {
            @Override
            public void onChanged(Group group) {
                if (group.getId() == -1L){
                    binding.tipMessage.setText(R.string.incorrectData);
                }
                else if (group.getId() == -2L){
                    binding.tipMessage.setText(R.string.serverProblems);
                }
                else {
                    NavDirections action = CreateGroupFragmentDirections.actionCreateGroupFragmentToGroupInfoFragment(group);
                    Navigation.findNavController(view).navigate(action);
                }
            }
        });
        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.nameText.getText().toString();
                String description = binding.descriptionText.getText().toString();
                if (name.equals("") || description.equals("")){
                    binding.tipMessage.setText(R.string.enterData);
                    return;
                }
                mainViewModel.createGroup(app.getQuestApi(),
                        new Group(name, null, description, 0), app.getUserID());
            }
        });
    }
}