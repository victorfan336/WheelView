package com.victor.library.wheelview.mode;

import android.graphics.Paint;


/**
 * Created by Victor on 2017/7/21.
 */

public class WheelViewCenterMode extends IWheelViewMode {

    public WheelViewCenterMode(int eachItemHeight, int childrenSize) {
        super(eachItemHeight, childrenSize);
    }

    @Override
    public int getSelectedIndex(int baseIndex) {
        return baseIndex + childrenSize / 2;
    }

    @Override
    public int getTopMaxScrollHeight() {
        return -eachItemHeight * (childrenSize - 1) / 2;
    }

    @Override
    public int getBottomMaxScrollHeight() {
        return eachItemHeight * (childrenSize - 1) / 2;
    }

    @Override
    public float getTextDrawY(int height, int index, Paint paint) {
        return (getCenterY(height, paint) + (index - (childrenSize - 1) / 2) * eachItemHeight);
    }
}
