// IBookManager.aidl
package com.nullWolf.learn;

import com.nullWolf.learn.Book;
import com.nullWolf.learn.IOnBookArrivedListener;

// Declare any non-default types here with import statements

interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

            void addBook(in Book book);

            List<Book> getBookList();

            void registerListener(IOnBookArrivedListener listener);

            void unRegisterListener(IOnBookArrivedListener listener);
}
