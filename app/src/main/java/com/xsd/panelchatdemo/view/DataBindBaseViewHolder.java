package com.xsd.panelchatdemo.view;

import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * @author zhengdaquan
 * @description:
 * @date : 2020/6/8 15:35
 */
public class DataBindBaseViewHolder extends BaseViewHolder {
    private ViewDataBinding binding;

    public DataBindBaseViewHolder(View view) {
        super(view);
        if (itemView.getTag() != null) binding = DataBindingUtil.bind(itemView);
    }

    public ViewDataBinding getBinding() {
        return binding;
    }
}
