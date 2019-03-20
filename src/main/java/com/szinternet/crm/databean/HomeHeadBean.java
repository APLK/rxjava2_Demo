package com.szinternet.crm.databean;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class HomeHeadBean {
    private int imageResId;
    private String desr;

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getDesr() {
        return desr;
    }

    public void setDesr(String desr) {
        this.desr = desr;
    }

    public HomeHeadBean(int imageResId, String desr) {
        this.imageResId = imageResId;
        this.desr = desr;
    }
}
