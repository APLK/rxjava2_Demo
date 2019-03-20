package com.szinternet.crm.ui.myinfo;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.api.HttpObserverInterface;
import com.szinternet.crm.api.HttpSubscriber;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.api.RxSchedulers;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.UploadInfo;
import com.szinternet.crm.update.BgUpdate;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.szinternet.crm.api.RxSchedulers.IO_TRANSFORMER;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class MyinfoPresenter extends RxPresenter<MyinfoContract.View> implements MyinfoContract.Presenter<MyinfoContract.View> {

    private Context mContext;
    private NetworkApi netApi;


    @Inject
    public MyinfoPresenter(Context mContext, NetworkApi netApi) {
        this.netApi = netApi;
        this.mContext = mContext;
    }

    //上传图片
    @Override
    public void upload_avatar(String filePath) {
        Map<String, RequestBody> params = new HashMap<>();
        params.put("token", netApi.convertToRequestBody(CreditCardApplication.getsInstance().getToken()));

        List<File> fileList = new ArrayList<>();
        fileList.add(new File(filePath));
        List<MultipartBody.Part> partList = netApi.filesToMultipartBodyParts(fileList);

       netApi.upload_avatar(params, partList)
                .compose(RxSchedulers.<BaseBean<UploadInfo>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<UploadInfo>(mView, mActivity, new HttpObserverInterface<UploadInfo>() {

                    @Override
                    public void onSuccess(BaseBean<UploadInfo> httpResult) {
                        Toast.makeText(mContext, "上传完成", Toast.LENGTH_SHORT).show();
                        if (CreditCardApplication.getsInstance().account != null) {
                            CreditCardApplication.getsInstance().account.img_url = httpResult.getData().img_url;
                        }
                    }

                    @Override
                    public void onError(String msg, int code) {
                    }
                }));
    }

    @Override
    public void downLoadApp() {
        final String url = "https://pro-app-qn.fir.im/3c87c508fcdca4b705e89800a6d6081361be034b.apk?attname=BGAUpdateDemo_v1.0.1_debug.apk_1.0.1.apk&e=1513743719&token=LOvmia8oXF4xnLh0IdH05XMYpH6ENHNpARlmPc-T:wm7E0Hp3y6LP26kiyEao-i4xWc4=";
        BgUpdate.updateForDialog(mContext, url, Environment.getExternalStorageDirectory() + "/xiaohui.apk");
    }

}
