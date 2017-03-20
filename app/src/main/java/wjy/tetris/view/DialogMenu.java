package wjy.tetris.view;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;

import wjy.tetris.GameApplication;
import wjy.tetris.R;
import wjy.tetris.base.BaseActivity;

/**
 * Created by Administrator on 2017/3/2.
 * 暂停菜单
 */

public class DialogMenu implements View.OnClickListener{
    private GameActivity mActivity;
    private Dialog dialog;
    private Button[] mButtons;

    public DialogMenu(GameActivity activity) {
        this.mActivity = activity;
        dialog = new Dialog(mActivity, R.style.dialog);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(false);    //点击其他位置不会消失
        mButtons = new Button[3];
        mButtons[0] = (Button) dialog.findViewById(R.id.btn_sound);
        mButtons[1] = (Button) dialog.findViewById(R.id.btn_resume);
        mButtons[2] = (Button) dialog.findViewById(R.id.btn_main);
        mButtons[0].setBackgroundDrawable(GameApplication.getSoundIco());
        for (Button b : mButtons)
            b.setOnClickListener(this); //添加监听
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }
    /**
     * 音乐开关 */
    private void onClickSound() {
        GameApplication.setAllowMusic(GameApplication.isAllowMusic());
        mButtons[0].setBackgroundDrawable(GameApplication.getSoundIco());
    }
    /**
     * 继续游戏 */
    private void onClickResume() {
        mActivity.setPause(false);
        dismiss();
    }
    /**
     * 返回主菜单 */
    private void onClickBackMain() {
        mActivity.finish();
        dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sound: onClickSound();break;
            case R.id.btn_resume: onClickResume();break;
            default:onClickBackMain();
        }
    }
}
