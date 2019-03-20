package com.szinternet.crm.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.szinternet.crm.R;
import com.yuyh.easyadapter.abslistview.EasyLVAdapter;
import com.yuyh.easyadapter.abslistview.EasyLVHolder;

import java.util.List;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class UpdateTextAdapter extends EasyLVAdapter<String> {


    public UpdateTextAdapter(Context context, List<String> list) {
        super(context, list, R.layout.text_item);
    }

    @Override
    public void convert(EasyLVHolder holder, int position, String chapterCount) {
        if (!TextUtils.isEmpty(chapterCount)) {
            ((TextView) holder.getView(R.id.text)).setText(chapterCount);
        }
    }

}
