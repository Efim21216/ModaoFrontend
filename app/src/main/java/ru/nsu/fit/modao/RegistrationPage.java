package ru.nsu.fit.modao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ru.nsu.fit.modao.repository.MyApplication;

public class RegistrationPage extends AppCompatActivity {

    ImageButton cont;
    TextView message;
    OkHttpClient client;
    EditText login;
    EditText password;
    EditText username;
    final String auto = "auto_authorization";
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        cont = findViewById(R.id.buttonContinue);
        message = findViewById(R.id.tipMessage);
        username = findViewById(R.id.createUsername);
        login = findViewById(R.id.createLogin);
        password = findViewById(R.id.createPassword);
        preferences = getSharedPreferences(auto, MODE_PRIVATE);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.setText(R.string.wait);
                try{
                    registration();
                } catch (IOException e){
                    e.printStackTrace();
                }

            }
        });
    }
    void registration() throws IOException {
        MyApplication app = (MyApplication) RegistrationPage.this.getApplication();
        if (app.getClient() != null){
            client = app.getClient();
        }
        else {
            client = new OkHttpClient();
            app.setClient(client);
        }
        String url = "http://" + app.getIpServer() + ":8080/user";
        String log = login.getText().toString();
        String pass = password.getText().toString();
        String name = username.getText().toString();
        if (log.equals("") || pass.equals("") || name.equals("")) {
            message.setText(R.string.enterData);
            return;
        }
        String json = "{\"login\" : \"" + log + "\"," +
                " \"password\" : \"" + pass + "\"," +
                " \"username\" : \"" + name + "\"}";
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
                    long id = Integer.parseInt(response.body().string());
                    Intent intent = new Intent(RegistrationPage.this, MainActivity.class);
                    MyApplication app = (MyApplication) RegistrationPage.this.getApplication();
                    app.setUserID(id);
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putString("auto_log", log);
                    edit.putString("auto_pass", pass);
                    edit.apply();
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
                            message.setText(R.string.alreadyExist);
                        }
                    });
                }

            }
        });
    }
}