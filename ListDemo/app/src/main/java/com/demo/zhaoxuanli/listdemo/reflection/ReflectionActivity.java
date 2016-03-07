package com.demo.zhaoxuanli.listdemo.reflection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.demo.zhaoxuanli.listdemo.R;

public class ReflectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflection);

        IObserver<UserDto> userObserver = new IObserver<UserDto>() {
            @Override
            public Class getType() {
                return null;
            }

            @Override
            public void update(UserDto data) {
                Log.e("TAG","Update UserDto 回调成功");
            }
        };
        IObserver<StudentDto> studentObserver = new IObserver<StudentDto>() {
            @Override
            public Class getType() {
                return null;
            }

            @Override
            public void update(StudentDto data) {
                Log.e("TAG","Update StudentDto 回调成功");
            }
        };

        InterfaceReflectionTest.addObserver(userObserver);
        InterfaceReflectionTest.addObserver(studentObserver);

        Object sonDto = new SonDto(1,"asd");

        Log.e("TAG","子类类名"+sonDto.getClass().getName());
    }
}
