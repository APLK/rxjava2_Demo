package com.szinternet.crm.ui.operationsguide;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.shizhefei.view.largeimage.LargeImageView;
import com.shizhefei.view.largeimage.factory.InputStreamBitmapDecoderFactory;
import com.szinternet.crm.R;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.fragment.contract.BaseMainContract;
import com.szinternet.crm.fragment.presenter.BaseMainPresenter;

import java.io.IOException;

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class OperationGuideActivity extends BaseActivity<BaseMainPresenter> implements BaseMainContract.View {


    @BindView(R.id.image)
    LargeImageView mImage;

    @Override
    public int getLayoutId() {
        return R.layout.activity_operation_guide;
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
        int guideType = getIntent().getIntExtra("guideType", 0);
        if (guideType==0){
            //通过流的方式加载assets文件夹里面的大图
            try {
                mImage.setImage(new InputStreamBitmapDecoderFactory(getAssets().open("repay_guide.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            //通过流的方式加载assets文件夹里面的大图
            try {
                mImage.setImage(new InputStreamBitmapDecoderFactory(getAssets().open("credit_guide.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void configViews() {
        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        initTitleBar(true, "操作指南");
    }


    @Override
    protected void processClick(View v) {
    }


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
