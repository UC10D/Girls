package com.android.ll.znns.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ll.znns.R;
import com.android.ll.znns.domain.FeedBackDomain;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


/**
 * 用户反馈
 * Created by 822 on 2015/11/19.
 */
public class DlgFeedBack extends DialogFragment {


    @Override
    public void onStart() {
        super.onStart();
        initDialogStyle();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.dialog_feedback, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpViewAndData(view);
    }

    private void initDialogStyle() {
        Dialog dialog =  getDialog();
        if(dialog == null) {
            return;
        }

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
    }

    private void setUpViewAndData(final View view) {

        view.findViewById(R.id.imb_feedback_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAllowingStateLoss();
            }
        });

        view.findViewById(R.id.btn_feedback_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOne(view);
            }
        });

    }

    private void addOne(View view) {
        final Activity activity = getActivity();
        if(activity == null) return;
        String content = ((EditText)view.findViewById(R.id.edt_feedback_content)).getText().toString();
        String address = ((EditText)view.findViewById(R.id.edt_feedback_address)).getText().toString();

        if(TextUtils.isEmpty(content) || TextUtils.isEmpty(address)) {
            Toast.makeText(activity, "请填写相关信息" , Toast.LENGTH_LONG).show();
            return;
        }

        FeedBackDomain entity = new FeedBackDomain();
        entity.setContent(content);
        entity.setAddress(address);

        entity.save(new SaveListener<String>() {
            @Override
            public void done(String objectId,BmobException e) {
                if(e==null){
                    Toast.makeText(activity, "发送成功，我们将尽快与您联系" , Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(activity, "发送失败，请稍后再试", Toast.LENGTH_LONG).show();
                }
            }
        });

        dismissAllowingStateLoss();
    }
}
