package wjy.tetris.view;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import wjy.tetris.GameApplication;
import wjy.tetris.R;
import wjy.tetris.base.BaseActivity;

/**
 * Created by Administrator on 2017/3/16.
 */

public class LogoActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(LogoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        timer.schedule(task, 1000);
    }
    @Override
    protected void onStart() {
        super.onStart();
        GameApplication.destroyMusic();
    }
}
