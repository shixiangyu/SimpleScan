package com.sxy.scan.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.CaptureFragment;
import com.google.zxing.ScanCallback;
import com.sxy.scan.R;
import com.sxy.scan.utils.StatusBarUtil;
import com.sxy.scan.utils.UiUtil;


/**
 * Initial the camera
 * <p>
 * 默认的二维码扫描Activity
 */
public class CaptureActivity extends AppCompatActivity implements View.OnClickListener {

    private CaptureFragment captureFragment;
    private View mflashLightContainer;
    private ImageView flashLight;
    private TextView lightTip;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CaptureActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_capture);

        Window window = getWindow();
        StatusBarUtil.setStatusBarOverlay(window);
        StatusBarUtil.setStatusBarColor(window, ContextCompat.getColor(this, R.color.transparent));

        findViewById(R.id.back).setOnClickListener(this);
        flashLight = (ImageView) findViewById(R.id.open_light);
        flashLight.setOnClickListener(this);

        mflashLightContainer = findViewById(R.id.scan_light_container);
        lightTip = (TextView) findViewById(R.id.light_tip);
        lightTip.setText("打开闪光灯");
        View topView = findViewById(R.id.top_view);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UiUtil.dp2px(this, 44));
        int offset;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            offset = UiUtil.dp2px(this, 24);
            params.topMargin = offset;
        }
        topView.setLayoutParams(params);

        captureFragment = new CaptureFragment();
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_zxing_container, captureFragment).commit();


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 二维码解析回调函数
     */
    ScanCallback.AnalyzeCallback analyzeCallback = new ScanCallback.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            if (captureFragment.flashIsOpen()) {
                lightTip.setText("打开闪光灯");
                flashLight.setImageResource(R.drawable.ic_scan_light_close);
            }
            ScanTransferActivity.startActivity(CaptureActivity.this,result);
        }

        @Override
        public void onAnalyzeFailed() {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_light:
                captureFragment.switchFlashlight();
                if (captureFragment.flashIsOpen()) {
                    lightTip.setText("关闭闪光灯");
                    flashLight.setImageResource(R.drawable.ic_scan_light_open);
                } else {
                    lightTip.setText("打开闪光灯");
                    flashLight.setImageResource(R.drawable.ic_scan_light_close);
                }
                break;
            case R.id.back:
                finish();
                break;
        }

    }
}