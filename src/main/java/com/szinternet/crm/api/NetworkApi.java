package com.szinternet.crm.api;


import com.google.gson.Gson;
import com.szinternet.crm.Url;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.databean.AllBankBean;
import com.szinternet.crm.databean.BindInfoBean;
import com.szinternet.crm.databean.CashRecordBean;
import com.szinternet.crm.databean.CollectInfoBean;
import com.szinternet.crm.databean.CreditCollectInfoBean;
import com.szinternet.crm.databean.FrCashIndexBean;
import com.szinternet.crm.databean.InsuranceInfoBean;
import com.szinternet.crm.databean.LoginResultBean;
import com.szinternet.crm.databean.MemberDetailBean;
import com.szinternet.crm.databean.MembersBean;
import com.szinternet.crm.databean.MembersCountBean;
import com.szinternet.crm.databean.MessageBean;
import com.szinternet.crm.databean.MyCardBean;
import com.szinternet.crm.databean.MyGainBean;
import com.szinternet.crm.databean.MyGradeBean;
import com.szinternet.crm.databean.MyRateBean;
import com.szinternet.crm.databean.MyRepaymentCardBean;
import com.szinternet.crm.databean.PlanDetailInfoBean;
import com.szinternet.crm.databean.RankBean;
import com.szinternet.crm.databean.RankOneBean;
import com.szinternet.crm.databean.RegisterResultBean;
import com.szinternet.crm.databean.RepaymentPeriodsBean;
import com.szinternet.crm.databean.ServerPhoneBean;
import com.szinternet.crm.databean.ShareDownUrlBean;
import com.szinternet.crm.databean.SpendRecordBean;
import com.szinternet.crm.databean.TradeLogBean;
import com.szinternet.crm.databean.UpclassBean;
import com.szinternet.crm.databean.UploadInfo;
import com.szinternet.crm.databean.VersionConfigBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 *
 * @des Retrofit网络请求封装
 */
public class NetworkApi {
    public static NetworkApi instance;
    private NetworkApiServeice mApiServeice;

    public NetworkApi(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Url.baseUrl)//添加url地址
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加Rx适配器
                .addConverterFactory(GsonConverterFactory.create(new Gson()))//添加gson转换器
                .addConverterFactory(ScalarsConverterFactory.create())//添加string转换器
                .client(okHttpClient)//添加请求
                .build();
        mApiServeice = retrofit.create(NetworkApiServeice.class);
    }


    public static NetworkApi getInstance(OkHttpClient okHttpClient) {
        if (instance == null) {
            instance = new NetworkApi(okHttpClient);
        }
        return instance;
    }

    public RequestBody convertToRequestBody(String param) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                param);
        return requestBody;
    }

    public List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files) {
        List<MultipartBody.Part>
                parts = new ArrayList<>(files.size());
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("mulitpart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("img", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }

    public Observable<BaseBean<RegisterResultBean>> register(Map<String, RequestBody> bodyMap) {
        return mApiServeice.register(bodyMap);
    }

    public Observable<BaseBean<LoginResultBean>> login(Map<String, RequestBody> bodyMap) {
        return mApiServeice.login(bodyMap);
    }

    public Observable<BaseBean<VersionConfigBean>> checkVersion(Map<String, RequestBody> bodyMap) {
        return mApiServeice.checkVersion(bodyMap);
    }

    public Observable<BaseBean<BaseBean>> sendCode(String username) {
        return mApiServeice.sendRegisterCode(username);
    }

    public Observable<BaseBean<BaseBean>> updatePwd(Map<String, RequestBody> bodyMap) {
        return mApiServeice.updatePwd(bodyMap);
    }

    public Observable<BaseBean<BaseBean>> forgetPwd(Map<String, RequestBody> bodyMap) {
        return mApiServeice.forgetPwd(bodyMap);
    }

    public Observable<BaseBean<BaseBean>> sendUpdatePwdCode(String username) {
        return mApiServeice.sendUpdatePwdCode(username);
    }

    public Observable<BaseBean<BaseBean>> logout(String token) {
        return mApiServeice.logout(token);
    }

    public Observable<BaseBean<BaseBean>> bindBankCode(Map<String, RequestBody> bodyMap) {
        return mApiServeice.bindBankCode(bodyMap);
    }

    public Observable<BaseBean<BindInfoBean>> bindInfo(String token) {
        return mApiServeice.bindInfo(token);
    }

    public Observable<BaseBean<BaseBean>> sendRealNameCode(String token, String phone) {
        return mApiServeice.sendRealNameCode(token, phone);
    }

    public Observable<BaseBean<AllBankBean>> getAllBankList(String token) {
        return mApiServeice.getAllBankList(token);
    }

    public Observable<BaseBean<MessageBean>> sysMessage(String token) {
        return mApiServeice.sysMessage(token);
    }

    public Observable<BaseBean<MyRateBean>> myRate(String token) {
        return mApiServeice.myRate(token);
    }

    public Observable<BaseBean<MyGradeBean>> myGrade(String token) {
        return mApiServeice.myGrade(token);
    }

    public Observable<BaseBean<UpclassBean>> upclass(String token, String grade) {
        return mApiServeice.upclass(token, grade);
    }

    public Observable<BaseBean<MyCardBean>> myBank(String token) {
        return mApiServeice.myBank(token);
    }

    public Observable<BaseBean<BaseBean>> deleterc(String token, String id) {
        return mApiServeice.deleterc(token, id);
    }

    public Observable<BaseBean<BaseBean>> updaterc(Map<String, RequestBody> bodyMap) {
        return mApiServeice.updaterc(bodyMap);
    }

    public Observable<BaseBean<PlanDetailInfoBean>> planDetailInfo(String token, String id) {
        return mApiServeice.planDetailInfo(token, id);
    }

    public Observable<BaseBean<RepaymentPeriodsBean>> planPeriodsList(String token, String id, String type) {
        return mApiServeice.planPeriodsList(token, id, type);
    }

    public Observable<BaseBean<ServerPhoneBean>> webphone() {
        return mApiServeice.webphone();
    }

    public Observable<BaseBean<MyGainBean>> myGain(String token) {
        return mApiServeice.myGain(token);
    }

    public Observable<BaseBean<FrCashIndexBean>> frcashIndex(String token) {
        return mApiServeice.frcashIndex(token);
    }

    public Observable<BaseBean<BaseBean>> frcash(String token, String money) {
        return mApiServeice.frcash(token, money);
    }

    public Observable<BaseBean<CashRecordBean>> gainList(String token, String pageIndex, String limit) {
        return mApiServeice.gainList(token, pageIndex, limit);
    }


    public Observable<BaseBean<CashRecordBean>> spList(String token, String pageIndex, String limit) {
        return mApiServeice.spList(token, pageIndex, limit);
    }

    public Observable<BaseBean<CashRecordBean>> promoteList(String token, String pageIndex, String limit) {
        return mApiServeice.promoteList(token, pageIndex, limit);
    }

    public Observable<BaseBean<CashRecordBean>> cashList(String token, String type, String pageIndex, String limit) {
        return mApiServeice.cashList(token, type, pageIndex, limit);
    }

    public Observable<BaseBean<SpendRecordBean>> planList(String token, String type, String pageIndex, String limit) {
        return mApiServeice.planList(token, type, pageIndex, limit);
    }

    public Observable<BaseBean<TradeLogBean>> defrayLog(String token) {
        return mApiServeice.defrayLog(token);
    }

    public Observable<BaseBean<BaseBean>> addRepaymentCredit(Map<String, RequestBody> bodyMap) {
        return mApiServeice.addRepaymentCredit(bodyMap);
    }

    public Observable<BaseBean<BaseBean>> addPay(Map<String, RequestBody> bodyMap) {
        return mApiServeice.addPay(bodyMap);
    }

    public Observable<BaseBean<BaseBean>> addDefrayrcCredit(Map<String, RequestBody> bodyMap) {
        return mApiServeice.addDefrayrcCredit(bodyMap);
    }

    public Observable<BaseBean<RankOneBean>> rankOne() {
        return mApiServeice.rankOne();
    }

    public Observable<BaseBean<RankBean>> rankList(String type) {
        return mApiServeice.rankList(type);
    }

    public Observable<BaseBean<MembersCountBean>> myagents(String token) {
        return mApiServeice.myagents(token);
    }

    public Observable<BaseBean<MembersCountBean>> mychildren(String token) {
        return mApiServeice.mychildren(token);
    }

    public Observable<BaseBean<MembersBean>> agentsList(String token, String type) {
        return mApiServeice.agentsList(token, type);
    }

    public Observable<BaseBean<MembersBean>> childrensList(String token, String type) {
        return mApiServeice.childrensList(token, type);
    }

    public Observable<BaseBean<MemberDetailBean>> childInfo(String token, String id) {
        return mApiServeice.childInfo(token, id);
    }

    public Observable<BaseBean<MyRepaymentCardBean>> repaymentList(String token) {
        return mApiServeice.repaymentList(token);
    }

    public Observable<BaseBean<AllBankBean>> defrayList(String token) {
        return mApiServeice.defrayList(token);
    }

    public Observable<BaseBean<CreditCollectInfoBean>> income(String token) {
        return mApiServeice.income(token);
    }

    public Observable<BaseBean<MyRateBean>> servercharge(String token) {
        return mApiServeice.servercharge(token);
    }

    public Observable<BaseBean<BaseBean>> sendRepaymentCreditCode(String token, String phone) {
        return mApiServeice.sendRepaymentCreditCode(token, phone);
    }

    public Observable<BaseBean<CollectInfoBean>> sendCollectCode(String token, String user_bank_id,
                                                                 String rc_bank_id, String money) {
        return mApiServeice.sendCollectCode(token, user_bank_id, rc_bank_id, money);
    }

    public Observable<BaseBean<BaseBean>> cashPay(Map<String, RequestBody> bodyMap) {
        return mApiServeice.cashPay(bodyMap);
    }


    public Observable<BaseBean<InsuranceInfoBean>> myInsurance(String token) {
        return mApiServeice.myInsurance(token);
    }

    public Observable<BaseBean<ShareDownUrlBean>> shareImgUrl(String token) {
        return mApiServeice.shareImgUrl(token);
    }

    public Observable<BaseBean<ShareDownUrlBean>> shareDownUrl(String token) {
        return mApiServeice.shareDownUrl(token);
    }

    public Observable<BaseBean<UploadInfo>> upload_avatar(Map<String, RequestBody> bodyMap, List<MultipartBody.Part> partList) {
        return mApiServeice.upload_avatar(bodyMap, partList);
    }

    public Observable<ResponseBody> downloadFile(String url) {
        return mApiServeice.downloadFile(url);
    }


}
