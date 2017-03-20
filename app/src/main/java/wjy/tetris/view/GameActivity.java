package wjy.tetris.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import wjy.tetris.R;
import wjy.tetris.base.BaseActivity;
import wjy.tetris.base.GameCanvas;
import wjy.tetris.config.Config;
import wjy.tetris.dto.GameDto;
import wjy.tetris.presenter.GamePresenter;

/**
 * Created by Administrator on 2017/3/1.
 */

public class GameActivity extends BaseActivity implements IGameView, View.OnLongClickListener {


    private static final int LONG_CLICK = 1;
    private static final int UP_UI = 2;
    private View mView;

    private RefreshHandler mRefreshHandler;

    private DialogMenu dialogMenu;
    private GamePresenter mGamePresenter;
    /** 数据传输对象*/
    private GameDto mGameDto;
    /** 游戏画布 */
    private GameCanvas mGameCanvas;
    /** 下一个方块 */
    private ImageView mNextImage;
    /** 分数 消行数 */
    private TextView mScoreText, mLineText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mRefreshHandler = new RefreshHandler();
        mGameDto = (GameDto) getIntent().getSerializableExtra("dto");
        initActivity();
    }
    /** 初始化*/
    private void initActivity() {
        mNextImage = (ImageView) findViewById(R.id.next_ico);
        mScoreText = (TextView) findViewById(R.id.txt_score);
        mLineText = (TextView) findViewById(R.id.txt_line);
        mGameCanvas = (GameCanvas) findViewById(R.id.game_map);
        mGamePresenter = new GamePresenter(this, mGameDto);
        dialogMenu = new DialogMenu(this);
//        长按
        findViewById(R.id.btn_left).setOnLongClickListener(this);
        findViewById(R.id.btn_right).setOnLongClickListener(this);
        findViewById(R.id.btn_down).setOnLongClickListener(this);
        findViewById(R.id.btn_rotate).setOnLongClickListener(this);
    }

    /** 左移方块*/
    public void onLeftMove(View v) {
        mGamePresenter.onMove(-1, 0);

    }
    /** 右移方块*/
    public void onRightMove(View v) {
        mGamePresenter.onMove(1, 0);
    }
    /** 下移方块*/
    public void onDownMove(View v) {
        mGamePresenter.onMove(1);
    }
    /** 旋转方块*/
    public void onRotate(View v) {
        mGamePresenter.onRotate();
    }

    public void onPause(View v) {
        setPause(true);
        dialogMenu.show();
    }

    public void setPause(boolean bn) {
        mGameDto.setPause(bn);
    }

    @Override
    public void finish(){
        super.finish();
    }

    /** 更新界面*/
    @Override
    public void postInvalidate() {
        mRefreshHandler.sendEmptyMessage(UP_UI);
//        GameActivity.this.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                // 更新数据
//                mLineText.setText(String.valueOf(mGameDto.getLine()));
//                mScoreText.setText(String.valueOf(mGameDto.getScore()));
//                mNextImage.setBackgroundDrawable(getResources().getDrawable(R.mipmap.block_ico_0 + mGameDto.getNextBlockId()));
//                //更新画布上的数据
//                mGameCanvas.upGameView(mGameDto.getGameMap(), mGameDto.getBlockPoints(), mGameDto.getCurrentBlockId());
//                //刷新画布
//                mGameCanvas.postInvalidate();
//            }
//        });

    }

    /**
     * 返回键*/
    @Override
    public void onBackPressed() {
        setPause(true);
        dialogMenu.show();
    }
    /**
     * 长按 事件 */
    @Override
    public boolean onLongClick(View view) {
        this.mView = view;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mView.isPressed()) {
                    try {
                        Thread.sleep(100);
                        //使用handler 有延迟
                        GameActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mView.performClick();
                                //更新画布上的数据
                                mGameCanvas.upGameView(mGameDto.getGameMap(), mGameDto.getBlockPoints(), mGameDto.getCurrentBlockId());
                                //刷新画布
                                mGameCanvas.postInvalidate();
                            }
                        });
//                        mRefreshHandler.sendEmptyMessage(LONG_CLICK);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        return false;
    }

    /**
     * 内部类 更新 ui */
    public class RefreshHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LONG_CLICK: mView.performClick();break;
                    default:
                        // 更新数据
                        mLineText.setText(String.valueOf(mGameDto.getLine()));
                        mScoreText.setText(String.valueOf(mGameDto.getScore()));
                        mNextImage.setBackgroundDrawable(getResources().getDrawable(R.mipmap.block_ico_0 + mGameDto.getNextBlockId()));
                        //更新画布上的数据
                        mGameCanvas.upGameView(mGameDto.getGameMap(), mGameDto.getBlockPoints(), mGameDto.getCurrentBlockId());
                        //刷新画布
                        mGameCanvas.postInvalidate();
            }
        }
    }
}
