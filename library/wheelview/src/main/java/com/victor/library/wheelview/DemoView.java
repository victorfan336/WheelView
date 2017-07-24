package com.victor.library.wheelview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;


/**
 * Created by Administrator on 2017/7/21.
 */

public class DemoView extends View {

    Paint paint = new Paint();

    public DemoView(Context context) {
        super(context);
    }

    public DemoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DemoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(200, 200);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(0xff00f);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(10, 10, 15, paint);
    }
}
