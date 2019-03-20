package com.szinternet.crm.api;


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

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

import static com.szinternet.crm.Url.POST_ACCRATE;
import static com.szinternet.crm.Url.POST_ADDPAY;
import static com.szinternet.crm.Url.POST_AGENTS;
import static com.szinternet.crm.Url.POST_BANKLIST;
import static com.szinternet.crm.Url.POST_CASHLIST;
import static com.szinternet.crm.Url.POST_CASHPAY;
import static com.szinternet.crm.Url.POST_CASHSENT;
import static com.szinternet.crm.Url.POST_CHILDINFO;
import static com.szinternet.crm.Url.POST_CHILDRENS;
import static com.szinternet.crm.Url.POST_DEFRAYLIST;
import static com.szinternet.crm.Url.POST_DEFRAYLOG;
import static com.szinternet.crm.Url.POST_DEFRAYRCADD;
import static com.szinternet.crm.Url.POST_DELETERC;
import static com.szinternet.crm.Url.POST_DOWNURL;
import static com.szinternet.crm.Url.POST_FORGETPWD;
import static com.szinternet.crm.Url.POST_FRCASH;
import static com.szinternet.crm.Url.POST_FRCASHINDEX;
import static com.szinternet.crm.Url.POST_GAINLIST;
import static com.szinternet.crm.Url.POST_GAINREPAYLIST;
import static com.szinternet.crm.Url.POST_INCOME;
import static com.szinternet.crm.Url.POST_LHLIST;
import static com.szinternet.crm.Url.POST_LHLISTINDEX;
import static com.szinternet.crm.Url.POST_LOGIN;
import static com.szinternet.crm.Url.POST_LOGOUT;
import static com.szinternet.crm.Url.POST_MYAGENTS;
import static com.szinternet.crm.Url.POST_MYBANK;
import static com.szinternet.crm.Url.POST_MYCHILDREN;
import static com.szinternet.crm.Url.POST_MYGAIN;
import static com.szinternet.crm.Url.POST_MYGRADE;
import static com.szinternet.crm.Url.POST_MYINSURANCE;
import static com.szinternet.crm.Url.POST_PLANLISTS;
import static com.szinternet.crm.Url.POST_PLAN_LIST;
import static com.szinternet.crm.Url.POST_QRURL;
import static com.szinternet.crm.Url.POST_RCADD;
import static com.szinternet.crm.Url.POST_RCSENT;
import static com.szinternet.crm.Url.POST_REALNAME;
import static com.szinternet.crm.Url.POST_REALNAMESENT;
import static com.szinternet.crm.Url.POST_REGISTER;
import static com.szinternet.crm.Url.POST_REPAYLIST;
import static com.szinternet.crm.Url.POST_REPAYMENT_LIST;
import static com.szinternet.crm.Url.POST_SERVERCHARGE;
import static com.szinternet.crm.Url.POST_SPLIST;
import static com.szinternet.crm.Url.POST_SYSNEWS;
import static com.szinternet.crm.Url.POST_UPCLASS;
import static com.szinternet.crm.Url.POST_UPDATEPWD;
import static com.szinternet.crm.Url.POST_UPDATEPWDSENT;
import static com.szinternet.crm.Url.POST_UPDATERC;
import static com.szinternet.crm.Url.POST_UPLOADIMG;
import static com.szinternet.crm.Url.POST_VERIFY;
import static com.szinternet.crm.Url.POST_WEBPHONE;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 *
 * @des Observable网络请求参数接口
 */
public interface NetworkApiServeice {
    /**
     * 注册请求
     */
    @Multipart
    @POST(POST_REGISTER)
    Observable<BaseBean<RegisterResultBean>> register(@PartMap Map<String,
            RequestBody> map);

    /**
     * 登录请求
     */
    @Multipart
    @POST(POST_LOGIN)
    Observable<BaseBean<LoginResultBean>> login(@PartMap Map<String,
            RequestBody> map);

    /**
     * 检查新版本
     */
    @Multipart
    @POST(POST_LOGIN)
    Observable<BaseBean<VersionConfigBean>> checkVersion(@PartMap Map<String,
            RequestBody> map);

    /**
     * APK下载
     * 大文件下载需要加streaming
     *
     * @param fileUrl
     * @return
     */
    @Streaming
    @GET()
    Observable<ResponseBody> downloadFile(@Url String fileUrl);


    /**
     * 发送注册验证码
     * <p>
     * FormUrlEncoded用于修饰Field注解和FieldMap注解
     * 使用该注解,表示请求正文将使用表单网址编码。字段应该声明为参数，
     * 并用@Field注释或FieldMap注释。使用FormUrlEncoded注解的请求将
     * 具”application / x-www-form-urlencoded” MIME类型。
     * 字段名称和值将先进行UTF-8进行编码,再根据RFC-3986进行URI编码.
     */
    @FormUrlEncoded
    @POST(POST_VERIFY)
    Observable<BaseBean<BaseBean>> sendRegisterCode(@Field("username") String username);

    /**
     * 更改密码
     */
    @Multipart
    @POST(POST_UPDATEPWD)
    Observable<BaseBean<BaseBean>> updatePwd(@PartMap Map<String,
            RequestBody> map);

    /**
     * 忘记密码
     */
    @Multipart
    @POST(POST_FORGETPWD)
    Observable<BaseBean<BaseBean>> forgetPwd(@PartMap Map<String,
            RequestBody> map);

    /**
     * 发送忘记密码验证码
     */
    @FormUrlEncoded
    @POST(POST_UPDATEPWDSENT)
    Observable<BaseBean<BaseBean>> sendUpdatePwdCode(@Field("username") String username);

    /**
     * 退出登录
     */
    @FormUrlEncoded
    @POST(POST_LOGOUT)
    Observable<BaseBean<BaseBean>> logout(@Field("token") String token);

    /**
     * 银行卡绑定
     */
    @Multipart
    @POST(POST_REALNAME)
    Observable<BaseBean<BaseBean>> bindBankCode(@PartMap Map<String,
            RequestBody> map);

    /**
     * 银行卡绑定信息
     */
    @FormUrlEncoded
    @POST(POST_REALNAME)
    Observable<BaseBean<BindInfoBean>> bindInfo(@Field("token") String token);

    /**
     * 银行卡认证短信发送
     */
    @FormUrlEncoded
    @POST(POST_REALNAMESENT)
    Observable<BaseBean<BaseBean>> sendRealNameCode(@Field("token") String token, @Field("phone") String phone);

    /**
     * 银行卡列表消息
     */
    @FormUrlEncoded
    @POST(POST_BANKLIST)
    Observable<BaseBean<AllBankBean>> getAllBankList(@Field("token") String token);

    /**
     * 系统消息
     */
    @FormUrlEncoded
    @POST(POST_SYSNEWS)
    Observable<BaseBean<MessageBean>> sysMessage(@Field("token") String token);

    /**
     * 我的结算费率
     */
    @FormUrlEncoded
    @POST(POST_ACCRATE)
    Observable<BaseBean<MyRateBean>> myRate(@Field("token") String token);

    /**
     * 我的等级
     */
    @FormUrlEncoded
    @POST(POST_MYGRADE)
    Observable<BaseBean<MyGradeBean>> myGrade(@Field("token") String token);

    /**
     * 升级等级
     */
    @FormUrlEncoded
    @POST(POST_UPCLASS)
    Observable<BaseBean<UpclassBean>> upclass(@Field("token") String token, @Field("grade") String grade);

    /**
     * 我的银行卡管理
     */
    @FormUrlEncoded
    @POST(POST_MYBANK)
    Observable<BaseBean<MyCardBean>> myBank(@Field("token") String token);

    /**
     * 解绑信用卡
     */
    @FormUrlEncoded
    @POST(POST_DELETERC)
    Observable<BaseBean<BaseBean>> deleterc(@Field("token") String token, @Field("id") String id);

    /**
     * 修改信用卡信息
     */
    @Multipart
    @POST(POST_UPDATERC)
    Observable<BaseBean<BaseBean>> updaterc(@PartMap Map<String, RequestBody> map);

    /**
     * 还款计划信息
     */
    @FormUrlEncoded
    @POST(POST_PLANLISTS)
    Observable<BaseBean<PlanDetailInfoBean>> planDetailInfo(@Field("token") String token, @Field("id") String id);

    /**
     * 已还或未还期数详情
     */
    @FormUrlEncoded
    @POST(POST_REPAYLIST)
    Observable<BaseBean<RepaymentPeriodsBean>> planPeriodsList(@Field("token") String token, @Field("id") String id, @Field("type") String type);

    /**
     * 客服电话
     */
    @POST(POST_WEBPHONE)
    Observable<BaseBean<ServerPhoneBean>> webphone();

    /**
     * 我的收益
     */
    @FormUrlEncoded
    @POST(POST_MYGAIN)
    Observable<BaseBean<MyGainBean>> myGain(@Field("token") String token);

    /**
     * 分润利益提现data
     */
    @FormUrlEncoded
    @POST(POST_FRCASHINDEX)
    Observable<BaseBean<FrCashIndexBean>> frcashIndex(@Field("token") String token);

    /**
     * 分润利益提现
     */
    @FormUrlEncoded
    @POST(POST_FRCASH)
    Observable<BaseBean<BaseBean>> frcash(@Field("token") String token, @Field("money") String money);

    /**
     * 分润收益详情
     */
    @FormUrlEncoded
    @POST(POST_GAINLIST)
    Observable<BaseBean<CashRecordBean>> gainList(@Field("token") String token, @Field("offset") String pageIndex, @Field("limit") String limit);

    /**
     * 提现记录
     */
    @FormUrlEncoded
    @POST(POST_CASHLIST)
    Observable<BaseBean<CashRecordBean>> cashList(@Field("token") String token, @Field("type") String type, @Field("offset") String pageIndex, @Field("limit") String limit);

    /**
     * 推广收益详情
     */
    @FormUrlEncoded
    @POST(POST_SPLIST)
    Observable<BaseBean<CashRecordBean>> spList(@Field("token") String token, @Field("offset") String pageIndex, @Field("limit") String limit);

    /**
     * 还款详情
     */
    @FormUrlEncoded
    @POST(POST_GAINREPAYLIST)
    Observable<BaseBean<CashRecordBean>> promoteList(@Field("token") String token, @Field("offset") String pageIndex, @Field("limit") String limit);

    /**
     * 榜单列表三个第一名
     */
    @POST(POST_LHLISTINDEX)
    Observable<BaseBean<RankOneBean>> rankOne();

    /**
     * 榜单周月日列表
     */
    @FormUrlEncoded
    @POST(POST_LHLIST)
    Observable<BaseBean<RankBean>> rankList(@Field("type") String type);

    /**
     * 我的代理商
     */
    @FormUrlEncoded
    @POST(POST_MYAGENTS)
    Observable<BaseBean<MembersCountBean>> myagents(@Field("token") String token);


    /**
     * 我的下级
     */
    @FormUrlEncoded
    @POST(POST_MYCHILDREN)
    Observable<BaseBean<MembersCountBean>> mychildren(@Field("token") String token);

    /**
     * 角色等级列表
     */
    @FormUrlEncoded
    @POST(POST_AGENTS)
    Observable<BaseBean<MembersBean>> agentsList(@Field("token") String token, @Field("type") String type);

    /**
     * 掌柜列表
     */
    @FormUrlEncoded
    @POST(POST_CHILDRENS)
    Observable<BaseBean<MembersBean>> childrensList(@Field("token") String token, @Field("type") String type);

    /**
     * 角色或掌柜详情
     */
    @FormUrlEncoded
    @POST(POST_CHILDINFO)
    Observable<BaseBean<MemberDetailBean>> childInfo(@Field("token") String token, @Field("id") String id);


    /**
     * 还卡信用卡管理列表
     */
    @FormUrlEncoded
    @POST(POST_REPAYMENT_LIST)
    Observable<BaseBean<MyRepaymentCardBean>> repaymentList(@Field("token") String token);

    /**
     * 还卡计划列表
     */
    @FormUrlEncoded
    @POST(POST_PLAN_LIST)
    Observable<BaseBean<SpendRecordBean>> planList(@Field("token") String token, @Field("type") String type, @Field("offset") String pageIndex, @Field("limit") String limit);

    /**
     * 交易记录
     */
    @FormUrlEncoded
    @POST(POST_DEFRAYLOG)
    Observable<BaseBean<TradeLogBean>> defrayLog(@Field("token") String token);

    /**
     * 添加还款信用卡
     */
    @Multipart
    @POST(POST_RCADD)
    Observable<BaseBean<BaseBean>> addRepaymentCredit(@PartMap Map<String, RequestBody> map);

    /**
     * 提交还款申请
     */
    @Multipart
    @POST(POST_ADDPAY)
    Observable<BaseBean<BaseBean>> addPay(@PartMap Map<String, RequestBody> map);

    /**
     * 添加支付信用卡
     */
    @Multipart
    @POST(POST_DEFRAYRCADD)
    Observable<BaseBean<BaseBean>> addDefrayrcCredit(@PartMap Map<String, RequestBody> map);

    /**
     * 银行卡认证短信发送
     */
    @FormUrlEncoded
    @POST(POST_RCSENT)
    Observable<BaseBean<BaseBean>> sendRepaymentCreditCode(@Field("token") String token, @Field("phone") String phone);

    /**
     * 收款三方短信发送
     */
    @FormUrlEncoded
    @POST(POST_CASHSENT)
    Observable<BaseBean<CollectInfoBean>> sendCollectCode(@Field("token") String token, @Field("user_bank_id") String user_bank_id,
                                                          @Field("rc_bank_id") String rc_bank_id, @Field("money") String money);

    /**
     * 信用卡收款
     */
    @FormUrlEncoded
    @POST(POST_INCOME)
    Observable<BaseBean<CreditCollectInfoBean>> income(@Field("token") String token);

    /**
     * 我的费率
     */
    @FormUrlEncoded
    @POST(POST_SERVERCHARGE)
    Observable<BaseBean<MyRateBean>> servercharge(@Field("token") String token);

    /**
     * 已绑定的支付信用卡列表
     */
    @FormUrlEncoded
    @POST(POST_DEFRAYLIST)
    Observable<BaseBean<AllBankBean>> defrayList(@Field("token") String token);

    /**
     * 确认收款
     */
    @Multipart
    @POST(POST_CASHPAY)
    Observable<BaseBean<BaseBean>> cashPay(@PartMap Map<String, RequestBody> map);

    /**
     * 我的保险
     */
    @FormUrlEncoded
    @POST(POST_MYINSURANCE)
    Observable<BaseBean<InsuranceInfoBean>> myInsurance(@Field("token") String token);

    /**
     * 分享图片链接地址
     */
    @FormUrlEncoded
    @POST(POST_QRURL)
    Observable<BaseBean<ShareDownUrlBean>> shareImgUrl(@Field("token") String token);

    /**
     * 分享下载链接地址
     */
    @FormUrlEncoded
    @POST(POST_DOWNURL)
    Observable<BaseBean<ShareDownUrlBean>> shareDownUrl(@Field("token") String token);

    /**
     * 头像上传
     *
     * @param map
     * @param parts
     * @return
     */
    @Multipart
    @POST(POST_UPLOADIMG)
    Observable<BaseBean<UploadInfo>> upload_avatar(@PartMap Map<String,
            RequestBody> map, @Part List<MultipartBody.Part>
                                                           parts);

}
