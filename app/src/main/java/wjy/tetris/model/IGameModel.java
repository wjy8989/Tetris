package wjy.tetris.model;

import android.graphics.Point;

/**
 * Created by Administrator on 2017/3/1.
 */

public interface IGameModel {
    /**
     * 方块旋转 */
    void onRotate();
    /**
     * 方块移动
     * @param moveX 目标位置距当前方块的 x 坐标
     * @param moveY 目标位置距当前方块的 y 坐标*/
    boolean onMove(int moveX, int moveY);
    /**
     * 方块移动
     * @param moveY 目标位置距当前方块的 y 坐标*/
    boolean onMove(int moveY);
    /** 游戏是否结束*/
    boolean isOver();

}
