package com.xsd.panelchatdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author zhengdaquan
 * @description:
 * @date : 2020/6/3 14:16
 */
public class CusRecyclerView extends RecyclerView {
    public boolean startScroll = false;
    private ResetPanel resetPanel;

    public CusRecyclerView(Context context) {
        super(context);
    }

    public CusRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CusRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutManager(new LinearLayoutManager(context));
    }

    public void setResetPanel(ResetPanel resetPanel) {
        this.resetPanel = resetPanel;
        setItemAnimator(null);
    }

    public interface ResetPanel {
        void resetPanel();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        boolean result = super.onTouchEvent(e);
        if (e.getAction() == MotionEvent.ACTION_DOWN && result) {
            startScroll = false;
        }
        if (e.getAction() == MotionEvent.ACTION_SCROLL && result) {
            startScroll = true;
        }
        if (e.getAction() == MotionEvent.ACTION_UP && result) {
            if (resetPanel != null && !startScroll) {
                resetPanel.resetPanel();
            }
        }
        return result;
    }
}
