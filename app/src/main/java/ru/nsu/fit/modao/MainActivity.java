package ru.nsu.fit.modao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ru.nsu.fit.modao.repository.HelpFunction;
import ru.nsu.fit.modao.repository.MyApplication;


public class MainActivity extends AppCompatActivity {
    ImageButton expensesButton;
    ImageButton groupsButton;
    TextView name;
    TextView phone;
    TextView bank;
    OkHttpClient client;
    LinearLayout logOut;
    final String auto = "auto_authorization";
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expensesButton = findViewById(R.id.buttonExpenses);
        name = findViewById(R.id.personName);
        phone = findViewById(R.id.personPhone);
        bank = findViewById(R.id.personBank);
        logOut = findViewById(R.id.logOutLayout);
        preferences = getSharedPreferences(auto, MODE_PRIVATE);

        try {
            getDataPerson();
        } catch (IOException e) {
            e.printStackTrace();
        }

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor edit = preferences.edit();
                edit.remove("auto_log");
                edit.remove("auto_pass");
                edit.apply();
                HelpFunction.startNewActivity(MainActivity.this, AuthorizationPage.class);
            }
        });

        expensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelpFunction.startNewActivity(MainActivity.this, ExpensesActivity.class);
            }
        });

        groupsButton = findViewById(R.id.buttonGroups);
        groupsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpFunction.startNewActivity(MainActivity.this, GroupsActivity.class);
            }
        });
    }
    void getDataPerson() throws IOException {
        MyApplication app = (MyApplication) MainActivity.this.getApplication();
        client = app.getClient();
        String url = "http://" + app.getIpServer() + ":8080/user/" + app.getUserID();
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