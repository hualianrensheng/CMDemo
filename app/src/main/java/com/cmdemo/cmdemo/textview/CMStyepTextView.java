package com.cmdemo.cmdemo.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.cmdemo.cmdemo.R;

import androidx.annotation.Nullable;

public class CMStyepTextView extends View {

    //设置默认属性
    private int cmOuterColor = Color.RED;
    private int cmInnerColor = Color.BLUE;
    private int cmBorderWidth = 20;
    private int cmStepTextSize = 30;
    private int cmStepTextColor = Color.RED;

    //定义画笔
    private Paint cmOuterPaint,cmInnerPaint,cmTextPaint;

    private int cmStepMax = 0;
    private int cmStepCurrent = 0;

    public CMStyepTextView(Context context) {
        this(context,null);
    }

    public CMStyepTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CMStyepTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性
        getStype(context,attrs,defStyleAttr);
    }

    private void getStype(Context context,AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(R.styleable.CMStyepView);
        cmOuterColor = typedArray.getColor(R.styleable.CMStyepView_cmOuterColor,cmOuterColor);
        cmInnerColor = typedArray.getColor(R.styleable.CMStyepView_cmInnerColor,cmInnerColor);
        cmBorderWidth = typedArray.getDimensionPixelOffset(R.styleable.CMStyepView_cmBorderWidth,cmBorderWidth);
        cmStepTextSize = typedArray.getDimensionPixelOffset(R.styleable.CMStyepView_cmStepTextSize,cmStepTextSize);
        cmStepTextColor = typedArray.getColor(R.styleable.CMStyepView_cmStepTextColor,cmStepTextColor);
        typedArray.recycle();

        //设置外圈画笔
        cmOuterPaint = new Paint();
        //设置抗锯齿
        cmOuterPaint.setAntiAlias(true);
        //设置圆弧宽度
        cmOuterPaint.setStrokeWidth(cmBorderWidth);
        //设置为Round
        cmOuterPaint.setStrokeCap(Paint.Cap.ROUND);
        cmOuterPaint.setStrokeJoin(Paint.Join.ROUND);
        //设置画笔颜色
        cmOuterPaint.setColor(cmOuterColor);
        cmOuterPaint.setStyle(Paint.Style.STROKE);

        //设置内圈画笔
        cmInnerPaint = new Paint();
        //设置抗锯齿
        cmInnerPaint.setAntiAlias(true);
        //设置圆弧宽度
        cmInnerPaint.setStrokeWidth(cmBorderWidth);
        //设置为Round
        cmInnerPaint.setStrokeCap(Paint.Cap.ROUND);
        cmInnerPaint.setStrokeJoin(Paint.Join.ROUND);
        //设置画笔颜色
        cmInnerPaint.setColor(cmInnerColor);
        cmInnerPaint.setStyle(Paint.Style.STROKE);

        //设置TextView画笔
        cmTextPaint = new Paint();
        //设置抗锯齿
        cmTextPaint.setAntiAlias(true);
        //设置为Round
        cmTextPaint.setStrokeCap(Paint.Cap.ROUND);
        cmTextPaint.setStrokeJoin(Paint.Join.ROUND);
        //设置画笔颜色
        cmTextPaint.setColor(cmOuterColor);
        cmTextPaint.setStyle(Paint.Style.STROKE);
        cmTextPaint.setTextSize(cmStepTextSize);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取宽高
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heigth = MeasureSpec.getSize(heightMeasureSpec);
        //获取宽高模式
        int widthMode = MeasureSpec.getSize(widthMeasureSpec);
        int heigthMode = MeasureSpec.getSize(heightMeasureSpec);

        //如果高度的模式与宽度模式都是AT_MOST，则设置默认大小
        if(MeasureSpec.AT_MOST == heigthMode){
            heigth = 40;
        }
        if(MeasureSpec.AT_MOST == widthMode){
            width = 40;
        }
        //重新设置自定义View的宽高，并且保证宽高一致
        heigth = width>heigth? heigth:width;
        width = width>heigth? heigth:width;

        //重新设置宽高
        setMeasuredDimension(heigth,width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 1,绘制外圆弧
         * 2,绘制内圆弧
         * 3,绘制文字
         */
        //画外圆圈
        RectF rectF = new RectF(cmBorderWidth/2,cmBorderWidth/2,getWidth()-cmBorderWidth/2,getHeight()-cmBorderWidth/2);

        canvas.drawArc(rectF,135,270,false,cmOuterPaint);

        //画内圆圈 设置画笔颜色
        cmInnerPaint.setColor(cmInnerColor);
        float sweepAngle = (float)cmStepCurrent / cmStepMax;
        canvas.drawArc(rectF,135,sweepAngle*270,false,cmInnerPaint);


        //画文字 重置画笔
        String cmStep = String.valueOf(cmStepCurrent);
        Rect rectBounds = new Rect();
        cmTextPaint.getTextBounds(cmStep,0,cmStep.length(),rectBounds);
        int dx = getWidth()/2 - rectBounds.width()/2;
        //找基线
        Paint.FontMetricsInt fontMetricsInt = cmTextPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight()/2 + dy;
        canvas.drawText(cmStep,dx,baseLine,cmTextPaint);
    }

    /**
     * @Info 对外提供方法，设置最大步数
     * @param maxStep
     */
    public synchronized void setMaxStep(int maxStep){
        if(0>maxStep){
            throw new IllegalArgumentException("最大步数不能小于0");
        }
    }

    /**
     * @Info 提供最大步数
     * @return
     */
    public synchronized int getMaxStep(){
        return cmStepMax;
    }

    /**
     * @Info 设置最大步数
     * @param progressStep
     */
    public synchronized void setStepProgress(int progressStep){
        if(0 > progressStep){
            throw new IllegalArgumentException("当前步数小于0");
        }
        this.cmStepCurrent = progressStep;
        invalidate();
    }

    /**
     * @Info 提供当前步数
     * @return
     */
    public synchronized int getProgressStep(){
        return cmStepCurrent;
    }
}
