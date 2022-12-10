package ru.nsu.fit.modao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import ru.nsu.fit.modao.adapter.ExpensesAdapter;
import ru.nsu.fit.modao.model.Currency;
import ru.nsu.fit.modao.model.Expenses;
import ru.nsu.fit.modao.myStorage.HelpFunction;

public class ExpensesActivity extends AppCompatActivity {
    ImageButton account;
    ImageButton groups;
    RecyclerView expensesRecycler;
    ExpensesAdapter expensesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        groups = findViewById(R.id.buttonGroups);
        account = findViewById(R.id.buttonAccount);
        List<Expenses> expensesList = new ArrayList<>();
        expensesList.add(new Expenses(-100, "Nikita paid", "Nikita Paid", Currency.RUB));
        expensesList.add(new Expenses(-900, "Nikita paid", "Nikita Paid", Currency.RUB));
        expensesList.add(new Expenses(1200, "You paid", "You paid", Currency.RUB));
        expensesList.add(new Expenses(100, "Olga paid", "Olga Paid", Currency.RUB));

        setExpensesRecycler(expensesList);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelpFunction.startNewActivity(ExpensesActivity.this, MainActivity.class);
            }
        });
        groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpFunction.startNewActivity(ExpensesActivity.this, GroupsActivity.class);
            }
        });
    }

    private void setExpensesRecycler(List<Expenses> expensesList) {
        // Настройки для recycler
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        expensesRecycler = findViewById(R.id.expensesRecycler);
        // Установить настройки
        expensesRecycler.setLayoutManager(layoutManager);
        // Вся логика подстановки, которую писали раньше
        expensesAdapter = new ExpensesAdapter(this, expensesList);
        // Установка адаптера
        expensesRecycler.setAdapter(expensesAdapter);
    }
}