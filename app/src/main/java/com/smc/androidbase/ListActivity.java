package com.smc.androidbase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class ListActivity extends Activity implements View.OnClickListener {

    List<ItemBean> list;
    ListAdapter adapter;
    ListView listView;
    KeyboardView mKeyboardView;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, ListActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setContentView(R.layout.activity_list);
        listView = findViewById(R.id.list_view);
        TextView tvAdd = findViewById(R.id.tv_add);
        TextView tvDelete = findViewById(R.id.tv_delete);
        mKeyboardView = findViewById(R.id.kv_change);
        tvAdd.setOnClickListener(this);
        tvDelete.setOnClickListener(this);


        list = new ArrayList<>();
        list.add(new ItemBean("a"));
        list.add(new ItemBean("b"));
        list.add(new ItemBean("c"));
        list.add(new ItemBean("d"));
        list.add(new ItemBean("e"));
        adapter = new ListAdapter(this, list);
        adapter.setKey(mKeyboardView);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add:
                list.add(new ItemBean("q"));
                adapter = new ListAdapter(this, list);
                listView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_delete:
                list.remove(list.size() - 1);
//                adapter.notifyDataSetChanged();
                adapter = new ListAdapter(this, list);
                listView.setAdapter(adapter);
                break;
            default:
                break;
        }
    }
}
