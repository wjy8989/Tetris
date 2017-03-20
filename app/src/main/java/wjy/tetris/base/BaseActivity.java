package wjy.tetris.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import wjy.tetris.GameApplication;

/**
 * Created by Administrator on 2017/3/1.
 * 基类
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GameApplication.destroyMusic();
        GameApplication.startMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GameApplication.resumeMusic();
    }
    @Override
    protected void onPause() {
        super.onPause();
        GameApplication.pauseMusic();
    }
}
