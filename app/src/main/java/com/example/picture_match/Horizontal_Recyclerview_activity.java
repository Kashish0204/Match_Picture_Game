package com.example.picture_match;

import static com.example.picture_match.MainActivity.preferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

public class Horizontal_Recyclerview_activity extends AppCompatActivity {
    String levelArr[]={"level-1", "level-2", "level-3", "level-4", "level-5", "level-6", "level-7", "level-8", "level-9", "level-10"};
    Toolbar toolbar;
    RecyclerView recyclerView;
    private int levelNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_recyclerview);

        recyclerView=findViewById(R.id.recyclerView);
//        int levelNo = getIntent().getIntExtra("levelNo", 0);
        levelNo=preferences.getInt("lastLevel",0);
        levelNo++;//1
        RecyclerAdapter adapter=new RecyclerAdapter(levelArr, Horizontal_Recyclerview_activity.this,levelNo);//1
        toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (item.getItemId() == R.id.menu_share) {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String shareMessage= "";
            shareMessage = shareMessage + "Android: https://play.google.com/store/apps/details?id=com.alcamasoft.memorymatch&hl=en \n" +
                    "\n" +
                    " iOS: https://itunes.apple.com/app/id1448413094";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        }
        return super.onOptionsItemSelected(item);
    }

}