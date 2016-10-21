package com.zxdeng.food.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zxdeng.food.event.BaseEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * Desc：</br>
 * Created by Poison on 2016/10/21.
 */

public abstract class BaseFragment extends Fragment {

    protected BaseActivity mActivity;

    //封装抽象方法用于在fragment中初始化控件，增加代码的条理性
    protected abstract void initView(View view, Bundle savedInstanceState);

    //获取fragment布局文件ID
    protected abstract int getLayoutId();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (BaseActivity) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        initView(view, savedInstanceState);
        return view;
    }

    /**
     * eventbus发送事件
     * @param event 事件
     * @param <EVENT> 泛型，必须继承BaseEvent
     */
    public final<EVENT extends BaseEvent> void sendEvent( EVENT event){
        EventBus.getDefault().post(event);
    }
}
