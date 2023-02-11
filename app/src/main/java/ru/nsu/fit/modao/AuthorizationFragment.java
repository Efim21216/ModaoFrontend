package ru.nsu.fit.modao;

import android.content.IntentFilter;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.nsu.fit.modao.databinding.FragmentAuthorizationBinding;
import ru.nsu.fit.modao.model.UserIn;
import ru.nsu.fit.modao.repository.LoginViewModel;
import ru.nsu.fit.modao.repository.MyApplication;


public class AuthorizationFragment extends Fragment {
    LoginViewModel loginViewModel;
    MyApplication app;
    BottomNavigationView bottomMenu;
    NavController navController;
    FragmentAuthorizationBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAuthorizationBinding.inflate(inflater, container, false);
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

        bottomMenu = getActivity().findViewById(R.id.bottomMenu);
        navController = Navigation.findNavController(view);
        app = (MyApplication) getActivity().getApplication();
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getUserIn().observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long id) {
                if (id == -1L){
                    binding.tipMessage.setText(R.string.incorrectLoginOrPassword);
                    return;
                }
                if (id == -2L){
                    binding.tipMessage.setText(R.string.serverProblems);
                    return;
                }
                app.setUserID(id);
                bottomMenu.setVisibility(View.VISIBLE);
                NavDirections action = AuthorizationFragmentDirections.actionAuthorizationFragmentToProfileFragment();
                navController.navigate(action);
            }
        });
        binding.buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String log = binding.personLogin.getText().toString();
                String pass = binding.personPassword.getText().toString();
                if (log.equals("") || pass.equals("")){
                    binding.tipMessage.setText(R.string.enterData);
                    return;
                }
                binding.tipMessage.setText(R.string.wait);
                loginViewModel.requestLogin(app.getQuestApi(),
                        new UserIn(log, pass));
            }
        });

        binding.buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = AuthorizationFragmentDirections.actionAuthorizationFragmentToRegistrationFragment();
                navController.navigate(action);
            }
        });
    }
}