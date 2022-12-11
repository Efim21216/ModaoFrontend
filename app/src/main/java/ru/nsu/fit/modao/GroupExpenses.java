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

public class GroupExpenses extends AppCompatActivity {
    ImageButton account;
    ImageButton groups;
    ImageButton expenses;
    ImageButton addExpenses;
    RecyclerView expensesRecycler;
    ExpensesAdapter expensesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_expenses);

        groups = findViewById(R.id.buttonGroups);
        account = findViewById(R.id.buttonAccount);
        expenses = findViewById(R.id.buttonExpenses);
        addExpenses = findViewById(R.id.buttonAddExpense);

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelpFunction.startNewActivity(GroupExpenses.this, MainActivity.class);
            }
        });
        groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpFunction.startNewActivity(GroupExpenses.this, GroupsActivity.class);
            }
        });
        expenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpFunction.startNewActivity(GroupExpenses.this, ExpensesActivity.class);
            }
        });
        addExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpFunction.startNewActivity(GroupExpenses.this, SelectParticipants.class);
            }
        });
        List<Expenses> expensesList = new ArrayList<>();
        expensesList.add(new Expenses(-50, "Nikita paid", "Nikita Paid", Currency.RUB));
        expensesList.add(new Expenses(-90, "Nikita paid", "Nikita Paid", Currency.RUB));
        expensesList.add(new Expenses(120, "You paid", "You paid", Currency.RUB));
        expensesList.add(new Expenses(10, "Olga paid", "Olga Paid", Currency.RUB));

        setExpensesRecycler(expensesList);
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