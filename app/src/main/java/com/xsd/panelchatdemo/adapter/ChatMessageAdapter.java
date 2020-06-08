package com.xsd.panelchatdemo.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xsd.panelchatdemo.R;
import com.xsd.panelchatdemo.bean.ChatMessageBean;
import com.xsd.panelchatdemo.databinding.ItemPersonalChatBinding;
import com.xsd.panelchatdemo.view.DataBindBaseViewHolder;

/**
 * @author zhengdaquan
 * @description:
 * @date : 2020/6/8 15:34
 */
public class ChatMessageAdapter extends BaseQuickAdapter<ChatMessageBean, DataBindBaseViewHolder> {
    public ChatMessageAdapter() {
        super(R.layout.item_personal_chat);
    }

    @Override
    protected void convert(@NonNull DataBindBaseViewHolder helper, ChatMessageBean item) {
        ItemPersonalChatBinding mBind = (ItemPersonalChatBinding) helper.getBinding();
        mBind.setChat(item);
    }

}
