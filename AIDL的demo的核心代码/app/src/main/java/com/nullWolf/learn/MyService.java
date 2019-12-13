package com.nullWolf.learn;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class MyService extends Service {
    private AtomicBoolean isDestory = new AtomicBoolean();
    private CopyOnWriteArrayList<Book> bookList = new CopyOnWriteArrayList<>();//比ArrayList安全
    private RemoteCallbackList<IOnBookArrivedListener> listenerList = new RemoteCallbackList<>();//用于删除跨进程listener的接口数据结构

    private IBookManager.Stub binder = new IBookManager.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void addBook(Book book) throws RemoteException {
            if (!bookList.contains(book)) {
                bookList.add(book);
            }
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }

        @Override
        public void registerListener(IOnBookArrivedListener listener) throws RemoteException {
            listenerList.register(listener);
        }

        @Override
        public void unRegisterListener(IOnBookArrivedListener listener) throws RemoteException {
            listenerList.unregister(listener);
        }


    };

    private void onBookArrived(Book book) throws RemoteException {
        bookList.add(book);
        //遍历方式和ArrayList方式不一样
        final int N = listenerList.beginBroadcast();
        for (int i = 0; i < N; i++) {
            IOnBookArrivedListener listener = listenerList.getBroadcastItem(i);
            if (listener != null) {
                listener.onNewBookArrived(book);
            }
        }
        listenerList.finishBroadcast();
    }

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        //为了安全，添加权限。保证只有该权限的才能bind
        int check = checkCallingOrSelfPermission("android.permission.servertest");
        if (check == PackageManager.PERMISSION_DENIED) {
            Log.e("MyService", "onBind: " + "权限被拒绝");
            return null;
        }
        return binder;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        bookList.add(new Book(1, "书本1"));
        bookList.add(new Book(2, "书本2"));
        new Thread(new addBookRunnable()).start();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isDestory.set(true);
    }

    private class addBookRunnable implements Runnable {
        @Override
        public void run() {
            while (!isDestory.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int id = bookList.size() + 1;
                Book newBook = new Book(id, "新书" + id);

                try {
                    onBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
