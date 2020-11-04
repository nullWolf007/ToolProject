package com.example.learn.common;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.example.learn.bean.Person;

import java.util.List;

/**
 * 继承自binder实现了PersonManager的方法，说明它可以跨进程传输，并可进行服务端相关的数据操作
 */
public abstract class Stub extends Binder implements IPersonManager {
    private static final String DESCRIPTOR = "com.example.learn.common.IPersonManager";

    public Stub() {
        this.attachInterface(this, DESCRIPTOR);
    }

    public static IPersonManager asInterface(IBinder binder) {
        if (binder == null) {
            return null;
        }
        IInterface iin = binder.queryLocalInterface(DESCRIPTOR);//通过DESCRIPTOR查询本地binder，如果存在则说明调用方和service在同一进程间，直接本地调用
        if (iin != null && iin instanceof IPersonManager) {
            return (IPersonManager) iin;
        }
        return new Proxy(binder);//本地没有，返回一个远程代理对象
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case INTERFACE_TRANSACTION:
                reply.writeString(DESCRIPTOR);
                return true;
            case TRANSAVTION_getpersons:
                data.enforceInterface(DESCRIPTOR);
                List<Person> result = this.getPersons();
                reply.writeNoException();
                reply.writeTypedList(result);
                return true;
            case TRANSAVTION_addperson:
                data.enforceInterface(DESCRIPTOR);
                Person arg0 = null;
                if (data.readInt() != 0) {
                    arg0 = Person.CREATOR.createFromParcel(data);
                }
                this.addPerson(arg0);
                reply.writeNoException();
                return true;

        }
        return super.onTransact(code, data, reply, flags);
    }

    public static final int TRANSAVTION_getpersons = IBinder.FIRST_CALL_TRANSACTION;
    public static final int TRANSAVTION_addperson = IBinder.FIRST_CALL_TRANSACTION + 1;
}

