package app.onbo.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import app.onbo.R;


public class IncDecButton extends RelativeLayout {
    private Context context;
    private AttributeSet attrs;
    private int styleAttr;
    private OnClickListener mListener;
    private int initialNumber;
    private int lastNumber;
    private int currentNumber;
    private int finalNumber;
    private TextView textView;
    private Button addBtn, subtractBtn;
    private View view;
    private OnValueChangeListener mOnValueChangeListener;
    private OnButtonsClickedListener mOnButtonsClickedListener;
    private ViewSwitcher viewSwitcher;
    private TextView textViewAdd;


    public TextView getTextView() {
        return textView;
    }

    public IncDecButton(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public IncDecButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        initView();
    }

    public IncDecButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.attrs = attrs;
        this.styleAttr = defStyleAttr;
        initView();
    }

    private void initView() {
        this.view = this;
        inflate(context, R.layout.layout_inc_dec, this);
        final Resources res = getResources();
        final int defaultColor = res.getColor(R.color.colorPrimary);
        final int defaultTextColor = res.getColor(R.color.colorText);
        final Drawable defaultDrawable = res.getDrawable(R.drawable.background_inc_dec);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IncDecButton, styleAttr, 0);

        initialNumber = a.getInt(R.styleable.IncDecButton_initialNumber, 0);
        finalNumber = a.getInt(R.styleable.IncDecButton_finalNumber, Integer.MAX_VALUE);
        float textSize = a.getDimension(R.styleable.IncDecButton_textSize, 13);
        int color = a.getColor(R.styleable.IncDecButton_backGroundColor, defaultColor);
        int textColor = a.getColor(R.styleable.IncDecButton_textColor, defaultTextColor);
        Drawable drawable = a.getDrawable(R.styleable.IncDecButton_backgroundDrawable);

        subtractBtn = findViewById(R.id.subtract_btn);
        addBtn =  findViewById(R.id.add_btn);
        textView =  findViewById(R.id.number_counter);
        LinearLayout mLayout =  findViewById(R.id.layout);
        viewSwitcher = findViewById(R.id.view_switcher);
        textViewAdd = findViewById(R.id.textViewAddInitial);

        textViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnButtonsClickedListener==null){
                    try {
                        throw new Exception("ButtonClickedListener Must be implemented");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    viewSwitcher.showNext();
                    mOnButtonsClickedListener.onPlusClicked(1);
                }
            }
        });

        subtractBtn.setTextColor(textColor);
        addBtn.setTextColor(textColor);
        textView.setTextColor(textColor);
        subtractBtn.setTextSize(textSize);
        addBtn.setTextSize(textSize);
        textView.setTextSize(textSize);

        if (drawable == null) {
            drawable = defaultDrawable;
        }
        assert drawable != null;
        drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));
        if (Build.VERSION.SDK_INT > 16)
            mLayout.setBackground(drawable);
        else
            mLayout.setBackgroundDrawable(drawable);

        textView.setText(String.valueOf(initialNumber));

        currentNumber = initialNumber;
        lastNumber = initialNumber;

        subtractBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                int num = Integer.valueOf(textView.getText().toString());
                if(mOnButtonsClickedListener==null){
                    try {
                        throw new Exception("ButtonClickedListener Must be implemented");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    if(num>=1){
                        mOnButtonsClickedListener.onMinusClicked(num-1);
                    }else if(num==0) {
                        mOnButtonsClickedListener.onMinusClicked(num-1);
                        viewSwitcher.setDisplayedChild(0);
                    }
                }

            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                int num = Integer.valueOf(textView.getText().toString());
                if(mOnButtonsClickedListener==null){
                    try {
                        throw new Exception("ButtonClickedListener Must be implemented");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    mOnButtonsClickedListener.onPlusClicked(num+1);
                }
            }
        });
        a.recycle();
    }

    private void callListener(View view) {
        if (mListener != null) {
            mListener.onClick(view);
        }

        if (mOnValueChangeListener != null) {
            if (lastNumber != currentNumber) {
                mOnValueChangeListener.onValueChange(this, lastNumber, currentNumber);
            }
        }
    }

    public int getNumber() {
        return currentNumber;
    }

    public void setNumber(int number) {
        lastNumber = currentNumber;
        this.currentNumber = number;
        if (this.currentNumber > finalNumber) {
            this.currentNumber = finalNumber;
        }
        if (this.currentNumber < initialNumber) {
            this.currentNumber = initialNumber;
        }
        textView.setText(String.valueOf(currentNumber));
    }


    public void setNumber(int number, boolean switcherState) {
        if(number==0){
            setNumber(0);
            viewSwitcher.setDisplayedChild(0);
        }else {
            setNumber(number);
            if (switcherState) {
                viewSwitcher.setDisplayedChild(1);
            }
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mListener = onClickListener;
    }

//    public void setOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
//        mOnValueChangeListener = onValueChangeListener;
//    }

    public void setOnButtonsClickedListener(OnButtonsClickedListener onButtonsClickedListener){
        mOnButtonsClickedListener = onButtonsClickedListener;

    }
    public interface OnClickListener {

        void onClick(View view);

    }

    public interface OnButtonsClickedListener{
        void onPlusClicked(int num);
        void onMinusClicked(int num);
    }

    public interface OnValueChangeListener {
        void onValueChange(IncDecButton view, int oldValue, int newValue);
    }

    public void setRange(Integer startingNumber, Integer endingNumber) {
        this.initialNumber = startingNumber;
        this.finalNumber = endingNumber;
    }

    public void updateColors(int backgroundColor, int textColor) {
        this.textView.setBackgroundColor(backgroundColor);
        this.addBtn.setBackgroundColor(backgroundColor);
        this.subtractBtn.setBackgroundColor(backgroundColor);

        this.textView.setTextColor(textColor);
        this.addBtn.setTextColor(textColor);
        this.subtractBtn.setTextColor(textColor);
    }

    public void updateTextSize(int unit, float newSize) {
        this.textView.setTextSize(unit, newSize);
        this.addBtn.setTextSize(unit, newSize);
        this.subtractBtn.setTextSize(unit, newSize);
    }
}
