package com.nullWolf.learn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

@BindContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tv_test)
    TextView tv_shut_down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectImpl.getInstance().inject(this);

        tv_shut_down.setText("Test");
    }

    @OnClick(R.id.tv_test)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_test:
                Toast.makeText(this, "测试", Toast.LENGTH_LONG).show();
                break;
        }
    }
}







