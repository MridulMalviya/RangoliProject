package com.malviya.rangoliproject.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.malviya.rangoliproject.image_data_store.DataFlowerRangoli;
import com.malviya.rangoliproject.adapter.FlowerRangoliAdapter;
import com.malviya.rangoliproject.R;
import com.malviya.rangoliproject.main.GameCanvas;

import java.io.File;

/**
 * Created by Prafulla on 10/26/2016.
 */

public class FlowerRangoliMainActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    FlowerRangoliAdapter adapter;
    FloatingActionButton mFloattingBtn;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addToolbar(View.VISIBLE);
        mFloattingBtn = (FloatingActionButton) findViewById(R.id.floatingBtn);
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        adapter = new FlowerRangoliAdapter(this, DataFlowerRangoli.getData());
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Vertical Orientation
        mFloattingBtn.setOnClickListener(this);
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
            case R.id.normalPattern:
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.dotPattern:
                Intent intent1 = new Intent(this, DotRangoliMainActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);
                break;
        /*    case R.id.flowerPattern:
                Intent intent2 = new Intent(this,FlowerRangoliMainActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent2);
                break;*/
            case R.id.gameCanvas:
                startActivity(new Intent(this, GameCanvas.class));
                break;
            case R.id.shareButton:
                getShareData("Subject", "Hello", "rangoli3.jpg");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Returns a share intent
     */
    private void getShareData(String subject, String shareText, String shareImg) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        sharingIntent.setType("text/plain");
        sharingIntent.setType("image/*");

        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        if (shareImg != null) {
            String imagePath = Environment.getExternalStorageDirectory()
                    + "/" + shareImg;
            File imageFileToShare = new File(imagePath);
            Uri uri = Uri.fromFile(imageFileToShare);
            sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
        }

        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareText);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
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
