package ru.nsu.fit.modao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import ru.nsu.fit.modao.repository.HelpFunction;

public class EnterCostPage extends AppCompatActivity {

    ImageButton expensesButton;
    ImageButton groupsButton;
    ImageButton accountButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_cost_page);

        expensesButton = findViewById(R.id.buttonExpenses);
        expensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelpFunction.startNewActivity(EnterCostPage.this, ExpensesActivity.class);
            }
        });

        groupsButton = findViewById(R.id.buttonGroups);
        groupsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpFunction.startNewActivity(EnterCostPage.this, GroupsActivity.class);
            }
        });
        accountButton = findViewById(R.id.buttonAccount);
        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpFunction.startNewActivity(EnterCostPage.this, MainActivity.class);
            }
        });
    }
}