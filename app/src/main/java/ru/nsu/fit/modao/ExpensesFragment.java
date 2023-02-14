package ru.nsu.fit.modao;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.nsu.fit.modao.adapter.ExpensesAdapter;
import ru.nsu.fit.modao.databinding.FragmentExpensesBinding;
import ru.nsu.fit.modao.model.Currency;
import ru.nsu.fit.modao.model.Expenses;
import ru.nsu.fit.modao.model.User;
import ru.nsu.fit.modao.repository.MainViewModel;
import ru.nsu.fit.modao.repository.MyApplication;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpensesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpensesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentExpensesBinding binding;
    ExpensesAdapter expensesAdapter;
    List<Expenses> expensesList = new ArrayList<>();

    public ExpensesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExpensesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExpensesFragment newInstance(String param1, String param2) {
        ExpensesFragment fragment = new ExpensesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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