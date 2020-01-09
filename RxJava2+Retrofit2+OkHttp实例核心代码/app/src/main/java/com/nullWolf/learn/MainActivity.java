package com.nullWolf.learn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_get_data).setOnClickListener(v -> {
            //a=fy&f=auto&t=auto&w=hello%20world
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("a", "fy");
            hashMap.put("f", "auto");
            hashMap.put("t", "auto");
            hashMap.put("w", "hello,World");

            disposable = NetUtils.getNormalRetrofit()
                    .create(Api.class)
                    .getData(hashMap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getDataResultBean -> {
                        //数据获取成功
                        ((TextView) findViewById(R.id.tv_show_data)).setText(getDataResultBean.toString());
                    }, throwable -> {
                        //异常处理
                        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();
                    });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != disposable) {
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }
}





