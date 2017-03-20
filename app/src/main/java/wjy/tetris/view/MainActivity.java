package wjy.tetris.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import wjy.tetris.GameApplication;
import wjy.tetris.R;
import wjy.tetris.base.BaseActivity;
import wjy.tetris.config.Config;
import wjy.tetris.dto.GameDto;

/**
 * Created by Administrator on 2017/3/1.
 */

public class MainActivity extends BaseActivity {
    private Button mBtnSound;
    private SeekBar mSeekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnSound = (Button) findViewById(R.id.btn_sound);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mBtnSound.setBackgroundDrawable(GameApplication.getSoundIco()); //设置音效背景
    }
    /**
     * 开始游戏*/
    public void onPlay(View v) {
        GameDto gameDto = new GameDto(Config.MAX_SPEED - Config.SPEED * mSeekBar.getProgress());
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra("dto", gameDto);
        startActivity(intent);
    }
    /**
     * 继续游戏*/
    public void onContinue(View v) {

        GameDto gameDto = GameApplication.getInstance().readFile();
        if (gameDto == null) {
            Toast.makeText(MainActivity.this, "没有存档", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra("dto", gameDto);
        startActivity(intent);
    }

    /**
     * 音乐开关*/
    public void onSound(View v) {
        GameApplication.setAllowMusic(GameApplication.isAllowMusic());
        mBtnSound.setBackgroundDrawable(GameApplication.getSoundIco()); //设置音效背景
    }
    /**
     * 退出游戏*/
    public void onExit(View v) {
        System.exit(0);
    }
    /**
     * 页面重新被载入时*/
    @Override
    protected void onResume() {
        super.onResume();
        mBtnSound.setBackgroundDrawable(GameApplication.getSoundIco()); //设置音效背景
    }
}
