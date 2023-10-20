package com.example.picture_match;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.LabeledIntent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import kotlin.contracts.Returns;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtNoLimit,txtNormal,txtHard,txtShare;
    Toolbar toolbar;
    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;
    int lastLevel,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        preferences=getSharedPreferences("myPref",MODE_PRIVATE);
        editor= preferences.edit();

        lastLevel=preferences.getInt("lastLevel",-1);
        time= preferences.getInt("time",0);


        txtHard=findViewById(R.id.txtHard);
        txtHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Horizontal_Recyclerview_activity.class);
               //intent.putExtra("Level","Hard");
                startActivity(intent);
            }
        });

        txtNormal=findViewById(R.id.txtNormal);
        txtNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Horizontal_Recyclerview_activity.class);
                //intent.putExtra("Level","Normal");
                startActivity(intent);
            }
        });

        txtNoLimit=findViewById(R.id.txtNoLimit);
        txtNoLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Horizontal_Recyclerview_activity.class);
                intent.putExtra("Level","NoLimit");
                startActivity(intent);
            }
        });

        toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        txtShare=findViewById(R.id.txtShare);
        txtShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
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

    @Override
    public void onClick(View view) {

    }
}