package com.demo.viewpager_indicator.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alex.dagger.R;

import java.util.List;

/**
 * ViewPagerIndicator
 * Created by hongshen on 2017/8/7 0007.
 */

public class ViewPagerIndicator extends LinearLayout{

    /**
     * 默认下划线风格指示器
     */
    private static final int STYLE_UNDERLINE = 1;

    /**
     * 默认TAB数量
     */
    private static final int DEFUALT_TAB_COUNT = 3;

    /**
     * 默认当前被选中的文字颜色
     */
    private static final int DEFUALT_SELECTED_TEXT_COLOR = Color.parseColor("#CC0000");

    /**
     * 默认正常状态的文字颜色
     */
    private static final int DEFUALT_NORMAL_TEXT_COLOR = Color.parseColor("#333333");

    /**
     * 默认指示器颜色
     */
    private static final int DEFUALT_INDICATOR_COLOR = Color.parseColor("#CC0000");

    /**
     * 默认tab中文字大小size
     */
    private static final int DEFUALT_TEXT_SIZE = 16;

    /**
     * tab上的内容
     */
    private List<String> tabTitles;

    /**
     * 实际tab数量
     */
    private int tabCount = DEFUALT_TAB_COUNT;

    /**
     * 实际正常状态文字颜色
     */
    private int normalTextColor = DEFUALT_NORMAL_TEXT_COLOR;

    /**
     * 实际选中状态文字颜色
     */
    private int selectedTextColor = DEFUALT_SELECTED_TEXT_COLOR;

    /**
     * 实际tab中文字大小size
     */
    private int textSize = DEFUALT_TEXT_SIZE;

    /**
     * 实际indicator颜色
     */
    private int indicatorColor = DEFUALT_INDICATOR_COLOR;

    /**
     * 与之绑定的ViewPager
     */
    public ViewPager viewPager;

    /**
     * ViewPager 初始下标
     */
    private int position = 0;

    /**
     * 画笔
     */
    private Paint paint;

    /**
     * 矩形
     */
    private Rect rectangle;

    /**
     * 指示器高
     */
    private int indicatorHeight = 2;

    /**
     * 指示器宽
     */
    private int indicatorWidth = getWidth()/tabCount;

    /**
     * 指示器风格
     */
    private int indicatorStyle = STYLE_UNDERLINE;

    /**
     * 手指滑动时的偏移量
     */
    private float translationX;

    public ViewPagerIndicator(Context context) {
        super(context, null);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        setGravity(Gravity.CENTER_VERTICAL);
        /**
         * 获取自定义属性
         */
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
        tabCount = a.getInt(R.styleable.ViewPagerIndicator_tab_count, DEFUALT_TAB_COUNT);
        normalTextColor = a.getColor(R.styleable.ViewPagerIndicator_text_color_normal, DEFUALT_NORMAL_TEXT_COLOR);
        selectedTextColor = a.getColor(R.styleable.ViewPagerIndicator_text_color_selected, DEFUALT_SELECTED_TEXT_COLOR);
        textSize = a.getDimensionPixelSize(R.styleable.ViewPagerIndicator_text_size, DEFUALT_TEXT_SIZE);
        indicatorColor = a.getColor(R.styleable.ViewPagerIndicator_indicator_color, DEFUALT_INDICATOR_COLOR);
        indicatorStyle = a.getInt(R.styleable.ViewPagerIndicator_indicator_style, STYLE_UNDERLINE);

        /**
         * 初始化画笔
         */
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(indicatorColor);
        paint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //初始化指示器
        initIndicator(w, h);
        //初始化tab
        initTabItem();
        //设置被选中tab文字颜色
        setSelectedTextColor(position);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * 画指示器
     * 子view
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        switch (indicatorStyle) {
            case STYLE_UNDERLINE:
                //下划线风格
                canvas.translate(translationX, 0);
                canvas.drawRect(rectangle, paint);
                break;
        }
        canvas.restore();
        Paint verticalLinePaint = new Paint();
        paint.setStrokeWidth((float) 0.5);
        verticalLinePaint.setColor(Color.parseColor("#000000"));
        for (int i = 1; i < tabCount; i++) {
            canvas.drawLine(i * getWidth()/tabCount, 8, i * getWidth()/tabCount, getHeight()-8, verticalLinePaint);
        }
        super.dispatchDraw(canvas);
    }

    /**
     * 设置关联ViewPager
     * @param mViewPager
     * @param pos
     */
    @SuppressWarnings("deprecation")
    public void setViewPager(ViewPager mViewPager, int pos) {
        this.viewPager = mViewPager;
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                onScroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                setSelectedTextColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        viewPager.setCurrentItem(pos);
        position = pos;
    }

    /**
     * 设置tab上的文字
     * @param titles
     */
    public void setTabTitles(List<String> titles) {
        this.tabTitles = titles;
    }

    /**
     * 初始化指示器
     * @param w
     * @param h
     */
    private void initIndicator(int w, int h) {
        switch (indicatorStyle) {
            case STYLE_UNDERLINE:
                //下划线风格
                indicatorWidth = w / tabCount - 26;
                indicatorHeight = h / 22;
                rectangle = new Rect(13, h-indicatorHeight, indicatorWidth + 13, h);
                translationX = 0;
                break;
        }
    }

    /**
     * 初始化tab
     */
    private void initTabItem() {
        if (tabTitles != null && tabTitles.size() > 0) {
            if (this.getChildCount() != 0) {
                this.removeAllViews();
            }

            for (String title : tabTitles) {
                measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);//重新测量宽高
                TextView textView = createTextView(title);
                addView(textView);
            }

            //点击事件
            setOnIndicatorClickEvent();
        }
    }

    /**
     * 创建标题view
     * @param text
     * @return
     */
    private TextView createTextView(String text) {
        TextView tv = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.width = getWidth() / tabCount;
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(normalTextColor);
        tv.setText(text);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        tv.setLayoutParams(lp);
        return tv;
    }

    /**
     * 设置每个tab点击事件
     */
    private void setOnIndicatorClickEvent() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final int currentItemIndex = i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(currentItemIndex);
                }
            });
        }
    }

    /**
     * 设置当前选中tab文字颜色
     * @param pos
     */
    private void setSelectedTextColor(int pos) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof TextView) {
                if (i == pos) {
                    ((TextView) view).setTextColor(selectedTextColor);
                } else {
                    ((TextView) view).setTextColor(normalTextColor);
                }
            }
        }
    }

    /**
     * 滚动
     * @param pos
     * @param offset
     */
    private void onScroll(int pos, float offset) {
        // 不断改变偏移量，invalidate
        translationX = getWidth() / tabCount * (pos + offset);

        int tabWidth = getWidth() / tabCount;

        invalidate();
    }
}
