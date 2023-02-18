package ru.nsu.fit.modao.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.nsu.fit.modao.adapter.ExpensesAdapter;
import ru.nsu.fit.modao.databinding.FragmentExpensesBinding;
import ru.nsu.fit.modao.model.Currency;
import ru.nsu.fit.modao.model.Expenses;

public class ExpensesFragment extends Fragment {

    FragmentExpensesBinding binding;
    ExpensesAdapter expensesAdapter;
    List<Expenses> expensesList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        expensesList.add(new Expenses(-100, "Nikita paid", "Nikita Paid", Currency.RUB));
        expensesList.add(new Expenses(-900, "Nikita paid", "Nikita Paid", Currency.RUB));
        expensesList.add(new Expenses(1200, "You paid", "You paid", Currency.RUB));
        expensesList.add(new Expenses(100, "Olga paid", "Olga Paid", Currency.RUB));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentExpensesBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.expensesRecycler.setLayoutManager(layoutManager);
        expensesAdapter = new ExpensesAdapter(getContext(), expensesList);
        binding.expensesRecycler.setAdapter(expensesAdapter);
    }
}