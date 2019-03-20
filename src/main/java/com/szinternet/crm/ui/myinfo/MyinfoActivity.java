package com.szinternet.crm.ui.myinfo;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.R;
import com.szinternet.crm.base.BaseActivity;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.ui.bindidentity.BindIdentityActivity;
import com.szinternet.crm.utils.EventHelper;
import com.szinternet.crm.utils.FileUtil;
import com.yuyh.easyadapter.glide.GlideCircleTransform;

import java.util.List;

import butterknife.BindView;

import static com.luck.picture.lib.config.PictureMimeType.ofImage;
import static com.szinternet.crm.Url.baseUrl;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class MyinfoActivity extends BaseActivity<MyinfoPresenter> implements MyinfoContract.View {


    @BindView(R.id.tv_avatar)
    ImageView mTvAvatar;
    @BindView(R.id.avatar_view)
    RelativeLayout mAvatarView;
    @BindView(R.id.tv_phonenum)
    TextView mTvPhonenum;
    @BindView(R.id.phone_view)
    RelativeLayout mPhoneView;
    @BindView(R.id.bind_view)
    RelativeLayout mBindView;
    @BindView(R.id.tv_bind)
    TextView mTvBind;
    private List<LocalMedia> selectList;
    private RequestOptions mRequestOptions;

    @Override
    public int getLayoutId() {
        return R.layout.activity_myinfo;
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
        mRequestOptions = new RequestOptions();
        mRequestOptions.placeholder(R.mipmap.default_head);
        mRequestOptions.error(R.mipmap.default_head);
        mRequestOptions.transform(new GlideCircleTransform(this));

        if (CreditCardApplication.getsInstance().account != null) {
            mTvPhonenum.setText(CreditCardApplication.getsInstance().account.username);
            mTvBind.setText(CreditCardApplication.getsInstance().account.real.equalsIgnoreCase("have") ? "已认证" : "未认证");
//            mBindView.setEnabled(CreditCardApplication.getsInstance().account.real.equalsIgnoreCase("have") ? false : true);
            Glide.with(mContext).load(baseUrl + CreditCardApplication.getsInstance().account.img_url).apply(mRequestOptions).into(mTvAvatar);
        }
    }

    @Override
    public void configViews() {
        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        initTitleBar(true, "个人信息");
        EventHelper.click(this, mAvatarView, mBindView);
    }

    public void setSelectPicClick() {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(this)
                .openGallery(ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .previewVideo(false)// 是否可预览视频
                .enablePreviewAudio(false) // 是否可播放音频
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(true)// 是否裁剪
                .compress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .compressSavePath(FileUtil.getCachePath(this))//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(16, 9)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                .isGif(false)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(true)// 是否圆形裁剪
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .openClickSound(false)// 是否开启点击声音
                .selectionMedia(selectList)// 是否传入已选图片
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    Glide.with(mContext).load(selectList.get(0).getCompressPath()).apply(mRequestOptions).into(mTvAvatar);
                    mPresenter.upload_avatar(selectList.get(0).getCompressPath());
                    break;
            }
        }
    }


    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.avatar_view:
                setSelectPicClick();
                break;
            case R.id.bind_view:
                if (CreditCardApplication.getsInstance().account.real.equalsIgnoreCase("have")) {
                    Intent intent = new Intent(this, BindIdentityActivity.class);
                    intent.putExtra("state", true);
                    startActivity(intent);//需要携带信息过去
                } else {
                    start2Activity(BindIdentityActivity.class);
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void start2Activity(Class c) {
        startActivity(new Intent(this, c));
    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void showLoadDialog() {

    }

    @Override
    public void showLoadDialog(String loadingText) {

    }

}
