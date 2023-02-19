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
import ru.nsu.fit.modao.adapter.ExpensesAdapter;
import ru.nsu.fit.modao.databinding.FragmentExpensesBinding;
import ru.nsu.fit.modao.databinding.FragmentGroupExpensesBinding;
import ru.nsu.fit.modao.model.Currency;
import ru.nsu.fit.modao.model.Expenses;


public class GroupExpensesFragment extends Fragment {
    FragmentGroupExpensesBinding binding;
    List<Expenses> expenses;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGroupExpensesBinding.inflate(inflater, container, false);
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

        expenses = new LinkedList<>();
        expenses.add(new Expenses(-100, "Nikita paid", "Nikita Paid", Currency.RUB));
        expenses.add(new Expenses(-900, "Nikita paid", "Nikita Paid", Currency.RUB));
        expenses.add(new Expenses(1200, "You paid", "You paid", Currency.RUB));
        expenses.add(new Expenses(100, "Olga paid", "Olga Paid", Currency.RUB));


        ExpensesAdapter adapter = new ExpensesAdapter(getContext(), expenses);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.expensesRecycler.setLayoutManager(layoutManager);
        binding.expensesRecycler.setAdapter(adapter);
        binding.buttonAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = GroupExpensesFragmentDirections.actionGroupExpensesFragmentToEnterCostFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });
    }
}