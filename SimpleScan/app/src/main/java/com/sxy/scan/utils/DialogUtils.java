package com.sxy.scan.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.sxy.scan.R;


public class DialogUtils {

    public interface OnDialogClickListener {
        void onOkClick();
        void onCancelClick();
    }

    /**
     *  通用弹框
     *
     * @param title        弹框标题
     * @param message      弹框内容
     * @param positive     确定按钮文案
     * @param negative     取消按钮文案
     * @param hideCancel   隐藏取消按钮
     * @param listener
     */
    public static void showDialog(Context context, String title, String message,
                                  String positive, String negative, boolean hideCancel,
                                  final OnDialogClickListener listener) {
        final Dialog dialog = new Dialog(context, R.style.Dialog);

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);

        TextView tvTitle = (TextView) view.findViewById(R.id.dialog_title);
        if (TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setVisibility(View.VISIBLE);
        }
        tvTitle.setText(title);
        ((TextView) view.findViewById(R.id.dialog_message)).setText(message);

        TextView dialog_positive = (TextView) view.findViewById(R.id.dialog_ok);
        if (positive != null) {
            dialog_positive.setText(positive);
        }
        dialog_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.onOkClick();
            }
        });

        TextView dialog_negative = (TextView) view.findViewById(R.id.dialog_cancel);
        if (!TextUtils.isEmpty(negative)) {
            dialog_negative.setText(negative);
        }
        if (hideCancel) {
            dialog_negative.setVisibility(View.GONE);
        }
        dialog_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.onCancelClick();
            }
        });

        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.width = UiUtil.getScreenWidth(context) * 3 / 4;
        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        Window window = dialog.getWindow();
        window.setAttributes(p);
        dialog.show();
    }

    /**
     * 通用弹框
     * @see #showDialog(Context, String, String, String, String, boolean, OnDialogClickListener)
     */
    public static void showDialog(Context context, String title, String message,
                                  String positive, String negative,
                                  final OnDialogClickListener listener) {
        showDialog(context, title, message, positive, negative, false, listener);
    }


}
