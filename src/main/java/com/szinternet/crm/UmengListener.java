package com.szinternet.crm;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.szinternet.crm.utils.LogUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;


public class UmengListener {

    private Activity mAty;

    public UmengListener(Activity aty) {
        mAty = aty;
    }

    /**
     * 友盟分享回调监听器
     */
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
            LogUtils.d("1", "SHARE_MEDIA=" + platform);
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            LogUtils.d("1", "platform=" + platform);
            final String platName = getPlatName(platform);
            mAty.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(mAty, "分享到" + platName + "成功啦", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            final String platName = getPlatName(platform);
            mAty.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(mAty, "分享到" + platName + "失败啦", Toast.LENGTH_SHORT).show();
                }
            });
            if (t != null) {
                LogUtils.d("1", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            final String platName = getPlatName(platform);
            mAty.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(mAty, "分享到" + platName + "取消啦", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    @NonNull
    private String getPlatName(SHARE_MEDIA platform) {
        String platName = "";
        if (SHARE_MEDIA.SINA.equals(platform)) {
            platName = "新浪";
        } else if (SHARE_MEDIA.QQ.equals(platform)) {
            platName = "QQ";
        } else if (SHARE_MEDIA.QZONE.equals(platform)) {
            platName = "QQ空间";
        } else if (SHARE_MEDIA.WEIXIN.equals(platform)) {
            platName = "微信";
        } else if (SHARE_MEDIA.WEIXIN_CIRCLE.equals(platform)) {
            platName = "朋友圈";
        }
        return platName;
    }


    /**
     * 设置分享样式内容
     *
     * @param title
     * @param url
     */
    public void shareUmeng(String title, String url, String introduce) {
        UMImage image = new UMImage(mAty, R.mipmap.ic_launcher);
        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        web.setDescription(introduce);//描述
        web.setThumb(image);  //缩略图
        //新浪,qq,空间,微信,朋友圈
        new ShareAction(mAty).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .withMedia(web)
                .setCallback(umShareListener)
                .open();
    }

}
