package com.smc.androidbase.serializable;

import android.os.Parcel;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/8/27
 * @description
 */

public class Parcelable implements android.os.Parcelable {

    //实现Parcelable接口
    //1.需要自己重写write和read的方法，以及自己定义一个CREATOR，比较复杂，但是这样自由度更高
    //2.Parcelable是内存中的读写，所以效率会比较高

    private String name;
    private int age;


    protected Parcelable(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Parcelable> CREATOR = new Creator<Parcelable>() {
        @Override
        public Parcelable createFromParcel(Parcel in) {
            return new Parcelable(in);
        }

        @Override
        public Parcelable[] newArray(int size) {
            return new Parcelable[size];
        }
    };
}
