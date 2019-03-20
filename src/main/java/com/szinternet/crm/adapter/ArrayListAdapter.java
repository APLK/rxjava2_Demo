package com.szinternet.crm.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 * @param <T>
 */
public abstract class ArrayListAdapter<T> extends BaseAdapter {
	
	protected ArrayList<T> mList = new ArrayList<T>();
	protected Activity mContext;
	protected ListView mListView;
	protected LayoutInflater mInflater;
	
	public ArrayListAdapter(Activity context){
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if(mList != null)
			return mList.size();
		else
			return 0;
	}

	@Override
	public Object getItem(int position) {
		return mList == null ? null : mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	abstract public View getView(int position, View convertView, ViewGroup parent);
	
	public void setList(ArrayList<T> list) {
		this.mList = list;
		notifyDataSetChanged();
	}
	
	public ArrayList<T> getList(){
		return mList;
	}
	
	public void setList(T[] list){
		ArrayList<T> arrayList = new ArrayList<T>(list.length);
		for (T t : list) {  
			arrayList.add(t);  
		}  
		setList(arrayList);
	}
	
	public void add(T t) {
		mList.add(t);
		notifyDataSetChanged();
	}
	
	public void addList(ArrayList<T> list) {
		mList.addAll(list);
		notifyDataSetChanged();
	}

	public void add(ArrayList<T> list) {
		mList.addAll(list);
		notifyDataSetChanged();
	}

	public ListView getListView(){
		return mListView;
	}
	
	public void setListView(ListView listView){
		mListView = listView;
	}

	public static void setListViewHeightBaseOnChild(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			View listItemView = listAdapter.getView(i, null, listView);
			listItemView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			totalHeight += listItemView.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	public <T> T findViewById(int viewID,View view)
	{
		return (T)view.findViewById(viewID);
	}
}
