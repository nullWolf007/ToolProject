package com.example.learn.common;

/**
 * 这个类用来定义服务端具有什么样的能力，继承自IInterface才就有跨进程传输的基础能力
 /**
 * Base class for Binder interfaces.  When defining a new interface,
 * you must derive it from IInterface.  Iinterface的说明
 */

import android.os.IInterface;
import android.os.RemoteException;

import com.example.learn.bean.Person;

import java.util.List;

public interface IPersonManager extends IInterface {
    /**
     * 添加人数
     *
     * @throws RemoteException
     */
    void addPerson(Person personBean) throws RemoteException;

    /**
     * 获取人数
     *
     * @throws RemoteException
     */
    List<Person> getPersons() throws RemoteException;
}