package ru.nsu.fit.modao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    ImageButton expensesButton;
    ImageButton groupsButton;
    TextView name;
    TextView phone;
    TextView bank;
    OkHttpClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expensesButton = findViewById(R.id.buttonExpenses);
        name = findViewById(R.id.personName);
        phone = findViewById(R.id.personPhone);
        bank = findViewById(R.id.personBank);
        try {
            getDataPerson();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        intent.putExtra("userID", getIntent().getIntExtra("userID", 0));
        startActivity(intent);
    }

    void getDataPerson() throws IOException {
        client = new OkHttpClient();
        String ipServer = this.getString(R.string.ipServer);
        String url = "http://" + ipServer + ":8080/user/" + getIntent().getIntExtra("userID", 0);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                call.cancel();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject json = new JSONObject(response.body().string());
                        String n = json.getString("username");
                        String p = json.getString("phone_number");
                        String b = json.getString("bank");
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                name.setText(n);
                                phone.setText(p);
                                bank.setText(b);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}