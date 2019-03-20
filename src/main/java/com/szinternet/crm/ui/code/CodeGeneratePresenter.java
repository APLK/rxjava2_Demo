package com.szinternet.crm.ui.code;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.widget.Toast;

import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.api.HttpObserverInterface;
import com.szinternet.crm.api.HttpSubscriber;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.api.RxSchedulers;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.ShareDownUrlBean;
import com.szinternet.crm.utils.ToastUtils;

import javax.inject.Inject;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static com.szinternet.crm.api.RxSchedulers.IO_TRANSFORMER;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class CodeGeneratePresenter extends RxPresenter<CodeGenerateContract.View> implements CodeGenerateContract.Presenter<CodeGenerateContract.View> {

    private Context mContext;
    private NetworkApi netApi;


    @Inject
    public CodeGeneratePresenter(Context mContext, NetworkApi netApi) {
        this.netApi = netApi;
        this.mContext = mContext;
    }

    public void codeGenerate(final String url) {
        mView.showLoadDialog();
        Observable.just(url).map(new Function<String, Bitmap>() {
            @Override
            public Bitmap apply(String s) throws Exception {
                return QRCodeEncoder.syncEncodeQRCode(url,
                        BGAQRCodeUtil.dp2px(mContext, 150),
                        Color.parseColor("#ff000000"));
            }
        }).compose(RxSchedulers.<Bitmap>applySchedulers(IO_TRANSFORMER))
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        if (bitmap != null) {
                            mView.onSuccess(bitmap);
                        } else {
                            Toast.makeText(mContext, "生成二维码失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void getShareImgURL() {
        mView.showLoadDialog();
        netApi.shareImgUrl(CreditCardApplication.getsInstance().getToken())
                .compose(RxSchedulers.<BaseBean<ShareDownUrlBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<ShareDownUrlBean>(mView, mActivity, new HttpObserverInterface<ShareDownUrlBean>() {
                    @Override
                    public void onSuccess(BaseBean<ShareDownUrlBean> httpResult) {
                        mView.onSuccess(httpResult.getData().qr_url);
                        if (!TextUtils.isEmpty(httpResult.getData().qr_url)) {
                            codeGenerate(httpResult.getData().qr_url);
                        }
                    }

                    @Override
                    public void onError(String msg, int code) {
                        ToastUtils.showToast("生成二维码失败,请重试!");
                    }

                }));
    }
}
