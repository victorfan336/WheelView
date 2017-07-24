package com.victor.library.wheelview.mode;


import android.graphics.Paint;


/**
 * Created by Victor on 2017/7/21.
 */

public abstract class IWheelViewMode {

    int eachItemHeight;
    int childrenSize;

    public int getEachItemHeight() {
        return eachItemHeight;
    }

    public void setEachItemHeight(int eachItemHeight) {
        this.eachItemHeight = eachItemHeight;
    }

    public int getChildrenSize() {
        return childrenSize;
    }

    public void setChildrenSize(int childrenSize) {
        this.childrenSize = childrenSize;
    }

    public IWheelViewMode(int eachItemHeight, int childrenSize) {
        this.eachItemHeight = eachItemHeight;
        this.childrenSize = childrenSize;
    }

    public abstract int getSelectedIndex(int baseIndex);
    public abstract int getTopMaxScrollHeight();
    public abstract int getBottomMaxScrollHeight();
    public abstract float getTextDrawY(int height, int index, Paint paint);

    public float getCenterY(int height, Paint paint) {
        return (height - paint.getFontMetrics().bottom - paint.getFontMetrics().top) / 2;
    }
}
