package wjy.tetris.dto;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/9.
 */

public class BlockPoint implements Serializable{
    private static final long serialVersionUID = 01533123646750624203234L;
    public int x;
    public int y;
    public BlockPoint(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }
}
