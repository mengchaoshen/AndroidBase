package com.smc.androidbase.mvp;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/10/15
 * @description
 */

public class MyPresenter {

    IView mIView;

    public MyPresenter(IView view) {
        this.mIView = view;
    }

    public void getData(){
        if(null != mIView){
            mIView.onGetDataSuccess("");
        }
    }
}
