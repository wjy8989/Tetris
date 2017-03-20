package wjy.tetris.dto;

import java.io.Serializable;

import wjy.tetris.config.Config;

/**
 * Created by Administrator on 2017/3/1.
 * 游戏数据类
 */

public class GameDto implements Serializable{
    private static final long serialVersionUID = 01273700420565637063147L;

    private static final int[] LINES = {0, 20, 60, 100, 140};

    private boolean isOver;
    private boolean isPause;

    /** 游戏地图*/
    private int[][] gameMap;
    /** 当前方块所有坐标点*/
    private BlockPoint[] blockPoints;
    /** 当前方块编号*/
    private int currentBlockId;
    /** 下一个方块编号*/
    private int nextBlockId = -1;
    /** 分数*/
    private int score;
    /** 消行数*/
    private int line;
    /** 游戏速度*/
    private int speed;

    public GameDto(int speed) {
        super();
        this.speed   = speed;
        this.gameMap = new int[Config.MAX_X][Config.MAX_Y];
    }

    public int[][] getGameMap() {
        return gameMap;
    }

    public int getScore() {
        return score;
    }
    /**
     * 加分
     * @param i 消行数*/
    public void setScore(int i) {

        this.score += LINES[i];
    }

    public int getLine() {
        return line;
    }

    public void setLine(int i) {
        this.line += i;
    }

    public BlockPoint[] getBlockPoints() {
        return blockPoints;
    }

    public void setBlockPoints(BlockPoint[] blockPoints) {
        this.blockPoints = blockPoints;
    }

    public int getCurrentBlockId() {
        return currentBlockId;
    }

    public void setCurrentBlockId(int currentBlockId) {
        this.currentBlockId = currentBlockId;
    }

    public int getNextBlockId() {
        return nextBlockId;
    }

    public void setNextBlockId(int nextBlockId) {
        this.nextBlockId = nextBlockId;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public boolean isPause() {
        return isPause;
    }

    public void setPause(boolean pause) {
        isPause = pause;
    }
}
