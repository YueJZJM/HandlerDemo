package com.example.handlerdemo;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class FourActivity extends AppCompatActivity implements View.OnClickListener {

    //主线程中的handler
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Message message = Message.obtain();
            Log.i("Handler------->", "main     "+ message.what);
            //向子线程发送消息
            threadHandler.sendMessageDelayed(message, 1000);
        }
    };

    //子线程中的handler
    private Handler threadHandler;

    private Button mStartButton;
    private Button mStopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);

        mStartButton = findViewById(R.id.Start_button);
        mStopButton = findViewById(R.id.Stop_button);
        mStartButton.setOnClickListener(this);
        mStopButton.setOnClickListener(this);

        HandlerThread thread = new HandlerThread("handlerThread");
        thread.start();
        threadHandler = new Handler(thread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Message message = Message.obtain();
               // Message message = new Message();
                Log.i("Handler------->", "threadHandler  " + message.what);
                //向主线程发送消息
                handler.sendMessageDelayed(message, 1000);
            }
        };



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Start_button:
                handler.sendEmptyMessage(1);
                break;
            case R.id.Stop_button:
                Log.i("Handler", "Handler");
              //  handler.removeMessages(1);
               // handler.removeCallbacks(threadHandler);
                //这里是 0 才能停止
                handler.removeMessages(0);
                break;
            default:
                break;
        }
    }
}
