package com.demo.zhaoxuanli.listdemo.distribute;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.demo.zhaoxuanli.listdemo.R;

public class DistributeActivity extends AppCompatActivity {

    private DistributePool distributePool;
    private DisObserver<Event> userObserver;
    private DisObserver<StudentEvent> studentObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribute);

        distributePool = DistributePool.getInstance();

        distributePool.addObserver(this);
        //distributePool.addData(new StudentEvent(18, "初二三班"));
        distributePool.addData(new Event(1,"李兆轩"));

    }

    public void onEventStudentThread(StudentEvent data) {
        Log.e("TAG", "StudentEvent 收到消息,data:" + data.getClassName());
    }

    public void onEventUserThread(Event data) {
        Log.e("TAG","userObserver 收到消息，data:"+data.getName());
    }


    private void initObserver(){
        userObserver = new DisObserver<Event>() {
            @Override
            public void distributeEvent(Event data) {
                Log.e("TAG","userObserver 收到消息，data:"+data.getName());
            }
        };

        studentObserver = new DisObserver<StudentEvent>() {
            @Override
            public void distributeEvent(StudentEvent data) {
                Log.e("TAG","StudentEvent 收到消息,data:"+data.getClassName());
            }
        };

        distributePool.addObserver(userObserver);
        distributePool.addObserver(studentObserver);
    }


}
