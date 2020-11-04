package com.example.learn.common;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.example.learn.bean.Person;

import java.util.List;

/**
 * 远程代理类
 */
public class Proxy implements IPersonManager {
    /**
     * 远程binder对象
     */
    IBinder remote;
    private static final String DESCRIPTOR = "com.example.learn.common.IPersonManager";

    /**
     * 构造方法传入ibinder
     *
     * @param remote
     */
    public Proxy(IBinder remote) {
        this.remote = remote;
    }

    public String getInterfaceDescriptor() {
        return DESCRIPTOR;
    }

    @Override
    public void addPerson(Person personBean) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel replay = Parcel.obtain();
        try {
            data.writeInterfaceToken(DESCRIPTOR);
            if (personBean != null) {
                data.writeInt(1);
                personBean.writeToParcel(data, 0);
            } else {
                data.writeInt(0);
            }
            remote.transact(Stub.TRANSAVTION_addperson, data, replay, 0);
            replay.readException();
        } finally {
            replay.recycle();
            data.recycle();
        }
    }

    @Override
    public List<Person> getPersons() throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel replay = Parcel.obtain();
        List<Person> result;
        try {
            data.writeInterfaceToken(DESCRIPTOR);
            remote.transact(Stub.TRANSAVTION_getpersons, data, replay, 0);
            replay.readException();
            result = replay.createTypedArrayList(Person.CREATOR);
        } finally {
            replay.recycle();
            data.recycle();
        }
        return result;
    }

    @Override
    public IBinder asBinder() {
        return remote;
    }
}