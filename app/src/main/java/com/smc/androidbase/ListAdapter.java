package com.smc.androidbase;

import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class ListAdapter extends BaseHolderAdapter<ItemBean> {

    private final static String TAG = ListAdapter.class.getSimpleName();
    private KeyboardView mKeyboardView;
    private Context mContext;

    public ListAdapter(Context context, List<ItemBean> list) {
        super(context, list);
        this.mContext = context;
    }

    public void setKey(KeyboardView key) {
        mKeyboardView = key;
    }

    @Override
    public int getContentView(int position) {
        return R.layout.item_edittext;
    }

    @Override
    public void onInitView(View view, final int position) {
        final EditText editText = get(view, R.id.et);
        TextView textView = get(view, R.id.tv_add);
        final ItemBean item = getItem(position);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                item.body = item.body + "1";
//                editText.setText(item.body);

                editText.selectAll();
                notifyDataSetChanged();
            }
        });
        disableShowInput(editText);
        editText.setText(item.body);
//        editText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NumberKeyBoardUtil.getInstance().initKeyBoard(mKeyboardView, mContext, editText).showKeyboard();
//            }
//        });
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    editText.selectAll();
                    editText.setSelectAllOnFocus(true);
//                    editText.setSelection(0, editText.length());
                    NumberKeyBoardUtil.getInstance().initKeyBoard(mKeyboardView, mContext, editText).showKeyboard();
                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, "position=" + position + ",  s:" + s.toString());
            }
        });
    }

    /**
     * 禁止弹出系统键盘，且有光标显示
     */
    private void disableShowInput(EditText result) {
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            result.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(result, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(result, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
