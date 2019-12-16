package com.nullWolf.learn;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;

public class MessengerService extends Service {

    private static final String TAG = "MessengerService";

    public MessengerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    private static class MessagerHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    Log.d(TAG, "receive msg from Client: " + msg.getData().getString("msg"));
                    Messenger clientMessenger = msg.replyTo;
                    Message replyMessage = Message.obtain(null, 2);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply", "你好，我已经收到你的消息，待会回复");
                    replyMessage.setData(bundle);
                    try {
                        clientMessenger.send(replyMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    }

    private final Messenger messenger = new Messenger(new MessagerHandler());
}
