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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.nsu.fit.modao.databinding.FragmentRegistrationBinding;
import ru.nsu.fit.modao.model.NewUser;
import ru.nsu.fit.modao.repository.LoginViewModel;
import ru.nsu.fit.modao.repository.MyApplication;


public class RegistrationFragment extends Fragment {

    FragmentRegistrationBinding binding;
    LoginViewModel loginViewModel;
    BottomNavigationView bottomMenu;
    NavController navController;
    MyApplication app;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
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

        navController = Navigation.findNavController(view);
        bottomMenu = getActivity().findViewById(R.id.bottomMenu);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        app = (MyApplication) getActivity().getApplication();
        loginViewModel.getUserIn().observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long id) {
                if (id == -1L){
                    binding.tipMessage.setText(R.string.alreadyExist);
                    return;
                }
                if (id == -2L){
                    binding.tipMessage.setText(R.string.serverProblems);
                    return;
                }
                app.setUserID(id);
                bottomMenu.setVisibility(View.VISIBLE);
                NavDirections action = RegistrationFragmentDirections.actionRegistrationFragmentToProfileFragment();
                navController.navigate(action);
            }
        });
        binding.buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String log = binding.createLogin.getText().toString();
                String pass = binding.createPassword.getText().toString();
                if (log.equals("") || pass.equals("")){
                    binding.tipMessage.setText(R.string.enterData);
                    return;
                }
                loginViewModel.createUser(app.getQuestApi(), new NewUser(binding.createUsername.getText().toString(),
                        log, pass, null, null, null));
            }
        });
    }
}