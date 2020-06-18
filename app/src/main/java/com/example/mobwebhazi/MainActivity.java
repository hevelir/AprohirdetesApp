package com.example.mobwebhazi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.mobwebhazi.adapter.MyAdapter;
import com.example.mobwebhazi.data.Item;
import com.example.mobwebhazi.data.ItemListDatabase;
import com.example.mobwebhazi.fragment.DetailsDialogFragment;
import com.example.mobwebhazi.fragment.NewItemDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NewItemDialogFragment.NewItemDialogListener,
        MyAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private int ordered = 0;
    private ItemListDatabase database;
    private boolean others = false;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton order = findViewById(R.id.order);
        FloatingActionButton fab = findViewById(R.id.fab);
        username = getIntent().getStringExtra("username");

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ordered ==2) ordered = 0;
                else ordered++;
                initRecyclerView();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewItemDialogFragment fragm = new NewItemDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("uname", username);
                fragm.setArguments(bundle);
                fragm.show(getSupportFragmentManager(), NewItemDialogFragment.TAG);
            }
        });

        database = Room.databaseBuilder(
                getApplicationContext(),
                ItemListDatabase.class,
                "list"
        ).build();

        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.MainRecyclerView);
        adapter = new MyAdapter(this);
        loadMyItemsInBackground();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @SuppressLint("StaticFieldLeak")
    private void loadMyItemsInBackground() {
        new AsyncTask<Void, Void, List<Item>>() {

            @Override
            protected List<Item> doInBackground(Void... voids) {
                if(others) {
                    if (ordered == 0)
                        return database.ItemDao().getOthers(username);
                    if(ordered == 1)
                        return database.ItemDao().getOthersAscending(username);
                    if (ordered == 2)
                        return database.ItemDao().getOthersDescending(username);
                }
                if(!others) {
                    if(ordered == 1)
                        return database.ItemDao().getUserItemsAscending(username);
                    if (ordered == 2)
                        return database.ItemDao().getUserItemsDescending(username);
                }
                return database.ItemDao().getUserItems(username);
            }

            @Override
            protected void onPostExecute(List<Item> Items) {
                adapter.update(Items);
            }
        }.execute();
    }

    @Override
    public void detailsClicked(Item item) {
        DetailsDialogFragment fragm = new DetailsDialogFragment(item);
        fragm.show(getSupportFragmentManager(), NewItemDialogFragment.TAG);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onItemChanged(final Item item) {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                database.ItemDao().update(item);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean isSuccessful) {
                Log.d("MainActivity", "Item update was successful");
            }
        }.execute();
    }



    @SuppressLint("StaticFieldLeak")
    @Override
    public void onDelete(final Item item) {
            adapter.remove(item);
            new AsyncTask<Void, Void, Boolean>() {

                @Override
                protected Boolean doInBackground(Void... voids) {
                    database.ItemDao().deleteItem(item);
                    return true;
                }

                @Override
                protected void onPostExecute(Boolean isSuccessful) {
                    Log.d("MainActivity", "ShoppingItem update was successful");
                }
            }.execute();
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public void onItemCreated(final Item newItem) {
        new AsyncTask<Void, Void, Item>() {

            @Override
            protected Item doInBackground(Void... voids) {
                newItem.id = database.ItemDao().insert(newItem);
                return newItem;
            }

            @Override
            protected void onPostExecute(Item Item) {
                adapter.addItem(Item);
            }
        }.execute();
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

        if (id == R.id.other_items) {
            others = true;
            initRecyclerView();
            // return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.my_items) {
            others = false;
            initRecyclerView();
           // return true;
        }

        if (id == R.id.logout) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            // return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
