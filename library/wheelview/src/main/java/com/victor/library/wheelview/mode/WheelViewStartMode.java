package com.victor.library.wheelview.mode;

import android.graphics.Paint;


/**
 * Created by Victor on 2017/7/21.
 */

public class WheelViewStartMode extends IWheelViewMode {

    public WheelViewStartMode(int eachItemHeight, int childrenSize) {
        super(eachItemHeight, childrenSize);
    }

    @Override
    public int getSelectedIndex(int baseIndex) {
        return baseIndex;
    }

    @Override
    public int getTopMaxScrollHeight() {
        return 0;
    }

    @Override
    public int getBottomMaxScrollHeight() {
        return eachItemHeight * (childrenSize - 1);
    }

    @Override
    public float getTextDrawY(int height, int index, Paint paint) {
        return (getCenterY(height, paint) + index * eachItemHeight);
    }
}
