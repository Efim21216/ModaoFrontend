package ru.nsu.fit.modao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.LinkedList;
import java.util.List;

import ru.nsu.fit.modao.adapter.ParticipantsAdapter;
import ru.nsu.fit.modao.repository.HelpFunction;

public class SelectParticipants extends AppCompatActivity {
    ImageButton account;
    ImageButton groups;
    ImageButton expenses;
    ImageButton next;
    RecyclerView participantsRecycler;
    ParticipantsAdapter participantsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_participants);

        groups = findViewById(R.id.buttonGroups);
        account = findViewById(R.id.buttonAccount);
        expenses = findViewById(R.id.buttonExpenses);
        next = findViewById(R.id.buttonNext);

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelpFunction.startNewActivity(SelectParticipants.this, MainActivity.class);
            }
        });
        groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpFunction.startNewActivity(SelectParticipants.this, GroupsActivity.class);
            }
        });
        expenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpFunction.startNewActivity(SelectParticipants.this, ExpensesActivity.class);
            }
        });
        List<String> participants = new LinkedList<>();
        participants.add("Olga");
        participants.add("Peter");
        participants.add("Nikita");
        participants.add("Efim");
        setParticipantsRecycler(participants);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpFunction.startNewActivity(SelectParticipants.this, EnterCostPage.class);
            }
        });

    }
    private void setParticipantsRecycler(List<String> list){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        participantsRecycler = findViewById(R.id.participantRecycler);
        participantsRecycler.setLayoutManager(layoutManager);
        participantsAdapter = new ParticipantsAdapter(this, list);
        participantsRecycler.setAdapter(participantsAdapter);
    }
}