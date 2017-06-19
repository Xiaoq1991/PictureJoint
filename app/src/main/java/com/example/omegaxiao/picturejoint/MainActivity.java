package com.example.omegaxiao.picturejoint;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static com.example.omegaxiao.picturejoint.R.drawable.bitmap1;

public class MainActivity extends AppCompatActivity {

    JointBitmapView jointBitmapView;
    MyDialog dialog;
    Button btn_down;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        jointBitmapView = (JointBitmapView) findViewById(R.id.img_loading);
        btn_down = (Button) findViewById(R.id.btn_dialog);
    }
    int i = 0;

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dialog.setProcess(i++);
            Message message = this.obtainMessage();
            if(i<=100){
                this.sendMessageDelayed(message,100);
            }

        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        BitmapFactory.Options bfoOptions = new BitmapFactory.Options();
        bfoOptions.inScaled = false;

        final Bitmap bitmap1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.sprite_game_loading);
        final Bitmap bitmap2 = BitmapFactory.decodeResource(this.getResources(),R.drawable.sprite_game_loading_end);
//        jointBitmapView.setBitmaps(bitmap1,bitmap2);


        btn_down.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
//                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
//                View view = inflater.inflate(R.layout.dialog,null);
//                jointBitmapView = (JointBitmapView) view.findViewById(R.id.jp_download);
                MyDialog.Builder builder = new MyDialog.Builder(MainActivity.this);
                dialog = builder.create();
                dialog.show();
//                Window window = dialog.getWindow();
//                WindowManager.LayoutParams layoutParams = window.getAttributes();
//                layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
//                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                window.setAttributes(layoutParams);
//                window.setGravity(Gravity.CENTER);
//                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                params.setMargins(0,0,0,0);
//                jointBitmapView.setLayoutParams(params);
//                jointBitmapView.setBitmaps(bitmap1,bitmap2);
//                dialog.addContentView(view,new RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT
//                        , android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
//                dialog.setContentView(view);
//
//                dialog.show();
                i = 0;
                mHandler.obtainMessage().sendToTarget();
            }
        });


    }

}

class MyDialog extends Dialog{

    JointBitmapView jointBitmapView;

    public MyDialog(@NonNull Context context) {
        super(context);
    }

    public MyDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected MyDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setJointBitmapView(JointBitmapView jointBitmapView){
        this.jointBitmapView = jointBitmapView;
    }

    public void setProcess(int process){
        if(jointBitmapView != null){
            jointBitmapView.setProgress(process);
        }
    }
    public static class Builder {
        private Context context;
        private Bitmap start;
        private Bitmap end;

        public Builder(Context context) {
            this.context = context;
        }


        public Builder setImage(Bitmap start,Bitmap end) {
            this.start = start;
            this.end = end;
            return this;

        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public MyDialog create() {
            LinearLayout linearLayout = new LinearLayout(context);
            FrameLayout.LayoutParams rootParams = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutParams(rootParams);
            JointBitmapView jointBitmapView = new JointBitmapView(context,
                    R.drawable.sprite_game_loading,
                    R.drawable.sprite_game_loading_end);
            LinearLayout.LayoutParams barParams = new LinearLayout.LayoutParams(
                    dp2px(80f,context.getResources()),
                    dp2px(80f,context.getResources()));
            barParams.setMargins(
                    dp2px(60.5f,context.getResources()),
                    0,
                    dp2px(60.5f,context.getResources()),
                    0
                    );
            jointBitmapView.setLayoutParams(barParams);
            linearLayout.addView(jointBitmapView);
            TextView textView = new TextView(context);
            textView.setText("游戏即将开始！请耐心等待...");
            textView.setTextSize(16f);
            textView.setTextColor(Color.parseColor("#ababab"));
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            textParams.setMargins(0,dp2px(10f,context.getResources()),0,0);
            textView.setLayoutParams(textParams);
            linearLayout.addView(textView);
            Button button = new Button(context);
            button.setText("取消");
            button.setTextSize(14);
            button.setTextColor(Color.parseColor("#ffffff"));
            button.setBackground(context.getResources().getDrawable(R.drawable.sprite_game_loading_cancel));
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    dp2px(100,context.getResources()),
                    dp2px(30,context.getResources()));
            buttonParams.setMargins(0,dp2px(35,context.getResources()),0,0);
            buttonParams.gravity = Gravity.CENTER_HORIZONTAL;
            button.setLayoutParams(buttonParams);
            linearLayout.addView(button);
//            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View view = inflater.inflate(R.layout.dialog,null);
//            JointBitmapView jointBitmapView = (JointBitmapView) view.findViewById(R.id.jp_download);
            MyDialog dialog = new MyDialog(context,R.style.ApolloGameLoadingDialog);
            dialog.setJointBitmapView(jointBitmapView);
            Window window = dialog.getWindow();
//            WindowManager.LayoutParams layoutParams = window.getAttributes();
//            layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
//            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//            window.setAttributes(layoutParams);
            window.setGravity(Gravity.CENTER);
//            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.setMargins(0,0,0,0);
////                jointBitmapView.setLayoutParams(params);
////            jointBitmapView.setBitmaps(start,end);
//            dialog.addContentView(view,new RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT
//                    , android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
            dialog.setContentView(linearLayout);
            dialog.setCanceledOnTouchOutside(false);
            return dialog;
        }


    }
    public static final int dp2px(float dp, Resources res) {
        if (dp == 0) {
            return 0;
        } else {
            Log.e("mainActivity","desity:" + res.getDisplayMetrics().density);
            return (int) (dp * res.getDisplayMetrics().density + 0.5f);
        }
    }

}
