package com.example.nn0lumesther.javadev.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nn0lumesther.javadev.R;
import com.example.nn0lumesther.javadev.adapter.DeveloperAdapter;
import com.example.nn0lumesther.javadev.model.DeveloperList;
import com.example.nn0lumesther.javadev.network.APIError;
import com.example.nn0lumesther.javadev.network.Client;
import com.example.nn0lumesther.javadev.network.ErrorUtils;
import com.example.nn0lumesther.javadev.network.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final Service API_INTERFACE = Client.getClient().create(Service.class);
    private RecyclerView developerRecyclerView;
    private ProgressBar mProgressBar;
    private DeveloperAdapter mAdapter;
    private DeveloperList developerList;
    private TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        developerRecyclerView = (RecyclerView) findViewById(R.id.developers_rv);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        errorText = (TextView) findViewById(R.id.error_text);
        developerRecyclerView.setHasFixedSize(true);

        getJavaDevelopers();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBar.setVisibility(View.VISIBLE);
                getJavaDevelopers();
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putParcelableArrayList(DEVELOPER_KEY, developerList.getItems());
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
        String searchFilter = "language:java location:lagos";
        Call<DeveloperList> call = API_INTERFACE.getDevsInLagos(searchFilter);
        call.enqueue(new Callback<DeveloperList>() {
            @Override
            public void onResponse(Call<DeveloperList> call, Response<DeveloperList> response) {
                if (response.isSuccessful()) {
                    errorText.setVisibility(View.GONE);
                    mProgressBar.setVisibility(View.GONE);
                    developerList = response.body();
                    loadData(developerList);

                    Log.d("TAG", "Request Successful");
                } else {
                    APIError error = ErrorUtils.parseError(response);

                    Log.d("TAG", error.message());
                }
            }

            @Override
            public void onFailure(Call<DeveloperList> call, Throwable t) {

                mProgressBar.setVisibility(View.INVISIBLE);
                errorText.setVisibility(View.VISIBLE);

                t.printStackTrace();
            }
        });

    }

    private void loadData(DeveloperList developerList) {
        mAdapter = new DeveloperAdapter(MainActivity.this, developerList.getItems());
        developerRecyclerView.setAdapter(mAdapter);
    }
}