package com.szinternet.crm.databean;

public class ProxyBean {
    private int count;
    private int imageResId;
    private String desr;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

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

    public ProxyBean(int imageResId, String desr, int count) {
        this.imageResId = imageResId;
        this.desr = desr;
        this.count = count;
    }
}
