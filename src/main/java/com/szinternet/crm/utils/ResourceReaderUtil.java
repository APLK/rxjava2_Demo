package com.szinternet.crm.utils;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class ResourceReaderUtil {

	public static String readString (Context context, int id) {
		return context.getResources().getString(id);
	}
	public static int readDimen(Context context, int id){
		return context.getResources().getDimensionPixelOffset(id);
	}
	public static Drawable readDrawable(Context context, int id){
		return context.getResources().getDrawable(id);
	}
	public static int readColor(Context context, int id){
		return context.getResources().getColor(id);
	}
	public static ColorStateList readColorStateList(Context context, int id){
		return context.getResources().getColorStateList(id);
	}
	
}