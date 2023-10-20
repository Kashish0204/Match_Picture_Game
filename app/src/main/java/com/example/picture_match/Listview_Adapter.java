package com.example.picture_match;


import static com.example.picture_match.MainActivity.preferences;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Listview_Adapter extends BaseAdapter {
    Horizontal_Recyclerview_activity horizontalRecyclerviewActivity;
    String[] levelArr;
    String levelStatus;
    int levelNo;


    public Listview_Adapter(Horizontal_Recyclerview_activity horizontalRecyclerviewActivity, String[] levelArr) {
        this.horizontalRecyclerviewActivity = horizontalRecyclerviewActivity;
        this.levelArr = levelArr;

    }

    @Override
    public int getCount() {
        return levelArr.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(horizontalRecyclerviewActivity).inflate(R.layout.horizontal_recyclerview_activity_item_item, viewGroup, false);
        TextView textView = view.findViewById(R.id.textView);
        ImageView txtView = view.findViewById(R.id.txtView);

        String time = String.valueOf(preferences.getInt("time"+i, 0));
        Log.d("GGG", "getView: levelNo="+i+"\tTime="+time);
        textView.setText("" + levelArr[i]);
        levelStatus = preferences.getString("levelStatus" + i, "pending");
        levelNo = preferences.getInt("lastLevel", 0);
//        int finalPosition = i;
//        if(levelStatus.equals("win")||finalPosition==(i+1))
//        {
//            txtView.setVisibility(View.INVISIBLE);
//
//        }
        if (levelStatus.equals("win") || i == 0 || i == (levelNo + 1)) {

            txtView.setVisibility(View.INVISIBLE);
            textView.setText("" + levelArr[i] + " - " + time + " sec");
        } else if (levelStatus.equals("pending")) {
            Log.d("JJJ", "getView: PENDING level no " + i);
            txtView.setVisibility(View.VISIBLE);
            Drawable d = horizontalRecyclerviewActivity.getResources().getDrawable(R.drawable.bg);
            d.setAlpha(180);
            txtView.setBackgroundDrawable(d);
        }
        return view;
    }
}
