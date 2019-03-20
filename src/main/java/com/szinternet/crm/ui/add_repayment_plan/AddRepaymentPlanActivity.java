package com.szinternet.crm.ui.add_repayment_plan;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szinternet.crm.R;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.databean.MyRateBean;
import com.szinternet.crm.databean.MyRepaymentCardBean;
import com.szinternet.crm.datepicker.ConvertUtils;
import com.szinternet.crm.datepicker.DatePicker;
import com.szinternet.crm.dialog.AlertDialog;
import com.szinternet.crm.ui.about.AboutActivity;
import com.szinternet.crm.ui.show_repayment_plan.ShowRepaymentPlanActivity;
import com.szinternet.crm.utils.CommonUtil;
import com.szinternet.crm.utils.EventHelper;
import com.szinternet.crm.utils.LogUtils;
import com.szinternet.crm.utils.ToastUtils;
import com.szinternet.crm.view.ClearEditText;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class AddRepaymentPlanActivity extends BaseActivity<AddPlanPresenter> implements AddPlanContract.View {


    @BindView(R.id.toolbar)
    LinearLayout mToolbar;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.line)
    View mLine;
    @BindView(R.id.tv_limit)
    TextView mTvLimit;
    @BindView(R.id.tv_repayment_day)
    TextView mTvRepaymentDay;
    @BindView(R.id.tv_last_repayment)
    TextView mTvLastRepayment;
    @BindView(R.id.et_money)
    ClearEditText mEtMoney;
    @BindView(R.id.iv_delete_money)
    ImageView mIvDeleteMoney;
    @BindView(R.id.et_server_charge)
    TextView mEtServerCharge;
    @BindView(R.id.tv_start_date)
    TextView mTvStartDate;
    @BindView(R.id.tv_end_date)
    TextView mTvEndDate;
    @BindView(R.id.tv_statement)
    TextView mTvStatement;
    @BindView(R.id.bt_next)
    Button mBtNext;
    @BindView(R.id.tv_bank_name)
    TextView mTvBankName;
    private int mYear;
    private int mMonth;
    private int mDay;
    private String repayRate;
    private MyRepaymentCardBean.DataEntity mData;
    private String mIds;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_repayment_plan;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initDatas() {
        mData = (MyRepaymentCardBean.DataEntity) getIntent().getSerializableExtra("data");
        mPresenter.servercharge();
        showEditDialog();
        mEtMoney.setClearDrawable(null);
        mEtMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                    isShowX(false, mIvDeleteMoney);
                } else {
                    isShowX(true, mIvDeleteMoney);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //计算手续费
                //还款手续费=1000*0.46/100+11(还款每笔+1元 ,共11笔) =15.6元
                int selectionStart = mEtMoney.getSelectionStart();
                int selectionEnd = mEtMoney.getSelectionEnd();
                if (!CommonUtil.isOnlyPointNumber(editable.toString()) && editable.length() > 0) {
                    //删除多余输入的字（不会显示出来）
                    editable.delete(selectionStart - 1, selectionEnd);
                    mEtMoney.setText(editable);
                    mEtMoney.setSelection(editable.length());
                }
                if (TextUtils.isEmpty(editable.toString()) || Float.valueOf(editable.toString()) < 0) {
                    mEtServerCharge.setText("");
                } else {
                    float serverCharge = new BigDecimal(new BigDecimal(editable.toString())
                            .multiply(new BigDecimal(Float.parseFloat(repayRate) / 100)).floatValue() + "")
                            .add(new BigDecimal(11)).floatValue();
                    DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                    String p = decimalFormat.format(serverCharge);//format 返回的是字符串
                    mEtServerCharge.setText(p);
                }
            }
        });
        if (mData != null) {
            mIds = mData.ids;
            mTvBankName.setText(mData.bank_name +
                    mData.nub.substring(mData.nub.length() - 4, mData.nub.length()));
            mName.setText(String.format(mContext.getString(R.string.card_admin),
                    CommonUtil.userNameReplaceWithStar(mData.user)));
            mTvLimit.setText(String.format(mContext.getString(R.string.money_limit),
                    CommonUtil.userNameReplaceWithStar(mData.limit_money)));
            mTvRepaymentDay.setText(String.format(mContext.getString(R.string.repayment_day),
                    CommonUtil.userNameReplaceWithStar(mData.bill_day)));
            mTvLastRepayment.setText(String.format(mContext.getString(R.string.last_repayment),
                    CommonUtil.userNameReplaceWithStar(mData.end_time)));
        }
    }

    @Override
    public void configViews() {
        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        initTitleBar(true, "新增还款计划");
        EventHelper.click(this, mEtMoney, mTvStartDate, mTvEndDate, mIvDeleteMoney, mBtNext, mTvStatement);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        mDay = c.get(Calendar.DAY_OF_MONTH);
    }


    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.iv_delete_money:
                mEtMoney.setText("");
                break;
            case R.id.tv_start_date:
                onYearMonthDayPicker(mTvStartDate, 0);
                break;
            case R.id.tv_end_date:
                onYearMonthDayPicker(mTvEndDate, 1);
                break;
            case R.id.tv_statement:
                start2Activity(AboutActivity.class);
                break;
            case R.id.bt_next:
                String startTime = mTvStartDate.getText().toString();
                String endTime = mTvEndDate.getText().toString();
                if (TextUtils.isEmpty(mEtMoney.getText().toString())) {
                    ToastUtils.showToast("还款金额不能为空");
                    break;
                } else if (TextUtils.isEmpty(startTime)) {
                    ToastUtils.showToast("还款开始时间不能为空");
                    break;
                } else if (TextUtils.isEmpty(endTime)) {
                    ToastUtils.showToast("还款结束时间不能为空");
                    break;
                }
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = format.parse(endTime);
                    date2 = format.parse(startTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int subTime = (int) ((date1.getTime() - date2.getTime()) / (1000 * 3600 * 24)) + 1;
                if (subTime <= 5 || subTime > 30) {
                    ToastUtils.showToast("还款时间间隔不能超过30天且不能少于5天");
                    break;
                } else if (Float.valueOf(mEtMoney.getText().toString()) <= 0) {
                    ToastUtils.showToast("还款金额必须大于0");
                    break;
                } else if (Float.valueOf(mEtServerCharge.getText().toString()) > Float.valueOf(mEtMoney.getText().toString().trim())) {
                    ToastUtils.showToast("手续费已高于您的还款金额,请重新输入还款金额");
                    break;
                }

                //                float v1 = new BigDecimal(mEtMoney.getText().toString())
                //                        .add(new BigDecimal(mEtServerCharge.getText().toString())).floatValue();
                //                DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                //                String p = decimalFormat.format(v1);//format 返回的是字符串
                Intent intent = new Intent(this, ShowRepaymentPlanActivity.class);
                intent.putExtra("ID", mIds);
                intent.putExtra("startTime", startTime);
                intent.putExtra("endTime", endTime);
                //                intent.putExtra("money", Float.valueOf(p));
                intent.putExtra("repaymentMoney", Float.valueOf(mEtMoney.getText().toString()));
                intent.putExtra("serverchange", Float.valueOf(mEtServerCharge.getText().toString().trim()));
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void showEditDialog() {
        new AlertDialog(AddRepaymentPlanActivity.this).builder().setTitle("温馨提示")
                .setMsg("还款期间请保证信用卡状态正常,请勿调整临时额度,不动用每次还款的资金,否则可能导致还款失败哦!")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
    }

    public void onYearMonthDayPicker(final TextView view, int type) {
        final DatePicker picker = new DatePicker(this);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(this, 10));
        LogUtils.i("1", "mYear=" + mYear + ",mMonth=" + mMonth + ",mDay=" + mDay);
        String[] splitStart;
        String[] splitEnd;
        if (type == 0) {//开始
            if (!TextUtils.isEmpty(mTvEndDate.getText().toString())) {
                splitEnd = mTvEndDate.getText().toString().split("-");
                picker.setRangeEnd(Integer.valueOf(splitEnd[0]), Integer.valueOf(splitEnd[1]), Integer.valueOf(splitEnd[2]));
                picker.setRangeStart(mYear, mMonth, mDay);
                picker.setSelectedItem(mYear, mMonth, mDay);
            } else {
                picker.setRangeEnd(2035, 1, 1);
                picker.setRangeStart(mYear, mMonth, mDay);
                picker.setSelectedItem(mYear, mMonth, mDay);
            }
        } else {//结束
            if (!TextUtils.isEmpty(mTvStartDate.getText().toString())) {
                splitStart = mTvStartDate.getText().toString().split("-");
                picker.setRangeEnd(2035, 1, 1);
                picker.setRangeStart(Integer.valueOf(splitStart[0]), Integer.valueOf(splitStart[1]), Integer.valueOf(splitStart[2]));
                picker.setSelectedItem(Integer.valueOf(splitStart[0]), Integer.valueOf(splitStart[1]), Integer.valueOf(splitStart[2]));
            } else {
                picker.setRangeEnd(2035, 1, 1);
                picker.setRangeStart(mYear, mMonth, mDay);
                picker.setSelectedItem(mYear, mMonth, mDay);
            }
        }
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                view.setText(year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }


    @Override
    public void start2Activity(Class c) {
        startActivity(new Intent(this, c));
    }

    @Override
    public void showLoadDialog() {
        super.showDialog();
    }

    @Override
    public void showLoadDialog(String loadingText) {

    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onSuccess(MyRateBean bean) {
        if (bean != null && !TextUtils.isEmpty(bean.repay_rate)) {
            repayRate = bean.repay_rate;
        } else {
            mPresenter.servercharge();
        }
    }


    @Override
    public void isShowX(boolean isShow, ImageView imageView) {
        if (!isShow) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
        }
    }

}
