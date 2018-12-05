package com.smc.androidbase;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;


/**
 * @createTime： 2018/5/23
 * @author：yangxd
 * @desc：数字键盘
 **/
public class NumberKeyBoardUtil {

    private KeyboardView keyboardView;
    private Keyboard keyboard;

    private EditText ed;

    private static class KeyboardHolder {
        private static final NumberKeyBoardUtil sInstance = new NumberKeyBoardUtil();
    }

    public static NumberKeyBoardUtil getInstance() {
        return KeyboardHolder.sInstance;
    }

    private NumberKeyBoardUtil() {
    }

    public NumberKeyBoardUtil initKeyBoard(KeyboardView view, Context ctx, EditText edit) {
        keyboard = new Keyboard(ctx, R.xml.dish_discount_keyboard);
        this.ed = edit;
        keyboardView = view;
        keyboardView.setKeyboard(keyboard);
        keyboardView.setEnabled(true);
        keyboardView.setPreviewEnabled(false);
        keyboardView.setOnKeyboardActionListener(listener);
        return this;
    }

    public void changeFocus(EditText edit) {
        this.ed = edit;
    }

    public void removeFocus() {
        this.ed = null;
    }

    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void swipeUp() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            if (ed == null){
                return;
            }
            Editable editable = ed.getText();
            int start = ed.getSelectionStart();
            int end = ed.getSelectionEnd();
            if (primaryCode == Keyboard.KEYCODE_CANCEL) {
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
            } else {
                //小数点只能有一个
                if (primaryCode == 46 && ed.getText().toString().contains(".")) {
                    return;
                }
                if(start < end) {
                    editable.delete(start, end);
                }
                editable.insert(start, Character.toString((char) primaryCode));
            }
            ed.setSelection(ed.length());
        }
    };

    public void showKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            keyboardView.setVisibility(View.VISIBLE);
        }
    }
}
