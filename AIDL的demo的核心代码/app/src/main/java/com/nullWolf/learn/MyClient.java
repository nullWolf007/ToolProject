package com.nullWolf.learn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.telecom.ConnectionService;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyClient extends AppCompatActivity implements View.OnClickListener {
    public static String[] STORAGE_PERMISSIONS = {"android.permission.WRITE_EXTERNAL_STORAGE"};
    public static int REQUEST_CODE = 233;


    private IBookManager iBookManager;
    //界面
    private Button btn_add;
    private Button btn_get;
    private TextView tv_info;

    private IOnBookArrivedListener iOnBookArrivedListener = new IOnBookArrivedListener.Stub() {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            Log.e("IOnBookArrivedListener", "onNewBookArrived: " + newBook.toString());
        }
    };

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iBookManager = IBookManager.Stub.asInterface(iBinder);
            try {
                iBookManager.registerListener(iOnBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            iBookManager = null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = findViewById(R.id.id_add);
        btn_get = findViewById(R.id.id_get);
        tv_info = findViewById(R.id.id_info);

        btn_add.setOnClickListener(this);
        btn_get.setOnClickListener(this);

        Intent intent = new Intent();
        intent.setAction("com.nullWolf.learn.service");
        intent.setPackage(getPackageName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);

        permissionsDeal();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_add:
                try {
                    iBookManager.addBook(new Book(666, "新书"));
                    Toast.makeText(this, "添加新书成功", Toast.LENGTH_LONG).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.id_get:
                try {
                    tv_info.setText(iBookManager.getBookList().toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (iOnBookArrivedListener != null) {
            try {
                iBookManager.unRegisterListener(iOnBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (iBookManager != null) {
            unbindService(serviceConnection);
        }
    }

    /**
     * 权限处理
     */
    public void permissionsDeal() {
        MuPermissionsTool.requestPermission(this, REQUEST_CODE, new MuPermissionsTool.PermissionListener() {
            @Override
            public void onPermissionSucceed(int requestCode, List<String> grantedList) {
                Log.e("permissionsDeal", "权限通过");
            }

            @Override
            public void onPermissionFailed(int requestCode, List<String> deniedList) {
                Log.e("permissionsDeal", "失败");
            }

            @Override
            public void onPermissionsDialogCancel() {

            }
        }, STORAGE_PERMISSIONS);
    }
}
