package com.example.picture_match;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Activity_Gridview extends AppCompatActivity {


    GridView gridview;
    TextView timer;
    Toolbar toolbar;
    Adapter_GridView adapter;
    ProgressBar progressBar;
    ArrayList<String> listImages = new ArrayList<>();
    List<String> imageList = new ArrayList<>();
    int levelNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);
        gridview = findViewById(R.id.gridview);
        timer = findViewById(R.id.timer);
        progressBar = findViewById(R.id.progressBar);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        levelNo=getIntent().getIntExtra("levelNo",0);
        getImageFromAssets();
        adapter = new Adapter_GridView(Activity_Gridview.this, imageList, timer, progressBar,levelNo);
        showDialogue();

    }

    private void showDialogue() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Activity_Gridview.this);
        dialogBuilder.setTitle("Alert...!");
        dialogBuilder.setMessage("YOU HAVE 5 SECONDS TO MEMORIZE ALL IMAGES");

        dialogBuilder.setPositiveButton("GO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                gridview.setAdapter(adapter);
                dialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        onDismiss(dialogInterface);
                    }
                });
            }
        });

        dialogBuilder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_share) {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String shareMessage = "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=com.alcamasoft.memorymatch";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        }
        return super.onOptionsItemSelected(item);
    }

    public void getImageFromAssets() {

        String[] images = new String[0];
        try {
            images = getAssets().list("");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        listImages = new ArrayList<String>(Arrays.asList(images));
        imageList = listImages.subList(0, 6);
        imageList.addAll(imageList);
        Collections.shuffle(imageList);
    }


}