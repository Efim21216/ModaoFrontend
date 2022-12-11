package ru.nsu.fit.modao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

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

public class StartActivity extends AppCompatActivity {
    SharedPreferences preferences;
    final String auto = "auto_authorization";
    OkHttpClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        HelpFunction.delay(2, new HelpFunction.DelayCallback() {
            @Override
            public void afterDelay() {
                init();
            }
        });
    }
    private void init(){
        preferences = getSharedPreferences(auto, MODE_PRIVATE);
        if (preferences.contains("auto_log")){
            String login = preferences.getString("auto_log", "");
            String password = preferences.getString("auto_pass", "");
            try {
                authorization(login, password);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            HelpFunction.startNewActivity(StartActivity.this, AuthorizationPage.class);
        }

    }
    private void authorization(String log, String pass) throws IOException {
        MyApplication app = (MyApplication) StartActivity.this.getApplication();
        client = new OkHttpClient();
        app.setClient(client);
        String url = "http://" + app.getIpServer() + ":8080/user/in";
        if (log.equals("") || pass.equals("")) {
            HelpFunction.startNewActivity(StartActivity.this, AuthorizationPage.class);
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
                HelpFunction.startNewActivity(StartActivity.this, AuthorizationPage.class);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    long id = Integer.parseInt(response.body().string());
                    app.setUserID(id);
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
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
                            HelpFunction.startNewActivity(StartActivity.this, AuthorizationPage.class);
                        }
                    });
                }

            }
        });
    }
}