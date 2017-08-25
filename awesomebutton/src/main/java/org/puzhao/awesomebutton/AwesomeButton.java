package org.puzhao.awesomebutton;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.renderscript.Sampler;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.puzhao.awesomebutton.log.LogUtil;

/**
 * Created by puzhao on 24/8/17.
 */

public class AwesomeButton extends RelativeLayout {
    public boolean allowRotate = true;
    private ImageView originButton;
    private ImageView[] buttons;
    private View backGround;
    private ButtonClickInterface buttonClickInterface;
    private int buttonNumber;
    private State showState = State.HIDE;

    public AwesomeButton(Context context) {
        super(context);
        init();
    }

    public AwesomeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        readAttrs(attrs);
        init();
    }

    public AwesomeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttrs(attrs);
        init();
    }

    private void init(){
        backGround = new View(getContext());
        backGround.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        backGround.setBackgroundColor(Color.GRAY);
        backGround.setVisibility(View.GONE);
        backGround.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hideButtons();
            }
        });
        addView(backGround);

        buttons = new ImageView[3];

        buttons[0] = new ImageView(getContext());
        prepareButtons(buttons[0], R.drawable.facebook);

        buttons[1] = new ImageView(getContext());
        prepareButtons(buttons[1], R.drawable.instagram);

        buttons[2] = new ImageView(getContext());
        prepareButtons(buttons[2], R.drawable.wechat);

        originButton = new ImageView(getContext());
        prepareButtons(originButton, R.drawable.initial_button);
        originButton.setAlpha(1f);
        originButton.setOnClickListener(rotateClick());

    }

    private void prepareButtons(View v, int resource){
        RelativeLayout.LayoutParams params1 = new LayoutParams(80, 80);
        params1.addRule(ALIGN_PARENT_BOTTOM, TRUE);
        params1.addRule(ALIGN_PARENT_END, TRUE);
        params1.rightMargin = 50;
        params1.bottomMargin = 50;
        v.setLayoutParams(params1);
        v.setAlpha(0f);
        v.setBackgroundResource(resource);
        addView(v);
    }



    private void readAttrs(AttributeSet attrs){
        TypedArray ta = getContext().obtainStyledAttributes(attrs ,R.styleable.AwesomeButton);
        allowRotate = ta.getBoolean(R.styleable.AwesomeButton_allow_rotate, false);
        buttonNumber = ta.getInteger(R.styleable.AwesomeButton_button_number, 2);
        ta.recycle();
    }

    private void scaleView(View v,float x, float y){
        v.setScaleX(x);
        v.setScaleY(y);
    }

    private void moveView(View v, float x, float y){
        v.setX(x);
        v.setY(y);
    }

    private View.OnClickListener rotateClick(){
        return new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showState == State.HIDE) {
                    showButtons();
                }else{
                    hideButtons();
                }
            }
        };
    }

    private void showButtons(){
        ValueAnimator an = ValueAnimator.ofFloat(90f);
        an.setDuration(500);
        an.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (allowRotate)
                    originButton.setRotation((Float) valueAnimator.getAnimatedValue());

                buttons[0].setAlpha((float) (valueAnimator.getAnimatedValue()) / 90);
                scaleView(buttons[0], (float) (valueAnimator.getAnimatedValue()) / 90, (float) (valueAnimator.getAnimatedValue()) / 90);
                moveView(buttons[0], originButton.getX() - 2 * (float) valueAnimator.getAnimatedValue(), buttons[0].getY());
                buttons[1].setAlpha((float) (valueAnimator.getAnimatedValue()) / 90);
                scaleView(buttons[1], (float) (valueAnimator.getAnimatedValue()) / 90, (float) (valueAnimator.getAnimatedValue()) / 90);
                moveView(buttons[1], originButton.getX() - (float) Math.sqrt(2) * (float) valueAnimator.getAnimatedValue(), originButton.getY() - (float) Math.sqrt(2) * (float) valueAnimator.getAnimatedValue());

                buttons[2].setAlpha((float) (valueAnimator.getAnimatedValue()) / 90);
                scaleView(buttons[2], (float) (valueAnimator.getAnimatedValue()) / 90, (float) (valueAnimator.getAnimatedValue()) / 90);
                moveView(buttons[2], originButton.getX(), originButton.getY() - 2 * (float) valueAnimator.getAnimatedValue());


            }
        });
        an.start();
        backGround.setVisibility(View.VISIBLE);
        showState = State.SHOW;
    }

    private void hideButtons(){
        showState = State.HIDE;
        backGround.setVisibility(View.GONE);
        for (ImageView button: buttons) {
            button.setAlpha(0f);
            moveView(button, originButton.getX(), originButton.getY());
        }
    }

    public interface ButtonClickInterface{
        View.OnClickListener onButton1Click();
        View.OnClickListener onButton2Click();
        View.OnClickListener onButton3Click();
    }

    public void setButtonClickInterface(ButtonClickInterface buttonClickInterface) {
        this.buttonClickInterface = buttonClickInterface;
        setupListeners();
    }

    private void setupListeners(){
        if (buttonClickInterface!=null){
            buttons[0].setOnClickListener(buttonClickInterface.onButton1Click());
            buttons[1].setOnClickListener(buttonClickInterface.onButton2Click());
            buttons[2].setOnClickListener(buttonClickInterface.onButton3Click());
        }
    }

    private enum State{
        HIDE,SHOW
    }
}
