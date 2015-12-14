package com.demo.zhaoxuanli.listdemo.db_orm;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.demo.zhaoxuanli.listdemo.DemoApplication;
import com.demo.zhaoxuanli.listdemo.R;
import com.demo.zhaoxuanli.listdemo.db_orm.orm.DataSupport;
import com.demo.zhaoxuanli.listdemo.db_orm.orm.IDataSupport;

import java.util.ArrayList;
import java.util.Random;

public class SQLiteActivity extends AppCompatActivity {

    private SwipeRefreshLayout mRefreshLayout;
    private Button mSearchBtn, mDeleteBtn, mSaveBtn;
    private EditText mSearchEdit;
    private TextView mResultText;
    private ListView mListView;
    private Random random = new Random(100);//指定种子数100
    private IDataSupport dataSupport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        initView();

    }


    private void initView() {
        dataSupport = DataSupport.getInstance(DemoApplication.getInstance());

        mListView = (ListView) findViewById(R.id.listView);
        mResultText = (TextView) findViewById(R.id.resultText);
        mSearchEdit = (EditText) findViewById(R.id.searchEdit);
        mSearchBtn = (Button) findViewById(R.id.searchBtn);
        mDeleteBtn = (Button) findViewById(R.id.deleteBtn);
        mSaveBtn = (Button) findViewById(R.id.saveBtn);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = random.nextInt();
                System.out.println(id);
                String name = "小明" + random.nextInt(10);
                String sex;
                if (random.nextInt() % 2 == 0)
                    sex = "男";
                else
                    sex = "女";
                String className = "三年" + random.nextInt(10) + "班";
                String schoolName = "上海第" + random.nextInt(10) + "小学";

                StudentValue sv = new StudentValue(id, name, sex, className, schoolName);

                dataSupport.insertEntity(sv);
                StudentValue studentValue = dataSupport.getEntity(id, StudentValue.class);
                Log.e("SQLiteActivity", studentValue.getName());
                ArrayList<StudentValue> studentValues = dataSupport.getAllEntity(StudentValue.class);
                for (StudentValue student : studentValues) {
                    Log.i("RESULT", student.getId() + "  " + student.getName() + " " + student.getSex() + " " + student.getClassName() + " " + student.getSchoolName());
                }
            }
        });

    }


}
