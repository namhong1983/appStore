package com.meiliwu.installer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.meiliwu.installer.utils.DisplayUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenying on 2018/4/23.
 */

public class WelcomeActivity extends AppCompatActivity {

    @BindView(R.id.lr_icon)
    ImageView loginImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                finish();
                return;
            }
        }
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) loginImageView.getLayoutParams();
        lp.setMargins(0, (int) (DisplayUtil.getWindowHight(this) * 0.3), 0, 0);
        loginImageView.setLayoutParams(lp);

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
