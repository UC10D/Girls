package com.android.ll.znns.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.ll.znns.R;

/**
 * Created by LeeMy on 16/12/19.
 * 引导页的fragment
 */
public class GuideFragment extends Fragment {
    private View rootView;
    private ImageView imgGuideUp;
    private int imgRes;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_guide, null);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();

        if (parent != null) {
            parent.removeView(rootView);
        }

        initView();
        initData();
        return rootView;
    }

    private void initView() {
        imgGuideUp = (ImageView) rootView.findViewById(R.id.img_guide_up);
    }

    public void initData() {
        Bundle arguments = getArguments();
        if(arguments!=null){
            imgRes = arguments.getInt("imgRes", -1);
        }
        if(imgRes!=-1){
            imgGuideUp.setImageResource(imgRes);
        }
    }
}
