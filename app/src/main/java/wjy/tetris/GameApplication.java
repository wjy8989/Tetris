package wjy.tetris;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import wjy.tetris.dto.GameDto;

/**
 * Created by Administrator on 2017/3/2.
 */

public class GameApplication extends Application{

    public static final String SAVE_NAME = "tetris.save";

    private File mSdDir;
    private File mSdFile;

    private static GameApplication sInstance;
    private static MediaPlayer mMediaPlayer;
    private static int mPosition;

    private static boolean allowMusic = true;

    public static GameApplication getInstance() {
        return sInstance;
    }

    public static void setAllowMusic(boolean bn) {
        allowMusic = !bn;
        if (bn) {
            pauseMusic();
        } else {
            destroyMusic();
            startMusic();
        }

    }
    public static boolean isAllowMusic() {
        return allowMusic;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mSdDir = Environment.getExternalStorageDirectory();
        mSdFile = new File(mSdDir, SAVE_NAME);
    }
    /**
     * 初始化 音效状态*/
    public static Drawable getSoundIco() {
        if (GameApplication.isAllowMusic()) {
            return GameApplication.getInstance().getResources().getDrawable(R.mipmap.btn_sound);
        }
        return GameApplication.getInstance().getResources().getDrawable(R.mipmap.btn_sound_1);

    }
    /**
     * 初始化音乐播放器
     */
    private static void initMusic() {
        /**创建MediaPlayer对象**/
        mMediaPlayer = MediaPlayer.create(sInstance, R.raw.bg);
        /**设置为循环播放**/
        mMediaPlayer.setLooping(true);
    }

    /**
     * 暂停音乐
     */
    public static void pauseMusic() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mPosition = mMediaPlayer.getCurrentPosition();
            mMediaPlayer.stop();
        }
    }

    /**
     * 播放音乐
     */
    public static void startMusic() {
        if (isAllowMusic()) {
            initMusic();
            if (!mMediaPlayer.isPlaying()) {
                mMediaPlayer.start();
            }
        }
    }

    /**
     * 摧毁音乐
     */
    public static void destroyMusic() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    /**
     * 继续音乐
     */
    public static void resumeMusic() {
        if (mPosition > 0 && mMediaPlayer != null) {
            mMediaPlayer.reset();
            initMusic();
            mMediaPlayer.start();
            mMediaPlayer.seekTo(mPosition);
            mPosition = 0;
        }
    }
    /**
     * 保存游戏*/
    public void saveGame(GameDto gameDto) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mSdFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(gameDto);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 读取存档*/
    public GameDto readFile() {
        GameDto gameDto = null;
        try {
            FileInputStream fis   = new FileInputStream(mSdFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            gameDto = (GameDto) ois.readObject();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return gameDto;
    }
    /**
     * 删除存档*/
    public void destroySave() {
        mSdFile.delete();
    }
}
