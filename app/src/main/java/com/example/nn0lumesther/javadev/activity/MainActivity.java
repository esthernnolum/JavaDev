package com.example.nn0lumesther.javadev.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.nn0lumesther.javadev.R;
import com.example.nn0lumesther.javadev.adapter.DeveloperAdapter;
import com.example.nn0lumesther.javadev.model.Developer;
import com.example.nn0lumesther.javadev.network.Client;
import com.example.nn0lumesther.javadev.network.Service;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView developerRecyclerView;
    private DeveloperAdapter mAdapter;
    private static final Service API_INTERFACE = Client.getClient().create(Service.class);
    private ArrayList<Developer> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        developerRecyclerView = (RecyclerView) findViewById(R.id.developers_rv);
        mList = new ArrayList<>();
        getJavaDevelopers();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJavaDevelopers();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getJavaDevelopers() {
        Call<ArrayList<Developer>> call = API_INTERFACE.getDevsInLagos("language:java+location:lagos&order=asc");
        call.enqueue(new Callback<ArrayList<Developer>>() {
            @Override
            public void onResponse(Call<ArrayList<Developer>> call, Response<ArrayList<Developer>> response) {
                mList = response.body();
                loadData();
                Toast.makeText( MainActivity.this, "Was successful but view issue", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ArrayList<Developer>> call, Throwable t) {
                Toast.makeText( MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadData() {
        mAdapter = new DeveloperAdapter(MainActivity.this, mList);
        //mAdapter.notifyDataSetChanged();
        developerRecyclerView.setAdapter(mAdapter);
    }
}
