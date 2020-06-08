package com.xsd.panelchatdemo.bean;

/**
 * @author zhengdaquan
 * @description:通用消息
 * @date : 2020/5/7 15:55
 */

public class CommonMessageBean {
    /**
     * id : 2
     * user_id : 0
     * type : 1
     * status : 2
     * content : 【系统】测试消费者预设消息1
     * type_text : 消费者
     * status_text : 已审核
     */

    private int id;
    private int user_id;
    private int type;
    private int status;
    private String content;
    private String type_text;
    private String status_text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType_text() {
        return type_text;
    }

    public void setType_text(String type_text) {
        this.type_text = type_text;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }


}
