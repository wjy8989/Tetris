package wjy.tetris.view;

/**
 * Created by Administrator on 2017/3/1.
 */

public interface IGameView {
    /** 更新界面与数据*/
    void postInvalidate();
    /** 游戏结束*/
    void finish();
}
