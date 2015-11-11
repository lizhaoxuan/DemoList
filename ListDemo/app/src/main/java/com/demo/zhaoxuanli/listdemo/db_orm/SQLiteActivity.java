package com.demo.zhaoxuanli.listdemo.db_orm;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.demo.zhaoxuanli.listdemo.R;
import com.demo.zhaoxuanli.listdemo.db_orm.orm.DataSupport;
import com.demo.zhaoxuanli.listdemo.db_orm.orm.DatabaseHelper;

import java.util.Random;

public class SQLiteActivity extends AppCompatActivity {

    private SwipeRefreshLayout mRefreshLayout;
    private Button mSearchBtn,mDeleteBtn,mSaveBtn;
    private EditText mSearchEdit;
    private TextView mResultText;
    private ListView mListView;
    private Random random = new Random(100);//指定种子数100
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        //initDataSource();
        initView();

    }


    private void initView(){
        mListView = (ListView)findViewById(R.id.listView);
        mResultText = (TextView)findViewById(R.id.resultText);
        mSearchEdit = (EditText)findViewById(R.id.searchEdit);
        mSearchBtn = (Button)findViewById(R.id.searchBtn);
        mDeleteBtn = (Button)findViewById(R.id.deleteBtn);
        mSaveBtn = (Button)findViewById(R.id.saveBtn);
        mRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);

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
                int id = random.nextInt()*-1;
                System.out.println(id);
                String name = "小明"+random.nextInt();
                String sex ;
                if(random.nextInt()%2==0)
                    sex = "男";
                else
                    sex = "女";
                String className = "三年"+random.nextInt()+"班";
                String schoolName = "上海第"+random.nextInt()+"小学";

                StudentValue sv = new StudentValue(id,name,sex,className,schoolName);

                DataSupport dataSupport = DataSupport.getInstance(getApplication());
                dataSupport.insertEntity(sv);
                StudentValue studentValue = (StudentValue)dataSupport.getEntity(id,StudentValue.class);
                Log.e("SQLiteActivity",studentValue.getName());

            }
        });

    }

    private void initDataSource(){
        DatabaseHelper database = new DatabaseHelper(this);//这段代码放到Activity类中才用this
        db = database.getWritableDatabase();
    }

}
