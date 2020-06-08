package com.xsd.panelchatdemo.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * @author zhengdaquan
 * @description:
 * @date : 2020/6/8 15:17
 */
public abstract class BaseBindActivity<DB extends ViewDataBinding> extends AppCompatActivity {
    protected DB db;

    protected abstract int getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = DataBindingUtil.setContentView(this, getLayoutId());
    }
    /**
     * 显示提示信息
     *
     * @param message
     */
    public void showToast(String message) {

        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_SHORT).show();

    }

}
