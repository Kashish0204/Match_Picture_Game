package com.example.picture_match;

import static com.example.picture_match.MainActivity.preferences;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Level_Holder> {

    int levelNo;
    int pos = 0;
    String[] levelArr;
    Horizontal_Recyclerview_activity horizontal_recyclerview_activity;

    public RecyclerAdapter(String[] levelArr, Horizontal_Recyclerview_activity horizontal_recyclerview_activity, int levelNo) {
        this.levelArr = levelArr;
        this.horizontal_recyclerview_activity = horizontal_recyclerview_activity;
        this.levelNo=levelNo;
    }

    @NonNull
    @Override
    public RecyclerAdapter.Level_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(horizontal_recyclerview_activity).inflate(R.layout.horizontal_recyclerview_activity_item, parent, false);
        Level_Holder holder = new Level_Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.Level_Holder holder, @SuppressLint("RecyclerView") int position) {
        pos = position;
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class Level_Holder extends RecyclerView.ViewHolder {
        ListView listview;

        Listview_Adapter adapter;

        public Level_Holder(@NonNull View itemView) {
            super(itemView);
            listview = itemView.findViewById(R.id.horizontal_recyclerview_activity_item_listView);

            adapter = new Listview_Adapter(horizontal_recyclerview_activity, levelArr);
            listview.setAdapter(adapter);


            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String levelStatus = preferences.getString("levelStatus" + i, "pending");
                    Log.d("YYY", "onItemClick: pos="+i+" LevelStatus="+levelStatus);
                    if (i==0)
                    {
                        Intent intent = new Intent(horizontal_recyclerview_activity, Activity_Gridview.class);
                        intent.putExtra("levelNo", i);
                        horizontal_recyclerview_activity.startActivity(intent);
                        horizontal_recyclerview_activity.finish();
                    }
                    if (levelStatus.equals("win") || levelNo==i) {

                        Intent intent = new Intent(horizontal_recyclerview_activity, Activity_Gridview.class);
                        intent.putExtra("levelNo", i);
                        horizontal_recyclerview_activity.startActivity(intent);
                        horizontal_recyclerview_activity.finish();
                    }
                }
            });
        }
    }
}
