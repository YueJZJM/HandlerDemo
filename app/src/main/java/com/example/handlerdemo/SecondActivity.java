package com.example.handlerdemo;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private MyThread thread;

    private Handler handlerUI = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i("UI-------->", String.valueOf(Thread.currentThread()));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView textView = new TextView(this);
        textView.setText("handler");
        setContentView(textView);
        thread = new MyThread();
        thread.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.handler.sendEmptyMessage(1);
        handlerUI.sendEmptyMessage(1);
    }

    class MyThread extends Thread{

        public Handler handler;

        @Override
        public void run() {
            super.run();
            //创建Looper
            Looper.prepare();
            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    Log.i("currentThread:" , String.valueOf(Thread.currentThread()));
                }
            };
            Looper.loop();
        }
    }
}
