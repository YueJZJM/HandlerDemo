package com.example.handlerdemo;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ThreadActivity extends AppCompatActivity {

    private TextView textView;

    private HandlerThread thread;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        textView = new TextView(this);
        textView.setText("handler Thread");
        setContentView(textView);
        thread = new HandlerThread("handler thread");
        thread.start();
        handler = new Handler(thread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.i("current thread------>", Thread.currentThread() + "");
            }
        };
        handler.sendEmptyMessage(1);
    }
}
