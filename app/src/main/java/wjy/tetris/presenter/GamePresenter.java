package wjy.tetris.presenter;

import wjy.tetris.dto.GameDto;
import wjy.tetris.model.GameModel;
import wjy.tetris.model.IGameModel;
import wjy.tetris.view.IGameView;

/**
 * Created by Administrator on 2017/3/1.
 */

public class GamePresenter implements Runnable{
    private GameDto mGameDto;
    private IGameView mGameView;
    private IGameModel mGameModel;

    public GamePresenter(IGameView gameView, GameDto gameDto) {
        super();
        this.mGameDto = gameDto;
        this.mGameView  = gameView;
        this.mGameModel = new GameModel(gameDto);
        mGameView.postInvalidate();
        new Thread(this).start();
    }

    /**
     * 旋转*/
    public void onRotate() {
        mGameModel.onRotate();
        mGameView.postInvalidate();
    }
    /**
     * 移动方块*/
    public void onMove(int x, int y) {
        mGameModel.onMove(x, y);
        mGameView.postInvalidate();
    }
    /**
     * 向下移动方块*/
    public void onMove(int y) {
        mGameModel.onMove(y);
        mGameView.postInvalidate();
    }

    @Override
    public void run() {
        int speed = mGameDto.getSpeed();
        for (;!mGameDto.isOver();) {
            try {
                Thread.sleep(speed);
                if (mGameDto.isPause())
                    continue;
                mGameDto.setOver(mGameModel.onMove(1));
                mGameView.postInvalidate();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mGameView.finish();
    }
}
