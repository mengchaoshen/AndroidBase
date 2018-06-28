package com.smc.androidbase.iterable;

import android.support.annotation.NonNull;

import java.util.Iterator;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/6/27
 * @description
 */

public class MyIterable<T> implements Iterable<T> {

    private Object[] array = new Object[10];
    private int size = 0;
    private int current = 0;

    public void add(T t) {
        array[size] = t;
        size++;
    }

    @NonNull
    @Override
    public Iterator<T> iterator() {
        return new MyIterator<T>();
    }

    class MyIterator<T> implements Iterator<T> {

        @Override
        public boolean hasNext() {
            if(current < size){
                return true;
            }
            return false;
        }

        @Override
        public T next() {
            return (T)array[current++];
        }
    }
}
