package com.android.ll.znns.ui;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.ll.znns.R;
import com.android.ll.znns.adapter.fragment.CommonFragmentPagerAdapter;
import com.android.ll.znns.fragment.GuideFragment;

import java.util.LinkedList;

/**
 * Created by LeeMy on 16/12/19.
 * 引导页
 */
public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnTouchListener {
    private ImageView imgRide;
    private ViewPager vpGuide;
    private ImageView imgGuideDown;
    private View viewScroll;

    private float lastOffset;
    private float lastX;
    private int pagerPosition;

    private int[] imgResUp;//上半部分图片
    private int[] imgResDown;//下半部分图片
    private AnimationDrawable animationDrawable = null;

    private LinkedList<Fragment> mFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        initData();
    }

    private void initView() {
        imgRide = (ImageView) findViewById(R.id.img_ride);
        vpGuide = (ViewPager) findViewById(R.id.vp_guide);
        imgGuideDown = (ImageView) findViewById(R.id.img_guide_down);
        viewScroll = findViewById(R.id.view_scroll);
    }

    private void initData() {
        imgResUp = new int[]{R.drawable.guide_up_one_bg, R.drawable.guide_up_two_bg, R.drawable.guide_up_three_bg};
        imgResDown = new int[]{R.drawable.guide_down_one, R.drawable.guide_down_two, R.drawable.guide_down_three};
        mFragmentList = new LinkedList<>();
        parseFragment();
        vpGuide.setAdapter(new CommonFragmentPagerAdapter(getFragmentManager(), mFragmentList));
        vpGuide.addOnPageChangeListener(this);

        imgRide.setBackgroundResource(R.drawable.guide_animation_list);
        animationDrawable = (AnimationDrawable) imgRide.getBackground();
        animationDrawable.start();

        vpGuide.setOnTouchListener(this);
    }

    public void parseFragment() {
        if (imgResUp != null) {
            int length = imgResUp.length;
            for (int i = 0; i < length; i++) {
                GuideFragment guideFragment = new GuideFragment();
                Bundle args = new Bundle();
                args.putInt("imgRes", imgResUp[i]);
                guideFragment.setArguments(args);
                mFragmentList.add(guideFragment);

            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (pagerPosition == 2) {
            int action = motionEvent.getAction();

            if (action == MotionEvent.ACTION_DOWN) {
                lastX = motionEvent.getX();
            } else if (action == MotionEvent.ACTION_MOVE) {
                if (lastX - motionEvent.getX() > 100) {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }

        }

        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewScroll.getLayoutParams();
        float distance = viewScroll.getWidth();
        int leftDistance = 0;
        if (lastOffset < positionOffset && positionOffset > 0) {
            //正向
            leftDistance = (int) (distance * (position));
            lp.leftMargin = leftDistance +
                    (int) (positionOffset * distance);
            if (positionOffset < 0.5) {
             /*   if (position == 0) {
                    ivGuideDown.setImageResource(imgResDown[0]);
                } else if (position == 1) {
                    ivGuideDown.setImageResource(imgResDown[1]);
                }*/
                imgGuideDown.setImageResource(imgResDown[position]);

                float alpha = 1 - 2 * positionOffset;
                imgGuideDown.setAlpha(alpha < 0.2 ? 0.2f : alpha);

            } else {
              /*  if (position == 0) {
                    ivGuideDown.setImageResource(imgResDown[1]);
                } else if (position == 1) {
                    ivGuideDown.setImageResource(imgResDown[2]);
                }*/
                imgGuideDown.setImageResource(imgResDown[position+1]);

                float alpha = (float) (2 * (positionOffset - 0.5));
                imgGuideDown.setAlpha(alpha < 0.2 ? 0.2f : alpha);
            }

        }
        if (lastOffset > positionOffset && positionOffset > 0) {
            //反向
            leftDistance = (int) (distance * (position + 1));
            lp.leftMargin = leftDistance +
                    (int) ((positionOffset - 1) * distance);

            if (positionOffset < 0.5) {
//                if (position == 0) {
//                    ivGuideDown.setImageResource(imgResDown[0]);
//                } else if (position == 1) {
//                    ivGuideDown.setImageResource(imgResDown[1]);
//                }
                imgGuideDown.setImageResource(imgResDown[position]);

                float alpha = 1 - 2 * positionOffset;
                imgGuideDown.setAlpha(alpha < 0.2 ? 0.2f : alpha);

            } else {
               /* if (position == 0) {
                    ivGuideDown.setImageResource(imgResDown[1]);
                } else if (position == 1) {
                    ivGuideDown.setImageResource(imgResDown[2]);
                }*/
                imgGuideDown.setImageResource(imgResDown[position+1]);

                float alpha = 2 * (positionOffset - 0.5f);
                imgGuideDown.setAlpha(alpha < 0.2 ? 0.2f : alpha);
            }
        }
        lastOffset = positionOffset;

//        LOGD(TAG, "---left margin" + lp.leftMargin + "--- position offset"
//                + positionOffset + "---distance" + distance + "left distance" + leftDistance +
//                "---current page" + position + "---position" + position);
        lp.width = (int) distance;
        viewScroll.setLayoutParams(lp);
        //设置底部图片的透明图

        pagerPosition = position;
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
