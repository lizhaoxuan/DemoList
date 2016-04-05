package com.demo.zhaoxuanli.listdemo.focus_divert;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.demo.zhaoxuanli.listdemo.R;

public class FocusDivertActivity extends AppCompatActivity {

    private EditText dlEdit,editText;

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_divert);

        btn = (Button)findViewById(R.id.btn);
        editText = (EditText)findViewById(R.id.editText);
        dlEdit = (EditText)findViewById(R.id.dlEdit);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();
                editText.findFocus();
                InputMethodManager imm = ((InputMethodManager)FocusDivertActivity.this.getSystemService(INPUT_METHOD_SERVICE));
                imm.showSoftInput(editText,1);
            }
        });
    }
}
