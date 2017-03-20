package wjy.tetris.config;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import wjy.tetris.GameApplication;
import wjy.tetris.R;
import wjy.tetris.dto.BlockPoint;

/**
 * Created by Administrator on 2017/3/1.
 * 配置类
 */

public class Config {
    public static final Config sConfig = new Config(GameApplication.getInstance().getApplicationContext());
    /** 最慢速度*/
    public static final int MAX_SPEED = 800;
    /** 点与点之间的速度差*/
    public static final int SPEED = 60;
    /** x 最大坐标*/
    public static final int MAX_X = 14;
    /** y 最大坐标*/
    public static final int MAX_Y = 20;
    /** 所有类型方块*/
    private BlockPoint[][] mBlocks;
    /** 是否允许旋转*/
    private boolean[] isRotate;
    /** 方块图片资源*/
    private Bitmap[] blockBitmap;

    public static Config getInstance() {
        return sConfig;
    }
    /**
     * 私有化构造函数 禁止实例化*/
    private Config(Context context) {
        super();
        this.initBlock();
        this.initBlockBitmap(context);
    }
    /**
     * 初始化 方块游戏数据*/
    private void initBlock() {
        mBlocks = new BlockPoint[][] {
                { new BlockPoint(5, 1), new BlockPoint(5, 0), new BlockPoint(6, 0), new BlockPoint(5, 2)},
                { new BlockPoint(6, 1), new BlockPoint(5, 0), new BlockPoint(6, 0), new BlockPoint(6, 2)},
                { new BlockPoint(5, 1), new BlockPoint(6, 1), new BlockPoint(5, 0), new BlockPoint(6, 2)},
                { new BlockPoint(6, 0), new BlockPoint(5, 0), new BlockPoint(7, 0), new BlockPoint(8, 0)},
                { new BlockPoint(6, 1), new BlockPoint(5, 1), new BlockPoint(6, 0), new BlockPoint(5, 2)},
                { new BlockPoint(6, 1), new BlockPoint(6, 0), new BlockPoint(5, 1), new BlockPoint(7, 1)},
                { new BlockPoint(5, 0), new BlockPoint(6, 0), new BlockPoint(5, 1), new BlockPoint(6, 1)}
        };
        isRotate = new boolean[]{true, true, true, true, true, true, false};
    }
    /**
    * 初始化所有方块图片*/
    private void initBlockBitmap(Context context) {
        blockBitmap = new Bitmap[mBlocks.length + 1];
        for (int i = 0; i < blockBitmap.length; i++) {
            blockBitmap[i] = drawableToBitmap(context, i);
        }
    }
    /**
     * 获取所有类型方块*/
    public BlockPoint[][] getBlocks() {
        return mBlocks;
    }
    /**
     * 获取方块能否移动*/
    public boolean[] getIsRotate() {
        return isRotate;
    }
    /**
     * 获取方块图片*/
    public Bitmap getBlockBitmap(int i) {
        try {
            return blockBitmap[i];
        } catch (Exception e) {
            return blockBitmap[0];
        }
    }
    /**
     * 转换图片*/
    private static Bitmap drawableToBitmap(Context context, int i) {
        return BitmapFactory.decodeResource(context.getResources(), R.mipmap.block_0 + i, null);
    }
}
