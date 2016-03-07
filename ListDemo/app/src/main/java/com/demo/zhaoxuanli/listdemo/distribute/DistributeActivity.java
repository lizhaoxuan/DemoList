package com.demo.zhaoxuanli.listdemo.distribute;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.demo.zhaoxuanli.listdemo.R;

public class DistributeActivity extends AppCompatActivity {

    private DistributePool distributePool;
    private DisObserver<UserDto> userObserver;
    private DisObserver<StudentDto> studentObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribute);

        distributePool = DistributePool.getInstance();

        distributePool.addObserver(this);
        //distributePool.addData(new StudentDto(18, "初二三班"));
        distributePool.addData(new UserDto(1,"李兆轩"));

    }

    public void onEventStudentThread(StudentDto data) {
        Log.e("TAG", "StudentDto 收到消息,data:" + data.getClassName());
    }

    public void onEventUserThread(UserDto data) {
        Log.e("TAG","userObserver 收到消息，data:"+data.getName());
    }


    private void initObserver(){
        userObserver = new DisObserver<UserDto>() {
            @Override
            public void distributeEvent(UserDto data) {
                Log.e("TAG","userObserver 收到消息，data:"+data.getName());
            }
        };

        studentObserver = new DisObserver<StudentDto>() {
            @Override
            public void distributeEvent(StudentDto data) {
                Log.e("TAG","StudentDto 收到消息,data:"+data.getClassName());
            }
        };

        distributePool.addObserver(userObserver);
        distributePool.addObserver(studentObserver);
    }


}
