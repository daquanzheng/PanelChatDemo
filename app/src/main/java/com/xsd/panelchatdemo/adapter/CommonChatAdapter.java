package com.xsd.panelchatdemo.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xsd.panelchatdemo.R;
import com.xsd.panelchatdemo.bean.CommonMessageBean;
import com.xsd.panelchatdemo.databinding.ItemChatPanelWordsRecyclerLayoutBinding;
import com.xsd.panelchatdemo.view.DataBindBaseViewHolder;

import java.util.List;


/**
 * @author zhengdaquan
 * @description: 常用语列表
 * @date : 2020/5/7 16:41
 */
public class CommonChatAdapter extends BaseQuickAdapter<CommonMessageBean, DataBindBaseViewHolder> {
    private boolean isCanDelete;

    public CommonChatAdapter(@Nullable List<CommonMessageBean> data) {
        super(R.layout.item_chat_panel_words_recycler_layout, data);
    }

    @Override
    protected void convert(@NonNull DataBindBaseViewHolder helper, CommonMessageBean item) {
        ItemChatPanelWordsRecyclerLayoutBinding mBind = (ItemChatPanelWordsRecyclerLayoutBinding) helper.getBinding();
        mBind.setItem(item);
        mBind.ivDelete.setVisibility(isCanDelete ? View.VISIBLE : View.GONE);
        helper.addOnClickListener(R.id.iv_delete);
    }

    public void setCanDelete(boolean canDelete) {
        isCanDelete = canDelete;
    }
}
