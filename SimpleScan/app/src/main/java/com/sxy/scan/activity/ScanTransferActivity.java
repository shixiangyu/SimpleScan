package com.sxy.scan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sxy.scan.R;
import com.sxy.scan.utils.DialogUtils;

/**
 * 半透明中转界面，用于专门处理扫描结果
 * Created by shixiangyu on 2017/6/21.
 */

public class ScanTransferActivity extends AppCompatActivity {


    private String mCodeInfo;

    public static void startActivity(Context context, String codeInfo) {
        Intent intent = new Intent(context, ScanTransferActivity.class);
        intent.putExtra("code_info", codeInfo);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_transfer);
        overridePendingTransition(0, 0);

        mCodeInfo = getIntent().getStringExtra("code_info");

        dealScanInfo(mCodeInfo);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    private void dealScanInfo(final String resultInfo) {
        if (resultInfo == null) {
            return;
        }

        DialogUtils.showDialog(ScanTransferActivity.this,
                "提示",
                "扫描结果:"+resultInfo,
                "确定",
                "继续扫",
                new DialogUtils.OnDialogClickListener() {
                    @Override
                    public void onOkClick() {
                        finish();
                    }

                    @Override
                    public void onCancelClick() {
                        finish();
                    }
                });
    }
}
