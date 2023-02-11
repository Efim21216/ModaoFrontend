package ru.nsu.fit.modao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import ru.nsu.fit.modao.repository.HelpFunction;

public class GroupInfo extends AppCompatActivity {
    ImageButton account;
    ImageButton groups;
    ImageButton expenses;
    ImageButton groupExpenses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);

        groupExpenses = findViewById(R.id.imageButtonExpenses);
        groups = findViewById(R.id.buttonGroups);
        account = findViewById(R.id.buttonAccount);
        expenses = findViewById(R.id.buttonExpenses);
        TextView name = findViewById(R.id.nameGroup);
        name.setText(getIntent().getStringExtra("nameGroup"));
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelpFunction.startNewActivity(GroupInfo.this, MainActivity.class);
            }
        });
        groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpFunction.startNewActivity(GroupInfo.this, GroupsActivity.class);
            }
        });
        expenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpFunction.startNewActivity(GroupInfo.this, ExpensesActivity.class);
            }
        });
        groupExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpFunction.startNewActivity(GroupInfo.this, GroupExpenses.class);
            }
        });
    }
}