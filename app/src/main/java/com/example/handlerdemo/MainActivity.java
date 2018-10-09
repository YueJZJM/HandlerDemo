package com.example.handlerdemo;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButtonPost;
    private TextView mTextViewPost;
    private Button mButtonPostDelayed;
    private TextView mTextViewPostDelayed;
    private Button mButtonHandleMessage;
    private TextView mTextHandleMessage;
    private Button mButtonHandlerCallback;
    private Button SecondButton;
    private Button ThreadButton;
    private Button FourButton;

    private MyRunnable myRunnable = new MyRunnable();


    private Handler handlerPost = new Handler();

    private Handler handlerSendDey = new Handler();

    private Handler handlerSend = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mTextHandleMessage.setText(msg.arg1 + "-" + msg.obj);
        }
    };

    private Handler handlerCallback = new Handler(new Handler.Callback() {

        //返回true，表示消息已经拦截，第二个handleMessage就不会执行了
        @Override
        public boolean handleMessage(Message message) {
            Log.i("handleMessage", "1");
            return false;
        }
    }){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i("handleMessage", "2");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonPost = findViewById(R.id.post_button);
        mTextViewPost = findViewById(R.id.post_text);
        mButtonPostDelayed = findViewById(R.id.postDelayed_button);
        mTextViewPostDelayed = findViewById(R.id.postDelayed_text);
        SecondButton = findViewById(R.id.thread_button);
        ThreadButton = findViewById(R.id.HandlerThread_button);
        FourButton = findViewById(R.id.four_button);
        mButtonPostDelayed.setOnClickListener(this);
        mButtonPost.setOnClickListener(this);
        SecondButton.setOnClickListener(this);
        ThreadButton.setOnClickListener(this);
        FourButton.setOnClickListener(this);

        mTextViewPostDelayed.setOnClickListener(this);
        mButtonHandleMessage = findViewById(R.id.handleMessage_button);
        mTextHandleMessage = findViewById(R.id.handleMessage_text);
        mButtonHandleMessage.setOnClickListener(this);
        mButtonHandlerCallback = findViewById(R.id.handleCallback_button);
        mButtonHandlerCallback.setOnClickListener(this);

        Log.i("main", "main");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.post_button:
                updateUIByPost();
                break;
            case R.id.postDelayed_button:
                //3000指的是点击开始到第一次执行的时间
                handlerSendDey.postDelayed(myRunnable, 3000);
                break;
            case R.id.handleMessage_button:
                updateUIByHandlerMsg();
                break;
            case R.id.postDelayed_text:
                remove(handlerSendDey);
                break;
            case R.id.handleCallback_button:
                handlerCallback.sendEmptyMessage(1);
                break;
            case R.id.thread_button:
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
                break;
            case R.id.HandlerThread_button:
                Intent intent1 = new Intent(MainActivity.this, ThreadActivity.class);
                startActivity(intent1);
                break;
            case R.id.four_button:
                Intent intent2 = new Intent(MainActivity.this, FourActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    private void remove(Handler handler) {
        handler.removeCallbacks(myRunnable);
    }

    void updateUIByPost() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                handlerPost.post(new Runnable() {
                    @Override
                    public void run() {
                        //打印日志会发现，更新UI操作还是在主线程中
                        Log.i("post", "123");
                        mTextViewPost.setText("123");
                    }
                });
            }
        }.start();
    }



    private String strings[] = {"123","456","789"};
    private int index = 0;

    class MyRunnable implements Runnable{
        @Override
        public void run() {
            index ++ ;
            index = index % 3;
            mTextViewPostDelayed.setText(strings[index]);
            //1000 指每隔 1000 循环一次
            handlerSendDey.postDelayed(myRunnable, 1000);
        }
    }



    void updateUIByHandlerMsg() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(1000);
//                    Message message = new Message();
                    Message message = handlerSend.obtainMessage();
                    message.arg1 = 123;
                    PerSon perSon = new PerSon();
                    perSon.age = 10;
                    perSon.name = "hhh";
                    message.obj = perSon;
                    //发送消息给自己
//                    message.sendToTarget();
                    handlerSend.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
