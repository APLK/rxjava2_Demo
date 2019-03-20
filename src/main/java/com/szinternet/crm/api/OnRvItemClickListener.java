package com.szinternet.crm.api;

import android.view.View;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 * @param <T>
 */
public interface OnRvItemClickListener<T> {
    void onItemClick(View view, int position, T data);
}