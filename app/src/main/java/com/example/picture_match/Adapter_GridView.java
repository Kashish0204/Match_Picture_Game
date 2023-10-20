package com.example.picture_match;

import static com.example.picture_match.MainActivity.editor;
import static com.example.picture_match.MainActivity.preferences;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Adapter_GridView extends BaseAdapter {

    Activity_Gridview gridViewActivity;

    TextView timer;
    ProgressBar progressBar;
    int lastLevel;

    RelativeLayout relativeLayout;
    List<String> imgArr;
    int click = 1;
    View mask2;
    int time,temp;
    int pos1 = 0, pos2 = 0, count = 0;
    int levelNo;
    CountDownTimer countDownTimer1;
    boolean isWin=false;

    public Adapter_GridView(Activity_Gridview gridViewActivity, List<String> imgArr, TextView timer, ProgressBar progressBar, int levelNo) {
        this.gridViewActivity = gridViewActivity;
        this.imgArr = imgArr;
        this.timer = timer;
        this.progressBar = progressBar;
        this.levelNo = levelNo;
    }

    @Override
    public int getCount() {
        return imgArr.size();
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
        view = LayoutInflater.from(gridViewActivity).inflate(R.layout.activity_gridview_item, viewGroup, false);
        ImageView imageView = view.findViewById(R.id.activity_gridview_item_imageview);
        View mask1 = view.findViewById(R.id.mask1);
        time= preferences.getInt("time",0);
        InputStream inputstream = null;

        try {
            inputstream = gridViewActivity.getAssets().open("" + imgArr.get(i));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Drawable drawable = Drawable.createFromStream(inputstream, null);
        imageView.setImageDrawable(drawable);

        RelativeLayout relativeLayout = view.findViewById(R.id.relativeLayout);
        CountDownTimer countDownTimer = new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long l) {
                progressBar.setMax(5);
                timer.setText("" + (l / 1000) + "/0");
                progressBar.setProgress((int) (l / 1000));
            }

            @Override
            public void onFinish() {
                mask1.setVisibility(View.VISIBLE);
                playGame(mask1, relativeLayout, i);

                countDownTimer1 = new CountDownTimer(30000, 1000) {
                    @Override
                    public void onTick(long l) {
                        progressBar.setMax(30);
                        int interval = (int) (l / 1000);
                        timer.setText("" + (progressBar.getMax() - interval) + "/30");
                        progressBar.setProgress(progressBar.getMax() - interval);
                        time = progressBar.getProgress();
                        if(isWin)
                        {
                            cancel();
                        }
                        else {

                        }
                    }

                    @Override
                    public void onFinish() {
                        if (i == 0) {
                            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(gridViewActivity);
                            builder.setTitle("TIME OUT");
                            builder.setMessage("TRY AGAIN?");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(gridViewActivity, Horizontal_Recyclerview_activity.class);
                                    gridViewActivity.startActivity(intent);
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(gridViewActivity, Horizontal_Recyclerview_activity.class);
                                    gridViewActivity.startActivity(intent);
                                }
                            });
                            builder.show();
                        }
                        relativeLayout.setEnabled(false);
                    }
                }.start();
            }
        }.start();
        return view;
    }

    private void playGame(View mask1, RelativeLayout relativeLayout, int position) {

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable runnable;
                Handler handler = new Handler();
                if (click == 1) {
                    mask1.setVisibility(View.INVISIBLE);
                    pos1 = position;
                    Log.d("KKK", "onClick: pos 1 :- " + pos1);
                    mask2 = mask1;
                    click = 3;
                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            click = 2;
                        }
                    };
                    handler.postDelayed(runnable, 500);
                }
                if (click == 2) {
                    mask1.setVisibility(View.INVISIBLE);
                    pos2 = position;
                    Log.d("KKK", "onClick: pos 2 :- " + pos2);
                    click = 3;
                    Log.d("YYY", "onClick: pos1=" + imgArr.get(pos1) + "\tpos2=" + imgArr.get(pos2));

                    if (imgArr.get(pos1).equals(imgArr.get(pos2))) {
                        //mask1.setVisibility(View.INVISIBLE);

                        mask2.setVisibility(View.INVISIBLE);
                        count++;
                        runnable = new Runnable() {
                            @Override
                            public void run() {
                                click = 1;
                            }
                        };
                        handler.postDelayed(runnable, 300);


                    } else {

                        runnable = new Runnable() {
                            @Override
                            public void run() {
                                mask1.setVisibility(View.VISIBLE);
                                mask2.setVisibility(View.VISIBLE);
                                click = 1;
                            }
                        };
                        handler.postDelayed(runnable, 300);
                    }
                    if (count == 6) {
                        editor.putInt("lastLevel", levelNo);

                        Log.d("HHH", "onClick: levelno= " + levelNo);
                        editor.putString("levelStatus" + levelNo, "win");
                        editor.commit();
                        editor.putInt("time"+levelNo,time);

                        editor.commit();
                        isWin=true;
                        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(gridViewActivity);
                        builder.setTitle("NEW RECORD!!!");
                        builder.setMessage("" + time + " SECONDS\n" + (levelNo + 1) + " Level\nWELL DONE!!!!");

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(gridViewActivity, Horizontal_Recyclerview_activity.class);
                                //levelNo = levelNo + 1;
                                //intent.putExtra("levelNo", levelNo);
                                gridViewActivity.startActivity(intent);
                            }
                        });
                        builder.show();
                    }
                }
            }
        });
    }
}
