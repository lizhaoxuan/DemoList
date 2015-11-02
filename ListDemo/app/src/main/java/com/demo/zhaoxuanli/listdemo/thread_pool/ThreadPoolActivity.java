package com.demo.zhaoxuanli.listdemo.thread_pool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.demo.zhaoxuanli.listdemo.R;

/**
 * 加一个AsyncTask 控制
 */
public class ThreadPoolActivity extends AppCompatActivity implements View.OnClickListener{

    private Button startBtn1,startBtn2,startBtn3,startBtn4,startBtn5;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool);

        initView();
    }


    private void initView(){
        startBtn1 = (Button)findViewById(R.id.startBtn1);
        startBtn2 = (Button)findViewById(R.id.startBtn2);
        startBtn3 = (Button)findViewById(R.id.startBtn3);
        startBtn4 = (Button)findViewById(R.id.startBtn4);
        startBtn5 = (Button)findViewById(R.id.startBtn5);
        startBtn1.setOnClickListener(this);
        startBtn2.setOnClickListener(this);
        startBtn3.setOnClickListener(this);
        startBtn4.setOnClickListener(this);
        startBtn5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int flag = v.getId();
        switch (flag){
            case R.id.startBtn1:startActivity(TaskManager.SINGLE_THREAD_EXECUTOR);
                return;
            case R.id.startBtn2:startActivity(TaskManager.FIXED_THREAD_POOL);
                return;
            case R.id.startBtn3:startActivity(TaskManager.CACHED_THREAD_POOL);
                return;
            case R.id.startBtn4:startActivity(TaskManager.CUSTOM_THREAD_POOL);
                return;
            case R.id.startBtn5:startActivity(new Intent(this,ThreadControlActivity.class));
                return;
        }
    }

    private void startActivity(int kind){
        Intent in = new Intent(this,ThreadShowActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("kind",kind);
        in.putExtras(bundle);
        startActivity(in);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_thread_pool, menu);
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
