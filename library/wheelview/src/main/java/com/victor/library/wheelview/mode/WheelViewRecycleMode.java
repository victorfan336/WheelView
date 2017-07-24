package com.victor.library.wheelview.mode;

import android.graphics.Paint;


/**
 * Created by Victor on 2017/7/21.
 */

public class WheelViewRecycleMode extends IWheelViewMode {

    public WheelViewRecycleMode(int eachItemHeight, int childrenSize) {
        super(eachItemHeight, childrenSize);
    }

    @Override
    public int getSelectedIndex(int baseIndex) {
        int index = baseIndex;
        while (index < 0) {
            index += childrenSize;
        }
        return index % childrenSize;
    }

    @Override
    public int  getTopMaxScrollHeight() {
        return Integer.MIN_VALUE;
    }

    @Override
    public int getBottomMaxScrollHeight() {
        return Integer.MAX_VALUE;
    }

    @Override
    public float getTextDrawY(int height, int index, Paint paint) {
        return (getCenterY(height, paint) + index * eachItemHeight);
    }

}
