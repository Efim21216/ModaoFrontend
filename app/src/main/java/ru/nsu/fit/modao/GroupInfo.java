package ru.nsu.fit.modao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class GroupInfo extends AppCompatActivity {
    ImageButton account;
    ImageButton groups;
    ImageButton expenses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);

        groups = findViewById(R.id.buttonGroups);
        account = findViewById(R.id.buttonAccount);
        expenses = findViewById(R.id.buttonExpenses);
        TextView name = findViewById(R.id.nameGroup);
        name.setText(getIntent().getStringExtra("nameGroup"));
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewActivity(MainActivity.class);
            }
        });
        groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(GroupsActivity.class);
            }
        });
        expenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(ExpensesActivity.class);
            }
        });
    }
    void startNewActivity(Class<?> cls){
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}