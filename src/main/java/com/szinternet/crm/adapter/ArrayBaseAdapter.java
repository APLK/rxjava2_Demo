package com.szinternet.crm.adapter;

import android.app.Activity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 实现对BaseAdapter中ViewHolder相关的简化
 * 适合只有一种布局的ListView
 */
public abstract class ArrayBaseAdapter<T> extends ArrayListAdapter<T> {


    public ArrayBaseAdapter(Activity context) {
        super(context);
    }

    /**
     * 各个控件的缓存
     */
    public class ViewHolder {
        public SparseArray<View> views = new SparseArray<View>();

        /**
         * 指定resId和类型即可获取到相应的view
         *
         * @param convertView
         * @param resId
         * @param <T>
         * @return
         */
        public <T extends View> T obtainView(View convertView, int resId) {
            View v = views.get(resId);
            if (null == v) {
                v = convertView.findViewById(resId);
                views.put(resId, v);
            }
            return (T) v;
        }

    }

    /**
     * 改方法需要子类实现，需要返回item布局的resource id
     *
     * @return
     */
    public abstract int initLayoutRes();

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(initLayoutRes(), null);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        T t=getList().get(position);
        return getView(position, convertView, parent, holder,t);
    }

    /**
     * 使用该getView方法替换原来的getView方法，需要子类实现
     *
     * @param position
     * @param convertView
     * @param parent
     * @param holder
     * @return
     */
    public abstract View getView(int position, View convertView, ViewGroup parent, ViewHolder holder, T t);

}