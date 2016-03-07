package com.demo.zhaoxuanli.listdemo.thread_pool;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.demo.zhaoxuanli.listdemo.R;
import com.demo.zhaoxuanli.listdemo.default_widget.ProgressBarItem;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;

public class ThreadShowActivity extends AppCompatActivity {

    private ArrayList<ProgressBarItem> mBarList;
    private ArrayList<MyRunnable> mRunnableList;
    private Button mStartBtn, mStopBtn, mAddBtn;
    private TaskManager mTaskManager;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_show);


        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                mBarList.get(msg.what).setProgressBar(msg.arg1);
            }
        };

        initView();

    }

    private void initView() {

        mBarList = new ArrayList<>();
        mBarList.add((ProgressBarItem) findViewById(R.id.bar1));
        mBarList.add((ProgressBarItem) findViewById(R.id.bar2));
        mBarList.add((ProgressBarItem) findViewById(R.id.bar3));
        mBarList.add((ProgressBarItem) findViewById(R.id.bar4));
        mBarList.add((ProgressBarItem) findViewById(R.id.bar5));
        mBarList.add((ProgressBarItem) findViewById(R.id.bar6));

        mStartBtn = (Button) findViewById(R.id.startBtn);
        mStopBtn = (Button) findViewById(R.id.stopBtn);
        mAddBtn = (Button) findViewById(R.id.addBtn);

        Bundle bundle = getIntent().getExtras();
        int kind = bundle.getInt("kind");
        mTaskManager = new TaskManager(kind);

        initRunnable();


        initEvent();
    }


    private void initRunnable() {
        mRunnableList = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            mRunnableList.add(new MyRunnable(i, "线程" + i, mHandler));
            mBarList.get(i).setNameText("线程" + i + ":");
        }
    }

    private void initEvent() {
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (MyRunnable runnable : mRunnableList) {
                    mTaskManager.executeTask(runnable);
                }
                mStartBtn.setEnabled(false);
                mStopBtn.setEnabled(true);
            }
        });

        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = new Random().nextInt(5);
                mTaskManager.executeTask(new MyRunnable(i, "线程" + i, mHandler));
                Toast.makeText(ThreadShowActivity.this, "i=" + i, Toast.LENGTH_SHORT).show();
            }
        });

        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTaskManager.terminateAllTask();
                mStartBtn.setEnabled(true);
                mStopBtn.setEnabled(false);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_thread_show, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
