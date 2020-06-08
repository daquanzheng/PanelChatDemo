package com.xsd.panelchatdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.effective.android.panel.PanelSwitchHelper;
import com.effective.android.panel.interfaces.listener.OnKeyboardStateListener;
import com.effective.android.panel.interfaces.listener.OnPanelChangeListener;
import com.effective.android.panel.utils.PanelUtil;
import com.effective.android.panel.view.panel.IPanelView;
import com.effective.android.panel.view.panel.PanelView;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.xsd.panelchatdemo.adapter.ChatMessageAdapter;
import com.xsd.panelchatdemo.adapter.CommonChatAdapter;
import com.xsd.panelchatdemo.base.BaseBindActivity;
import com.xsd.panelchatdemo.bean.ChatMessageBean;
import com.xsd.panelchatdemo.bean.CommonMessageBean;
import com.xsd.panelchatdemo.databinding.NewPersonalChatActivityBinding;
import com.xsd.panelchatdemo.utils.L;
import com.xsd.panelchatdemo.view.CusRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseBindActivity<NewPersonalChatActivityBinding> implements View.OnClickListener {

    private PanelSwitchHelper mHelper;
    List<ChatMessageBean> chatList;//聊天列表
    ChatMessageAdapter mAdapter;
    List<CommonMessageBean> commonList;//常用语列表
    CommonChatAdapter commonChatAdapter;
    RecyclerView commonRecyclerView;
    EditText etWords;
    private LinearLayoutManager mLinearLayoutManager;
    boolean isCanDelete;

    @Override
    protected int getLayoutId() {
        return R.layout.new_personal_chat_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).statusBarColor(R.color.white).statusBarDarkFont(true).init();
        db.getRoot().setFitsSystemWindows(true);
        initView();
        onEvent();
        initData();
    }


    private void initView() {
        mLinearLayoutManager = new LinearLayoutManager(this);
        db.recyclerView.setLayoutManager(mLinearLayoutManager);
        db.tvTitle.setText("小二不拉客");
        chatList = new ArrayList<>();
        commonList = new ArrayList<>();
        commonRecyclerView = db.getRoot().findViewById(R.id.recycler_common_chat);
        etWords = db.getRoot().findViewById(R.id.et_words);
        mAdapter = new ChatMessageAdapter();
        db.recyclerView.setAdapter(mAdapter);

        commonChatAdapter = new CommonChatAdapter(commonList);
        commonRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commonRecyclerView.setAdapter(commonChatAdapter);

    }

    private void onEvent() {

        db.ivSend.setOnClickListener(this);
        db.getRoot().findViewById(R.id.tv_add).setOnClickListener(v -> {
//            mHelper.resetState();
//            db.panelUsefulWords.setVisibility(View.GONE);
//            mHelper.toKeyboardState();
            db.panelEditWords.setVisibility(View.VISIBLE);

        });

        etWords.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    PanelUtil.showKeyboard(MainActivity.this, v);
                }
                L.e("----------我获得了焦点---------");
            }
        });

        //保存常用语
        db.getRoot().findViewById(R.id.tv_submit).setOnClickListener(v -> {


            if (TextUtils.isEmpty(etWords.getText().toString())) {
                showToast("请输入内容");
                return;
            }

            CommonMessageBean bean = new CommonMessageBean();
            bean.setContent(etWords.getText().toString());
            commonChatAdapter.addData(bean);
            etWords.setText("");
            PanelUtil.hideKeyboard(MainActivity.this, etWords);
            db.panelEditWords.setVisibility(View.GONE);
            db.panelUsefulWords.setVisibility(View.VISIBLE);
            db.tvUsefulWords.setSelected(true);
        });

        //编辑常用语
        db.getRoot().findViewById(R.id.tv_edit).setOnClickListener(v -> {
            isCanDelete = !isCanDelete;
            commonChatAdapter.setCanDelete(isCanDelete);
            commonChatAdapter.notifyDataSetChanged();
        });
        commonChatAdapter.setOnItemClickListener((adapter, view, position) -> {
            CommonMessageBean item = (CommonMessageBean) adapter.getData().get(position);
            sendMessage(item.getContent());

        });
        commonChatAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            adapter.remove(position);
            adapter.notifyDataSetChanged();
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (mHelper != null) {
        mHelper = new PanelSwitchHelper.Builder(this).addKeyboardStateListener((visible, height) -> {
            L.d("------系统键盘是否可见----" + visible);
            scrollToBottom();
        }).addEditTextFocusChangeListener((view, hasFocus) -> {

            L.d("输入框是否获得焦点 : " + hasFocus);
            if (hasFocus) {
                scrollToBottom();
            }
        }).addViewClickListener(view -> {
            switch (view.getId()) {
                case R.id.et_chat:
                case R.id.tv_useful_words:
                    scrollToBottom();
            }

        })
                .addPanelChangeListener(new OnPanelChangeListener() {
                    @Override
                    public void onKeyboard() {
                        db.tvUsefulWords.setSelected(false);
                    }

                    @Override
                    public void onNone() {
                        db.tvUsefulWords.setSelected(false);
                    }

                    @Override
                    public void onPanel(IPanelView iPanelView) {
                        if (iPanelView instanceof PanelView) {
                            if (((PanelView) iPanelView).getId() == R.id.panel_useful_words) {

                                db.tvUsefulWords.setSelected(true);

                                PanelUtil.hideKeyboard(MainActivity.this, etWords);

                            } else {
                                db.tvUsefulWords.setSelected(false);
                            }
                        }
                        scrollToBottom();
                    }

                    @Override
                    public void onPanelSizeChange(IPanelView iPanelView, boolean b, int i, int i1, int i2, int i3) {

                    }
                })
                .contentCanScrollOutside(false)
                .logTrack(true)
                .build();

        db.recyclerView.setResetPanel(new CusRecyclerView.ResetPanel() {
            @Override
            public void resetPanel() {
                mHelper.hookSystemBackByPanelSwitcher();
            }
        });
//        }
    }

    private void initData() {
        //添加聊天
        for (int i = 0; i < 6; i++) {
            ChatMessageBean bean = new ChatMessageBean();
            bean.setContent("聊天信息" + i);
            chatList.add(bean);
        }
        mAdapter.setNewData(chatList);
        for (int i = 0; i < 5; i++) {
            CommonMessageBean bean = new CommonMessageBean();
            bean.setContent("常用语" + i);
            commonList.add(bean);
        }
        commonChatAdapter.setNewData(commonList);
        scrollToBottom();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_send:
                if (TextUtils.isEmpty(db.etChat.getText().toString())) {
                    showToast("请输入内容");
                    return;
                }
                sendMessage(db.etChat.getText().toString());
                db.etChat.setText("");
                break;

        }
    }

    private void sendMessage(String message) {
        ChatMessageBean chatMessageBean = new ChatMessageBean();
        chatMessageBean.setContent(message);
        mAdapter.addData(chatMessageBean);
        scrollToBottom();
    }

    private void scrollToBottom() {
        db.getRoot().post(new Runnable() {
            @Override
            public void run() {
                mLinearLayoutManager.scrollToPosition(mAdapter.getItemCount() - 1);
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (mHelper != null && mHelper.hookSystemBackByPanelSwitcher()) {
            return;
        }
        super.onBackPressed();
    }

}
