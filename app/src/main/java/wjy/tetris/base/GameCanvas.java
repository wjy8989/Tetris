package wjy.tetris.base;

;import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import wjy.tetris.config.Config;
import wjy.tetris.dto.BlockPoint;

/**
 * Created by Administrator on 2017/3/1.
 * 游戏画布
 */

public class GameCanvas extends View {
    /** 方块大小*/
    private int mBlockSize;
    /** 边距*/
    public static int marX;
    public static int marY;
    /** 游戏地图*/
    private int[][] mMap = new int[0][0];
    /** 方块坐标*/
    private BlockPoint[] mBlockPoint = new BlockPoint[0];
    /** 当前方块编号*/
    private int mCurrentBlockId;
    /** 配置文件*/
    private Config mConfig;

    public GameCanvas(Context context) {
        super(context);
        this.init();
    }

    public GameCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public GameCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        mConfig = Config.getInstance();
    }

    public void upGameView(int[][] map, BlockPoint[] blockPoint, int currentBlockId) {
        this.mMap   = map;
        this.mBlockPoint = blockPoint;
        this.mCurrentBlockId = currentBlockId;
    }
    /** 绘制游戏*/
    @Override
    public void onDraw(Canvas canvas) {
        drawMap(canvas);
        drawCurrentBlock(canvas);
    }
    /** 绘制地图*/
    private void drawMap(Canvas canvas) {
        for (int x = 0; x < mMap.length; x++) {
            for (int y = 0; y <mMap[x].length; y++) {
                Rect rect = new Rect(marX + x * mBlockSize, marY + y * mBlockSize,
                        marX + x * mBlockSize + mBlockSize, marY + y * mBlockSize + mBlockSize);
                canvas.drawBitmap(mConfig.getBlockBitmap(mMap[x][y]), null, rect, null);
            }
        }
    }
    /** 绘制当前方块*/
    private void drawCurrentBlock(Canvas canvas) {
        Bitmap bitmap = mConfig.getBlockBitmap(mCurrentBlockId + 1);
        for (BlockPoint p : mBlockPoint) {
            Rect rect = new Rect(marX + p.x * mBlockSize, marY + p.y * mBlockSize,
                    marX + p.x * mBlockSize + mBlockSize, marY + p.y * mBlockSize + mBlockSize);
            canvas.drawBitmap(bitmap, null, rect, null);
        }
    }

    /**
     * 当 view 大小发生改变时*/
    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        mBlockSize = ( w << 1 > h ) ? h / Config.MAX_Y : w / Config.MAX_X;
        marX = w - mBlockSize *  Config.MAX_X >> 1;      // x 坐标起始点y
        marY = h - mBlockSize *  Config.MAX_Y >> 1;     // y 坐标起始点
    }

}
