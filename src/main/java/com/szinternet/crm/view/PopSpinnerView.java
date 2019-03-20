package com.szinternet.crm.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szinternet.crm.R;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.utils.InputUtils;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class PopSpinnerView extends RelativeLayout {

    private final int SPACETOLEFTIMAGE = 12;  //文字距离左边图标像素距离

    /**
     * 自定义属性的参数 开始
     */
    private String textName; //未选择时提示的文字
    //    private int itemWidth;  //控件宽度
    private Drawable LeftImageDrawable;
    /**
     * 自定义属性的参数 结束
     */

    private ImageView mIv_arrow, mIv_leftIcon;
    private TextView mTv_content;
    private PopupWindow popupWindow;
    private ListView lv;
    //    private int height;
    private int listSize;
    private int curIndex = -1;
    private NameFilter nameFilter;
    private OnItemChecked checkedListener;
    private int width;
    private int footSize = 16;
    private float textSize;

    public PopSpinnerView(final Context context) {
        super(context);
        initView(context);
    }

    public PopSpinnerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initXmlConfigAttr(attrs);
    }

    public PopSpinnerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initXmlConfigAttr(attrs);
    }

    public interface OnItemChecked {
        void onChecked(int type);
    }

    public void setOnItemChecked(OnItemChecked listener) {
        checkedListener = listener;
    }


    public int getViewWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * 外部调用，用于初始化控件
     *
     * @param size
     * @param nameFilter
     */
    public void init(int size, NameFilter nameFilter) {
        this.listSize = size;
        this.nameFilter = nameFilter;
    }


    private void initView(final Context context) {
        //将控件布局填充进来
        LayoutInflater.from(context).inflate(R.layout.popspinerview, this);
        mIv_arrow = (ImageView) findViewById(R.id.iv_arrow);
        mIv_leftIcon = (ImageView) findViewById(R.id.iv_leftImage);
        mTv_content = (TextView) findViewById(R.id.tv_content);
        mTv_content.setTextSize(footSize);

    }

    public void showPop(Context context) {
        InputUtils.close((BaseActivity) context);
        View view = LayoutInflater.from(context).inflate(R.layout.pop_layout, null);
        initListView(context, view);
        initPopWindow(view, context);
        arrowRotation(0, -180f);
    }

    private void initListView(final Context context, View view) {
        lv = (ListView) view.findViewById(R.id.lv);
        lv.setAdapter(new MyAdapter(context));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //记录选中的角标
                curIndex = position;
                mTv_content.setText(nameFilter.filter(position));
                if (!TextUtils.isEmpty(mTv_content.getText())
                        && mTv_content.getText().toString().contains("请选择银行")) {
                    mTv_content.setTextColor(context.getResources().getColor(R.color.blue));
                } else {
                    mTv_content.setTextColor(context.getResources().getColor(R.color.common_h1));
                }
                if (checkedListener != null) {
                    checkedListener.onChecked(position);
                }
                popupWindow.dismiss();
            }
        });
    }

    public void dissmissPop() {
        if (popupWindow != null) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }


    private void initPopWindow(View view, Context context) {
        //        height = listSize >= 8 ? 387 : (listSize * 80 - 50) < 80 ? 80 : (listSize * 80 - 75);
        popupWindow = new PopupWindow(view);
        popupWindow.setWidth(width);
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(mTv_content);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //                changeTextBackground(R.drawable.shape_select_bg);
                arrowRotation(-180f, 0);
            }
        });
    }

    /**
     * 设置默认显示的文字
     *
     * @param content
     */
    public void setContent(String content) {
        mTv_content.setText(content);
    }

    public String getContent() {
        CharSequence text = mTv_content.getText();
        if (text == null)
            return "";
        return text.toString();
    }

    class MyAdapter extends BaseAdapter {
        private Context context;

        public MyAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return listSize;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_pop_category, null);
                holder.tv = (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv.setTextSize(footSize);
            if (nameFilter.filter(position).contains("请选择银行")) {
                holder.tv.setTextColor(context.getResources().getColor(R.color.blue));
            } else {
                holder.tv.setTextColor(context.getResources().getColor(R.color.common_h2));
            }
            holder.tv.setText(nameFilter.filter(position));
            return convertView;
        }
    }

    class ViewHolder {
        TextView tv;
    }

    /**
     * xml属性
     *
     * @param attrs
     */
    private void initXmlConfigAttr(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.PopSpinnerView);
        LeftImageDrawable = ta.getDrawable(R.styleable.PopSpinnerView_LeftImageDrawable);
        if (LeftImageDrawable != null) {
            mIv_leftIcon.setVisibility(VISIBLE);
            mIv_leftIcon.setImageDrawable(LeftImageDrawable);
            mTv_content.setPadding(LeftImageDrawable.getIntrinsicWidth() + SPACETOLEFTIMAGE + mTv_content.getPaddingLeft(), 0, 0, 0);
        } else {
            mIv_leftIcon.setVisibility(GONE);
        }
        textName = ta.getString(R.styleable.PopSpinnerView_textName);
        textSize = ta.getInt(R.styleable.PopSpinnerView_textSize, 14);
        if (!TextUtils.isEmpty(textName)) {
            mTv_content.setText(textName);
            mTv_content.setTextSize(textSize);
        }
        //        itemWidth = ta.getInt(R.styleable.PopSpinnerView_popWidth, 0);

        ta.recycle();
    }

    /**
     * 内容回调接口
     */
    public interface NameFilter {
        String filter(int position);
    }

    /**
     * 获取当前选择的item的index
     *
     * @return
     */
    public int getSelectIndex() {
        return curIndex;
    }

    /**
     * 设置选中的item的index
     *
     * @return
     */
    public void setSelectIndex(int index) {
        curIndex = index;
    }

    /**
     * 设置文字大小
     *
     * @return
     */
    public void setTextSize(int size) {
        footSize = size;
    }

    private void arrowRotation(float angleStart, float angleEnd) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(mIv_arrow, "rotation", angleStart, angleEnd);
        anim.setDuration(500);
        anim.start();
    }

    private void changeTextBackground(int res) {
        int paddingLeft = mTv_content.getPaddingLeft();
        mTv_content.setBackgroundResource(res);
        mTv_content.setPadding(paddingLeft, 0, 0, 0);
    }
}
