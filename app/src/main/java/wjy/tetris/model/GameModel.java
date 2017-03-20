package wjy.tetris.model;

import android.util.Log;

import java.util.Random;

import wjy.tetris.GameApplication;
import wjy.tetris.config.Config;
import wjy.tetris.dto.BlockPoint;
import wjy.tetris.dto.GameDto;

/**
 * Created by Administrator on 2017/3/1.
 * 游戏逻辑
 */

public class GameModel implements IGameModel {
    private BlockPoint[][] mBlocks;
    private boolean[] mIsRotate;
    private GameDto mGameDto;
    private Random mRandom;

    private int mLine;


    public GameModel(GameDto gameDto) {
        super();
        this.mGameDto  = gameDto;
        this.mBlocks   = Config.getInstance().getBlocks();
        this.mIsRotate = Config.getInstance().getIsRotate();
        this.mRandom    = new Random();
        if (mGameDto.getNextBlockId() != -1)
            return;
        mGameDto.setNextBlockId(mRandom.nextInt(mBlocks.length));   //  随机出下一个方块id
        createBlock();  //产生新方块
    }
    /**
     * 产生新方块 */
    public void createBlock() {
        mGameDto.setCurrentBlockId(mGameDto.getNextBlockId());              //将下一个方块id 赋给 当前方块的id
        mGameDto.setNextBlockId(mRandom.nextInt(mBlocks.length));   //随机出下一个方块的id
        BlockPoint[] temp = mBlocks[mGameDto.getCurrentBlockId()];      //临时变量 从配置中获得 当前id 的方块坐标点
        BlockPoint[] points = new BlockPoint[temp.length];
        for (int i = 0; i < points.length; i++) {           //遍历所有坐标点
            points[i] = new BlockPoint(temp[i].x, temp[i].y);    //复制临时变量
        }
        mGameDto.setBlockPoints(points);    //设置当前方块的坐标点
        int count = mRandom.nextInt(4);      //初始化时 随机旋转方块
        for (int i = 0; i < count; i++)
            onRotate();
    }
    /**
     * 方块旋转 */
    @Override
    public void onRotate() {
        /**
         *  A.x = O.y + O.x - B.y
         *  A.y = O.y - O.x + B.x
         */
        if (!mIsRotate[mGameDto.getCurrentBlockId()]) {//当前方块是否可以旋转 isRotate 不为 true ，判定成功
            return;//结束
        }
        int[][] map    = mGameDto.getGameMap();
        BlockPoint[] points = mGameDto.getBlockPoints();
        for (int i = 0; i < points.length; i++) {                   //遍历当前方块
            int tempX = points[0].y + points[0].x - points[i].y;    //临时记录方块 x 坐标
            int tempY = points[0].y - points[0].x + points[i].x;    //临时记录方块 y 坐标
            if (isOverZone(map, tempX, tempY)) {                    //判定 目标位置是否产生碰撞 true 判定成功
                return;//结束
            }
        }
        for (int i = 0; i < points.length; i++) {                   //遍历当前方块
            int tempX = points[0].y + points[0].x - points[i].y;    //临时记录方块 x 坐标
            int tempY = points[0].y - points[0].x + points[i].x;    //临时记录方块 y 坐标
            points[i].x = tempX;    //改变方块 x 坐标点
            points[i].y = tempY;    //改变方块 y 坐标点
        }
    }
    /**
     * 方块移动 */
    @Override
    public boolean onMove(int moveX, int moveY) {
        int[][] map    = mGameDto.getGameMap();
        BlockPoint[] points = mGameDto.getBlockPoints();
        for (BlockPoint p : points) {                    //遍历当前方块
            int tempX = p.x + moveX;                //临时记录方块 x 坐标
            int tempY = p.y + moveY;                //临时记录方块 y 坐标
            if (isOverZone(map, tempX, tempY)) {    //判定 目标位置是否产生碰撞 true 判定成功
                return false;
            }
        }
        for (BlockPoint p : points) {    //遍历当前方块
            p.x += moveX;           //改变坐标 x 值
            p.y += moveY;           //改变坐标 y 值
        }
        GameApplication.getInstance().saveGame(mGameDto);
        return true;
    }
    /**
     * 向下移动 */
    @Override
    public boolean onMove(int moveY) {
        if (onMove(0, moveY))   //true 移动成功 false 移动失败
            return false;
        int[][] map    = mGameDto.getGameMap();     //获取地图
        BlockPoint[] points = mGameDto.getBlockPoints(); //获取方块坐标点
        for(BlockPoint p : points) {                     //遍历方块所有坐标点
            map[p.x][p.y] = mGameDto.getCurrentBlockId() + 1;   //将坐标点记录到地图上 值为当前方块的 id
        }
        isLine();   //消行操作
        createBlock();  //产生新方块
        GameApplication.getInstance().saveGame(mGameDto);
        if (isOver())
            return true;
        return false;
    }
    /**
     * 消行操作 */
    public boolean isLine() {
        int[][] map = mGameDto.getGameMap();    //获取地图
        scan:
        for(int y = Config.MAX_Y - 1; y >= 0; y--){     //遍历 y 坐标
            for(int x = Config.MAX_X - 1; x >= 0; x--){ //遍历 x 坐标
                if(map[x][y] == 0){     //如果 y 行中 有 0  则跳到 这层循环
                    continue scan;
                }
            }
            for(int tempY = y; tempY > 0; tempY--){                 //遍历 y 坐标
                for(int tempX = 0; tempX < Config.MAX_X; tempX++){  //遍历 x 坐标
                    map[tempX][tempY] = map[tempX][tempY - 1];      //map[x][y - 1]的值 赋给 map[x][y]
                }
                if(tempY == 1){   //当遍历到最后一行时
                    mLine ++;
                    isLine(); //递归 重新遍历 直到没有可消除的行
                }
            }
        }
        mGameDto.setScore(mLine); //加分
        mGameDto.setLine(mLine);   //加消行数
        mLine = 0;
        return true;
    }

    /**
     * 游戏结束*/
    public boolean isOver() {
        int[][] map = mGameDto.getGameMap();
        BlockPoint[] points = mGameDto.getBlockPoints();
        for(BlockPoint p : points){
            if(map[p.x][p.y] != 0){
                GameApplication.getInstance().destroySave();
                return true;
            }
        }
        return false;
    }
    /**
     * 边界判断*/
    private boolean isOverZone(int [][] map,int x,int y){
        // x 不小于 0 不大于 地图 x 最大值； y 不小于 0 不大于 地图 y 最大值；地图当前坐标内的值等于0 （0表示没有方块）
        return x < 0 || x > Config.MAX_X - 1 || y < 0 || y > Config.MAX_Y - 1 || map[x][y] != 0;
    }
}
