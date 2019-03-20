package com.szinternet.crm.databean;

import java.io.Serializable;

public class MessageBean implements Serializable {
    /**
     * create_time : 2018-01-03
     * contents : 内容2
     * id : 2
     * titles : 测试2
     */
    public String create_time;
    public String contents;
    public String ids;
    public String titles;

    public String getTitle() {
        return titles;
    }

    public void setTitle(String title) {
        this.titles = title;
    }

    public String getContent() {
        return contents;
    }

    public void setContent(String content) {
        this.contents = content;
    }

    public MessageBean(String title, String content) {
        this.titles = title;
        this.contents = content;
    }

}
