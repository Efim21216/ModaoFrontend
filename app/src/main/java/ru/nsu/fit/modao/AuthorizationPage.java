package ru.nsu.fit.modao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class AuthorizationPage extends AppCompatActivity {

    ImageButton signUp;
    ImageButton logIn;
    EditText login;
    EditText password;
    OkHttpClient client;
    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization_page);

        password = findViewById(R.id.personPassword);
        login = findViewById(R.id.personLogin);
        message = findViewById(R.id.tipMessage);
        logIn = findViewById(R.id.buttonLogIn);
        signUp = findViewById(R.id.buttonSignUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AuthorizationPage.this, RegistrationPage.class);
                startActivity(intent);
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.setText(R.string.wait);
                try {
                    authorization();
                } catch (IOException e){
                    e.printStackTrace();
                }

            }
        });
    }

    void authorization() throws IOException {
        client = new OkHttpClient();
        String ipServer = this.getString(R.string.ipServer);
        String url = "http://" + ipServer + ":8080/user/in";
        String log = login.getText().toString();
        String pass = password.getText().toString();
        if (log.equals("") || pass.equals("")) {
            message.setText(R.string.enterData);
            return;
        }
        String json = "{\"login\" : \"" + log + "\"," +
                " \"password\" : \"" + pass + "\"}";
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
                    int id = Integer.parseInt(response.body().string());
                    Intent intent = new Intent(AuthorizationPage.this, MainActivity.class);
                    intent.putExtra("userID", id);
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
                            message.setText(R.string.incorrectLoginOrPassword);
                        }
                    });
                }

            }
        });
    }
}