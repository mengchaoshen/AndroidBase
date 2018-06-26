package com.smc.androidbase.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smc.androidbase.R;

/**
 * @author shenmengchao
 * @version 1.0.0
 * @date 2018/5/30
 * @description
 */

public class MyFragment1 extends Fragment {

    private ImageView mImageView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("MyFragment1", "onAttach()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MyFragment1", "onCreate()");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //onCreateView()回调里面，Activity可能还没有创建出来，还不能在这里与Activity进行交互
        Log.d("MyFragment1", "onCreateView()");
        View view = inflater.inflate(R.layout.item_fragment_red, container, false);
        mImageView = view.findViewById(R.id.iv_icon);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageView.setVisibility(View.GONE);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //这个时候Activity已经创建成功，可以在这里与Activity进行交互
        super.onActivityCreated(savedInstanceState);
        Log.d("MyFragment1", "onActivityCreated()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("MyFragment1", "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("MyFragment1", "onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("MyFragment1", "onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("MyFragment1", "onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("MyFragment1", "onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyFragment1", "onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("MyFragment1", "onDetach()");
    }
}
