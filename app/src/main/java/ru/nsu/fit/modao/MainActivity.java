package ru.nsu.fit.modao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.io.IOException;

import ru.nsu.fit.modao.adapter.GroupAdapter;

public class MainActivity extends AppCompatActivity {
    ImageButton expensesButton;
    ImageButton groupsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expensesButton = findViewById(R.id.buttonExpenses);
        expensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewActivity(ExpensesActivity.class);
            }
        });

        groupsButton = findViewById(R.id.buttonGroups);
        groupsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(GroupsActivity.class);
            }
        });
    }
    void startNewActivity(Class<?> cls){
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}