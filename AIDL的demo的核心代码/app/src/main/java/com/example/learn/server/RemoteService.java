package com.example.learn.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.learn.bean.Person;
import com.example.learn.common.Stub;

import java.util.ArrayList;
import java.util.List;

public class RemoteService extends Service {
    private List<Person> persons;

    public RemoteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        persons = new ArrayList<>();
        Log.e("RemoteService", "onBind");
        return iBinder;
    }

    private IBinder iBinder = new Stub() {
        @Override
        public void addPerson(Person personBean) throws RemoteException {
            persons.add(personBean);
        }

        @Override
        public List<Person> getPersons() throws RemoteException {
            return persons;
        }
    };
}
