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
import ru.nsu.fit.modao.myStorage.HelpFunction;
import ru.nsu.fit.modao.myStorage.MyApplication;

public class AuthorizationPage extends AppCompatActivity {

    ImageButton signUp;
    ImageButton logIn;
    EditText login;
    EditText password;
    OkHttpClient client;
    TextView message;
    final String auto = "auto_authorization";
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization_page);

        password = findViewById(R.id.personPassword);
        login = findViewById(R.id.personLogin);
        message = findViewById(R.id.tipMessage);
        logIn = findViewById(R.id.buttonLogIn);
        signUp = findViewById(R.id.buttonSignUp);
        preferences = getSharedPreferences(auto, MODE_PRIVATE);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpFunction.startNewActivity(AuthorizationPage.this, RegistrationPage.class);
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.setText(R.string.wait);
                String log = login.getText().toString();
                String pass = password.getText().toString();
                try {
                    authorization(log, pass, true);
                } catch (IOException e){
                    e.printStackTrace();
                }

            }
        });
    }

    private void authorization(String log, String pass, boolean showMessage) throws IOException {
        MyApplication app = (MyApplication) AuthorizationPage.this.getApplication();
        if (app.getClient() != null){
            client = app.getClient();
        }
        else {
            client = new OkHttpClient();
            app.setClient(client);
        }
        String url = "http://" + app.getIpServer() + ":8080/user/in";
        if (log.equals("") || pass.equals("")) {
            if (showMessage){
                message.setText(R.string.enterData);
            }
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
                if (!showMessage){
                    return;
                }
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
                    if (showMessage){
                        SharedPreferences.Editor edit = preferences.edit();
                        edit.putString("auto_log", log);
                        edit.putString("auto_pass", pass);
                        edit.apply();
                    }
                    app.setUserID(id);
                    Intent intent = new Intent(AuthorizationPage.this, MainActivity.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(intent);
                        }
                    });
                } else {
                    if (!showMessage){
                        return;
                    }
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