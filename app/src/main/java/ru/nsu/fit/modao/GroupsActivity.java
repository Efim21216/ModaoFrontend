package ru.nsu.fit.modao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.LinkedList;
import java.util.List;

import ru.nsu.fit.modao.adapter.GroupAdapter;
import ru.nsu.fit.modao.model.Groups;

public class GroupsActivity extends AppCompatActivity {
    RecyclerView groupsRecycler;
    GroupAdapter groupAdapter;
    ImageButton account;
    ImageButton expenses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        List<Groups> groups = new LinkedList<>();
        groups.add(new Groups("Picnic"));
        groups.add(new Groups("Company party"));
        groups.add(new Groups("Matinee"));

        setGroupsRecycler(groups);
        account = findViewById(R.id.buttonAccountG);
        expenses = findViewById(R.id.buttonExpenses);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewActivity(MainActivity.class);
            }
        });
        expenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(ExpensesActivity.class);
            }
        });
    }

    private void setGroupsRecycler(List<Groups> groupsList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        groupsRecycler = findViewById(R.id.groupsRecycler);
        groupsRecycler.setLayoutManager(layoutManager);
        groupAdapter = new GroupAdapter(this, groupsList);
        groupsRecycler.setAdapter(groupAdapter);
    }
    void startNewActivity(Class<?> cls){
        Intent intent = new Intent(this, cls);
        intent.putExtra("userID", getIntent().getIntExtra("userID", 0));
        startActivity(intent);
    }
}