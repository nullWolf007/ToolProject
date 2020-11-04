package com.example.learn.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.example.learn.R;
import com.example.learn.bean.Person;
import com.example.learn.common.IPersonManager;
import com.example.learn.common.Stub;
import com.example.learn.server.RemoteService;

import java.util.List;

public class ClientActivity extends AppCompatActivity {

    private IPersonManager iPersonManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, RemoteService.class);
        intent.setAction("com.example.learn.remoteservice");
        bindService(intent, connection, Context.BIND_AUTO_CREATE);

        findViewById(R.id.main_tv_aidl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.e("ClientActivity:", "Current Thread:" + Thread.currentThread());
                    iPersonManager.addPerson(new Person("hn"));
                    List<Person> list = iPersonManager.getPersons();
                    Log.e("ClientActivity:", "list:" + list.toString() + "Current Thread:" + Thread.currentThread());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("ClientActivity:", "onServiceConnected");
            iPersonManager = Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("ClientActivity:", "onServiceDisconnected");
            iPersonManager = null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
