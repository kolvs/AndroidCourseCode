package com.assess15.ui.widget.editText;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.TypedValue;
import com.assess15.ui.R;

import java.lang.reflect.Field;

/**
 * 自定义密码输入框
 */
public class PasswordEditText extends androidx.appcompat.widget.AppCompatEditText {
    //画笔
    private Paint mPaint;
    //一个密码所占的宽度
    private int mPasswordItemWidth;
    //密码的个数默认6个
    private int mPasswordNumber = 6;
    //背景边框颜色
    private int mBgColor = Color.parseColor("#d1d2d6");
    //背景边框的大小
    private int mBgSize = 1;
    //背景边框圆角大小
    private int mBgCorner = 0;
    //分割线的颜色
    private int mDivisionLineColor = mBgColor;
    //分割线的大小
    private int mDivisionLineSize = 1;
    //密码圆点的颜色
    private int mPasswordColor = mDivisionLineColor;
    //密码圆点的半径大小
    private int mPasswordRadius = 4;

    public PasswordEditText(Context context) {
        this(context, null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initAttributeSet(context, attrs);
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    /**
     * 初始化属性
     */
    private void initAttributeSet(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PasswordEditText);
        //获取大小
        mDivisionLineSize = (int) array.getDimension(R.styleable.PasswordEditText_divisionLineSize, dip2px(mDivisionLineSize));
        mPasswordRadius = (int) array.getDimension(R.styleable.PasswordEditText_passwordRadius, dip2px(mPasswordRadius));
        mBgSize = (int) array.getDimension(R.styleable.PasswordEditText_bgSize, dip2px(mBgSize));
        mBgCorner = (int) array.getDimension(R.styleable.PasswordEditText_bgCorner, dip2px(mBgCorner));
        //获取颜色
        mBgColor = array.getColor(R.styleable.PasswordEditText_bgColor, mBgColor);
        mDivisionLineColor = array.getColor(R.styleable.PasswordEditText_divisionLineColor, mDivisionLineColor);
        mPasswordColor = array.getColor(R.styleable.PasswordEditText_passwordColor, mDivisionLineColor);
        array.recycle();
    }

    private float dip2px(int mDivisionLineSize) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mDivisionLineSize, getResources().getDisplayMetrics());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //一个密码的宽度
        mPasswordItemWidth = (getWidth() - 2 * mBgSize - (mPasswordNumber - 1) * mDivisionLineSize) / mPasswordNumber;
        drawBg(canvas);
        drawDivisionLine(canvas);
        drawPassword(canvas);
    }

    /**
     * 绘制密码
     *
     * @param canvas
     */
    private void drawPassword(Canvas canvas) {
        //设置实心样式
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mPasswordColor);
        String text = getText().toString().trim();
        int passwordLength = text.length();
        for (int i = 0; i < passwordLength; i++) {
            int cx = mBgSize + i * mPasswordItemWidth + i * mDivisionLineSize + mPasswordItemWidth / 2;
            int cy = getHeight() / 2;
            canvas.drawCircle(cx, cy, mPasswordRadius, mPaint);
        }
    }

    /**
     * 绘制分割线
     *
     * @param canvas
     */
    private void drawDivisionLine(Canvas canvas) {
        mPaint.setStrokeWidth(mDivisionLineSize);
        mPaint.setColor(mDivisionLineColor);
        for (int i = 0; i < mPasswordNumber - 1; i++) {
            int startX = mBgSize + (i + 1) * mPasswordItemWidth + i * mDivisionLineSize;
            int startY = mBgSize;
            int endY = getHeight() - mBgSize;
            canvas.drawLine(startX, startY, startX, endY, mPaint);
        }
    }

    /**
     * 绘制背景
     *
     * @param canvas
     */
    private void drawBg(Canvas canvas) {
        RectF rect = new RectF(mBgSize, mBgSize, getWidth() - mBgSize, getHeight() - mBgSize);
        //绘制背景  如果有圆角就绘制圆角矩形，没有就绘制矩形
        //设置画笔的大小
        mPaint.setStrokeWidth(mBgSize);
        mPaint.setColor(mBgColor);
        //绘制空心
        mPaint.setStyle(Paint.Style.STROKE);
        if (mBgCorner == 0) {
            canvas.drawRect(rect, mPaint);
        } else {
            canvas.drawRoundRect(rect, mBgCorner, mBgCorner, mPaint);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        //刷新界面
        invalidate();
        if (mListener != null) {
            if (getText().toString().length() == getMaxLength()) {
                String password = getText().toString().trim();
                mListener.passwordFull(password);
            }
        }
    }

    // 获取EditText输入的长度
    public int getMaxLength() {
        int length = 0;
        try {
            InputFilter[] inputFilters = getFilters();
            for (InputFilter filter : inputFilters) {
                Class<?> c = filter.getClass();
                if (c.getName().equals("android.text.InputFilter$LengthFilter")) {
                    Field[] f = c.getDeclaredFields();
                    for (Field field : f) {
                        if (field.getName().equals("mMax")) {
                            field.setAccessible(true);
                            length = (Integer) field.get(filter);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }

    /**
     * 密码填完回调
     */
    private PasswordFullListener mListener;

    public void setOnPasswordFullListener(PasswordFullListener listener) {
        mListener = listener;
    }

    public interface PasswordFullListener {
        void passwordFull(String password);
    }
}
