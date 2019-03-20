package com.szinternet.crm.ui.pay;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.szinternet.crm.R;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.fragment.contract.BaseMainContract;
import com.szinternet.crm.fragment.presenter.BaseMainPresenter;
import com.szinternet.crm.ui.quick_payment.QuickPaymentActivity;
import com.szinternet.crm.utils.EventHelper;
import com.szinternet.crm.utils.PayResult;
import com.szinternet.crm.utils.ToastUtils;
import com.szinternet.crm.utils.WXPayUtils;

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class PayActivity extends BaseActivity<BaseMainPresenter> implements BaseMainContract.View {


    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.tv_order_number)
    TextView mTvOrderNumber;
    @BindView(R.id.tv_order_nname)
    TextView mTvOrderNname;
    @BindView(R.id.wx_view)
    RelativeLayout mWxView;
    @BindView(R.id.cb_wx)
    ImageView mCbWx;
    @BindView(R.id.zfb_view)
    RelativeLayout mZfbView;
    @BindView(R.id.cb_zfb)
    ImageView mCbZfb;
    @BindView(R.id.bt_pay)
    Button mBtPay;
    @BindView(R.id.cb_fast)
    ImageView mCbFast;
    @BindView(R.id.fast_view)
    RelativeLayout mFastView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay;
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
    }

    @Override
    public void configViews() {
        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        initTitleBar(true, "升级支付");
        EventHelper.click(this, mWxView, mZfbView, mFastView, mBtPay);
    }


    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.fast_view:
                mCbWx.setSelected(false);
                mCbZfb.setSelected(false);
                mCbFast.setSelected(true);
                break;
            case R.id.zfb_view:
                mCbFast.setSelected(false);
                mCbZfb.setSelected(true);
                mCbWx.setSelected(false);
                break;
            case R.id.wx_view:
                mCbFast.setSelected(false);
                mCbWx.setSelected(true);
                mCbZfb.setSelected(false);
                break;
            case R.id.bt_pay:
                if (mCbZfb.isSelected()) {
                    payInfo("支付宝支付899元");
                } else if (mCbWx.isSelected()) {
                    weixinPay();
                } else if (mCbFast.isSelected()) {
                    ToastUtils.showToast("快捷支付");
                    Intent intent = new Intent(this, QuickPaymentActivity.class);
                    //                    intent.putExtra("creditID", creditID);
                    //                    intent.putExtra("bankCode", bankCode);
                    //                    intent.putExtra("bankID", bankID);
                    //                    intent.putExtra("money", "888");
                    startActivity(intent);
                } else {
                    ToastUtils.showToast("请选择一种支付方式进行支付!");
                }
                break;
            default:
                break;
        }
    }

    private void weixinPay() {
        ToastUtils.showToast("微信支付");
        //假装请求了服务端信息，并获取了appid、partnerId、prepayId
        WXPayUtils.WXPayBuilder builder = new WXPayUtils.WXPayBuilder();
        builder.setAppId("wxd930ea5d5a258f4f")
                .setPartnerId("1900000109")
                .setPrepayId("1101000000140415649af9fc314aa427")
                .setNonceStr("1101000000140429eb40476f8896f4c9")
                .setTimeStamp("1398746574")
                .setSign("7FFECB600D7157C5AA49810D2D8F28BC2811827B")
                .setPackageValue("Sign=WXPay")
                .build()
                .toWXPayAndSign(PayActivity.this, "wxd930ea5d5a258f4f", "key");
    }

    // 支付充值
    public void payInfo(final String payInfo) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(PayActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = 200;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 200: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(PayActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };


    @Override
    public void start2Activity(Class c) {
        startActivity(new Intent(this, c));
    }

    @Override
    public void showLoadDialog() {

    }

    @Override
    public void showLoadDialog(String loadingText) {

    }

}
