package ru.nsu.fit.modao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ru.nsu.fit.modao.repository.HelpFunction;
import ru.nsu.fit.modao.repository.MyApplication;

public class CreateGroup extends AppCompatActivity {
    ImageButton account;
    ImageButton groups;
    ImageButton expenses;
    ImageButton next;
    TextView message;
    TextInputEditText description;
    TextInputEditText nameText;
    OkHttpClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        groups = findViewById(R.id.buttonGroups);
        account = findViewById(R.id.buttonAccount);
        expenses = findViewById(R.id.buttonExpenses);
        description = findViewById(R.id.descriptionText);
        nameText = findViewById(R.id.nameText);
        message = findViewById(R.id.tipMessage);
        next = findViewById(R.id.buttonNext);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelpFunction.startNewActivity(CreateGroup.this, MainActivity.class);
            }
        });
        groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpFunction.startNewActivity(CreateGroup.this, GroupsActivity.class);
            }
        });
        expenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpFunction.startNewActivity(CreateGroup.this, ExpensesActivity.class);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.setText(R.string.wait);
                try {
                    createGroup();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    void createGroup() throws IOException {
        MyApplication app = (MyApplication) CreateGroup.this.getApplication();
        client = app.getClient();
        String name = nameText.getText().toString();
        if (name.equals("")){
            message.setText(R.string.enterData);
            return;
        }
        String url = "http://" + app.getIpServer() + ":8080/group/" + app.getUserID();
        String json = "{\"groupName\" : \"" + name + "\"," +
                " \"typeGroup\" : 0," +
                " \"description\" : \"" + description.getText().toString() + "\"}";
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                call.cancel();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        message.setText(R.string.serverProblems);
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(CreateGroup.this, GroupsActivity.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(intent);
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            message.setText(R.string.incorrectData);
                        }
                    });
                }
            }
        });
    }
}