package com.malviya.rangoliproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.malviya.rangoliproject.R;
import com.malviya.rangoliproject.adapter.MyCustomAdapter;
import com.malviya.rangoliproject.constants.ConstantMessage;
import com.malviya.rangoliproject.image_data_store.Data;
import com.malviya.rangoliproject.main.GameCanvas;
import com.malviya.rangoliproject.utilies.Utilities;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    MyCustomAdapter adapter;
    FloatingActionButton mFloatingBtn;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addToolbar(View.VISIBLE);
        mFloatingBtn = (FloatingActionButton) findViewById(R.id.floatingBtn);
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);

        adapter = new MyCustomAdapter(this, Data.getData());
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Vertical Orientation By Default
        mFloatingBtn.setOnClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    //Add toolbar
    private void addToolbar(int view) {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setVisibility(view);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.festivalSpecial:
                Intent intent1 = new Intent(this, FestivalSpecialRangoliMainActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);
                break;
            case R.id.dotPattern:
                Intent intent2 = new Intent(this, DotRangoliMainActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent2);
                break;
            case R.id.flowerPattern:
                Intent intent3 = new Intent(this, FlowerRangoliMainActivity.class);
                intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent3);
                break;
            case R.id.gameCanvas:
                startActivity(new Intent(MainActivity.this, GameCanvas.class));
                break;
            case R.id.shareButton:
                Utilities.shareWishToAll(this, ConstantMessage.IMAGE,
                        ConstantMessage.MESSAGE, ConstantMessage.HYPERLINK);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatingBtn:
                Intent intent = new Intent(this, GameCanvas.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }
}
