package com.victor.library.wheelview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.OverScroller;
import android.widget.Scroller;

import com.victor.library.wheelview.mode.IWheelViewMode;
import com.victor.library.wheelview.mode.WheelViewCenterMode;
import com.victor.library.wheelview.mode.WheelViewRecycleMode;
import com.victor.library.wheelview.mode.WheelViewStartMode;

import java.util.ArrayList;

import static android.R.attr.scrollY;
import static android.R.attr.width;


/**
 * Created by Victor on 2017/7/21.
 */

public class WheelView extends View {
    private static final String TAG = "WheelView";
    private int[] SHADOWS_COLORS = {0xefffffff, 0xcfffffff, 0x3fffffff};
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int textColor = 0x000;
    private float textSize = 19f;
    private int bgColor = 0x000;
    private Scroller scroller;
    private int eachItemHeight = 60;
    private int maxShowSize = 7;
    private float prevY = 0f;
    private ArrayList<String> textList = new ArrayList<>();
    // Shadows drawables
    private GradientDrawable topShadow = null;
    private GradientDrawable bottomShadow = null;

    private GestureDetector gestureDetector = null;
    private WheelScrollListener wheelScrollListener = null;

    private int mSelected = 0;

    private boolean canDragOutBorder = true;
    private int mScrollY = 0;
    private VelocityTracker vTracker = null;
    private float mEdgeSlop = 0f;
    private int mFlingDistance = 0;
    private int mCurrY = 0;
    /**
     * 模式:居中显示；从起始位置显示；循环显示
     */
    private IWheelViewMode wheelViewMode = new WheelViewRecycleMode(eachItemHeight, 0);

    public WheelView(Context context) {
        this(context, null);
    }

    public WheelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WheelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(context, attrs, defStyleAttr);
    }

    private void initParams(Context context, AttributeSet attrs, int defStyleAttr) {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.WheelView, defStyleAttr, 0);
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.WheelView_textColor) {
                textColor = typedArray.getColor(attr, 0x000);
            } else if (attr == R.styleable.WheelView_textSize) {
                textSize = typedArray.getDimension(attr, 19f);
            } else if (attr == R.styleable.WheelView_dragOut) {
                canDragOutBorder = typedArray.getBoolean(attr, true);
                break;
            }
        }
        scroller = new Scroller(context, new AccelerateDecelerateInterpolator());
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        typedArray.recycle();

        mEdgeSlop = 1f;
        bgColor = getResources().getColor(R.color.white);
        if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        gestureDetector = new GestureDetector(context, gestureListener);

        if (topShadow == null) {
            topShadow = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, SHADOWS_COLORS);
        }
        if (bottomShadow == null) {
            bottomShadow = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, SHADOWS_COLORS);
        }
    }

    private GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            int dy = (int) (-vTracker.getYVelocity() / 8);
            // 边距检测
            if (scrollY + dy <= wheelViewMode.getTopMaxScrollHeight()) {
                dy = wheelViewMode.getTopMaxScrollHeight() - scrollY;
            }
            if (scrollY + dy >= wheelViewMode.getBottomMaxScrollHeight()) {
                dy = wheelViewMode.getBottomMaxScrollHeight() - scrollY;
            }
            // 取整每次onfling的距离
            int scrollDy = scrollY % eachItemHeight;
            if (scrollDy + dy % eachItemHeight > eachItemHeight / 2) {
                dy += eachItemHeight - (scrollDy + dy % eachItemHeight);
            } else if (scrollDy + dy % eachItemHeight > 0 && scrollDy + dy % eachItemHeight <= eachItemHeight / 2) {
                dy -= scrollDy + dy % eachItemHeight;
            } else if (scrollDy + dy % eachItemHeight < 0 && scrollDy + dy % eachItemHeight >= -eachItemHeight / 2) {
                dy -= scrollDy + dy % eachItemHeight;
            } else if (scrollDy + dy % eachItemHeight < -eachItemHeight / 2) {
                dy -= scrollDy + dy % eachItemHeight + eachItemHeight;
            }
            mFlingDistance = dy;
            Log.e(TAG, "onfling: scrollY = ${scrollY} --- dy = $dy -- scrollY + dy = ${scrollY + dy}");
            scroller.startScroll(0, scrollY, 0, dy, 600);
            invalidate();
            return true;
        }
    };

    private Runnable task = new Runnable() {

        @Override
        public void run() {
            int moveIndex = (int) ((mCurrY + mFlingDistance) * 1f / eachItemHeight);
            int selected = wheelViewMode.getSelectedIndex(moveIndex);
            Log.e(TAG, "currY = $mCurrY -- mFlingDistance = $mFlingDistance -- moveIndex = $moveIndex  -- selected = $selected");
            if (mSelected != selected) {
                mSelected = selected;
                wheelScrollListener.changed(mSelected, textList.get(mSelected));
            }
        }
    };

    public void setText(ArrayList<String> list) {
        textList.clear();
        textList.addAll(list);
        scrollTo(0, 0);
        wheelViewMode.setChildrenSize(textList.size());
        invalidate();
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthMOde = MeasureSpec.getMode((widthMeasureSpec));
        int heightM = MeasureSpec.getSize(heightMeasureSpec);
        int widthM = MeasureSpec.getSize(widthMeasureSpec);

        if (heightMode == MeasureSpec.EXACTLY && widthMOde == MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthM, heightM);
        } else if (heightMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(600, heightM);
        } else if (widthMOde == MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthM, 600);
        } else {
            setMeasuredDimension(400, 600);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mFlingDistance = 0;
                if (vTracker == null) {
                    vTracker = VelocityTracker.obtain();
                } else {
                    vTracker.clear();
                }
                if (textList.size() == 0) {
                    return false;
                }

                ViewParent parent = getParent();
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
                if (!scroller.isFinished()) {
                    getHandler().removeCallbacks(task);
                    scroller.forceFinished(true);
                }
                prevY = event.getY();

                break;
            case MotionEvent.ACTION_MOVE:
                vTracker.addMovement(event);
                vTracker.computeCurrentVelocity(1000);
                float currY = event.getY();
                int dy = (int) (currY - prevY);
                if (Math.abs(dy) > mEdgeSlop) {
                    if (!canDragOutBorder) {
                        if (scrollY + dy <= 0 && scrollY + dy <= wheelViewMode.getTopMaxScrollHeight()) {
                            dy -= wheelViewMode.getTopMaxScrollHeight() - scrollY;
                        } else if (scrollY + dy > 0 && scrollY + dy >= wheelViewMode.getBottomMaxScrollHeight()) {
                            dy -= wheelViewMode.getBottomMaxScrollHeight() - scrollY;
                        }
                    }
                    Log.e(TAG, "currY = $currY  -- prevY = $prevY -- scrollY = $scrollY -- dy = $dy");
                    scrollBy(0, -dy);
                    invalidate();
                }
                prevY = currY;
                break;
            case MotionEvent.ACTION_UP:
                scrollerStop();
                break;
        }
        gestureDetector.onTouchEvent(event);
        return true;
    }

    private void scrollerStop() {
        float offset = 0.5f;
        if (scrollY < 0) {
            offset = -0.5f;
        }
        int moveIndex = (int) (scrollY * 1f / eachItemHeight + offset);
        int dy = scrollY - moveIndex * eachItemHeight;

        if (canDragOutBorder && !(wheelViewMode instanceof WheelViewRecycleMode)){
            if (scrollY <= 0 && scrollY <= wheelViewMode.getTopMaxScrollHeight()) {
                dy = -wheelViewMode.getTopMaxScrollHeight() + scrollY;
            } else if (scrollY > 0 && scrollY >= wheelViewMode.getBottomMaxScrollHeight()) {
                dy = scrollY - wheelViewMode.getBottomMaxScrollHeight();
            }
        }
        mCurrY = scrollY;
        mFlingDistance = -dy;
        scroller.startScroll(0, scrollY, 0, -dy, 200);
        invalidate();
        getHandler().postDelayed(task, 200);

    }

    public void setWheelScrollListener(WheelScrollListener wheelScrollListener) {
        this.wheelScrollListener = wheelScrollListener;
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            mScrollY = scroller.getCurrY();
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        if (textList.size() == 0) return;
        canvas.drawColor(0x000000);
        paint.setColor(0xff00ff);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(150, 150, 100, paint);
//        clipView(canvas);
//        drawText(canvas);
//        drawLine(canvas);
//        drawShadows(canvas);
    }

    private void drawLine(Canvas canvas) {
        float baseHeight = (getHeight() - eachItemHeight) / 2;
        paint.setColor(getResources().getColor(R.color.gray));
        canvas.drawLine(0f, baseHeight + scrollY, width, baseHeight + scrollY, paint);
        canvas.drawLine(0f, baseHeight + eachItemHeight + scrollY, width,
                baseHeight + eachItemHeight + scrollY, paint);
    }

    private void drawText(Canvas canvas) {
        paint.setColor(getResources().getColor(R.color.black));
        float y;
        float x;
        int minStart = 0;
        int count = textList.size();
        if (wheelViewMode instanceof WheelViewRecycleMode) {
            minStart = -maxShowSize / 2 - 1 + scrollY / eachItemHeight; // 多显示一个，避免闪现
            count = textList.size() + scrollY / eachItemHeight;
        }
        int index = 0;
        for (int i = minStart; i < count; i ++) {
            index = i;
            if (wheelViewMode instanceof WheelViewRecycleMode && index< 0){
                while (index < 0) {
                    index += textList.size();
                }
            } else{
                index = i;
            }
            x = (width - paint.measureText(textList.get(index % textList.size()))) / 2;
            y = wheelViewMode.getTextDrawY(getHeight(), i, paint);
            canvas.drawText(textList.get(index % textList.size()), x, y, paint);
        }
    }

    private float getMaxHeight() {
        float maxHeight = eachItemHeight * maxShowSize;
        if (maxHeight > getHeight()) {
            maxHeight = getHeight();
        }
        return maxHeight;
    }

    private void clipView(Canvas canvas) {
        float reqHeight = getMaxHeight();
        float starOffset = (getHeight() - reqHeight) / 2f;
        canvas.clipRect(0f, starOffset + scrollY, width,
                starOffset + reqHeight + scrollY);
    }

    private int getShadowsHeight() {
        return (getHeight() - eachItemHeight) / 2;
    }

    private void drawShadows(Canvas canvas) {
        int height = getShadowsHeight();
        topShadow.setBounds(0, scrollY, width, height + scrollY);
        topShadow.draw(canvas);

        bottomShadow.setBounds(0, scrollY + eachItemHeight + height, width, getHeight() + scrollY);
        bottomShadow.draw(canvas);
    }

    public int getEachItemHeight() {
        return eachItemHeight;
    }

    public void setEachItemHeight(int eachItemHeight) {
        this.eachItemHeight = eachItemHeight;
    }

    public void setMode(IWheelViewMode mode) {
        this.wheelViewMode = mode;
        scrollTo(0, 0);
        invalidate();
    }

    public IWheelViewMode getMode() {
        return this.wheelViewMode;
    }

    public int getContentSize() {
        return textList.size();
    }

    public int getMaxShowSize() {
        return maxShowSize;
    }

    public void setMaxShowSize(int size) {
        maxShowSize = size;
    }

    public interface WheelScrollListener {
        void changed(int selected, String name);
    }


    public static IWheelViewMode getCenterModeInstance (WheelView wheelView) {

        return new WheelViewCenterMode(wheelView.getEachItemHeight(), wheelView.getContentSize());
    }

    public static IWheelViewMode getStartModeInstance (WheelView wheelView) {

        return new WheelViewStartMode(wheelView.getEachItemHeight(), wheelView.getContentSize());
    }

    public static IWheelViewMode getRecycleModeInstance (WheelView wheelView) {

        return new WheelViewRecycleMode(wheelView.getEachItemHeight(), wheelView.getContentSize());
    }

}
