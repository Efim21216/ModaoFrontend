package ru.nsu.fit.modao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ru.nsu.fit.modao.adapter.GroupAdapter;
import ru.nsu.fit.modao.model.Group;
import ru.nsu.fit.modao.repository.HelpFunction;
import ru.nsu.fit.modao.repository.MyApplication;

public class GroupsActivity extends AppCompatActivity {
    RecyclerView groupsRecycler;
    GroupAdapter groupAdapter;
    ImageButton account;
    ImageButton expenses;
    ImageButton addGroup;
    List<Group> groups;
    OkHttpClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        groups = new LinkedList<>();
        setGroupsRecycler(groups);
        try {
            getDataPerson();
        } catch (IOException e) {
            e.printStackTrace();
        }

        addGroup = findViewById(R.id.buttonAddGroup);
        account = findViewById(R.id.buttonAccount);
        expenses = findViewById(R.id.buttonExpenses);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelpFunction.startNewActivity(GroupsActivity.this, MainActivity.class);
            }
        });
        expenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpFunction.startNewActivity(GroupsActivity.this, ExpensesActivity.class);
            }
        });
        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpFunction.startNewActivity(GroupsActivity.this, CreateGroup.class);
            }
        });
    }

    private void setGroupsRecycler(List<Group> groupList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        groupsRecycler = findViewById(R.id.groupsRecycler);
        groupsRecycler.setLayoutManager(layoutManager);
        groupAdapter = new GroupAdapter(this, groupList);
        groupsRecycler.setAdapter(groupAdapter);
    }

    void getDataPerson() throws IOException {
        MyApplication app = (MyApplication) GroupsActivity.this.getApplication();
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
                        JSONArray arr = json.getJSONArray("groups");
                        groups.clear();
                        for (int i = 0; i < arr.length(); i++){
                            JSONObject ob = (JSONObject) arr.get(i);
                            groups.add(new Group(ob.getString("name"), ob.getLong("id")));
                        }
                        GroupsActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setGroupsRecycler(groups);
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