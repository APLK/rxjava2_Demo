package com.szinternet.crm.component;


import com.szinternet.crm.fragment.AllRankingFragment;
import com.szinternet.crm.fragment.DayRankingFragment;
import com.szinternet.crm.fragment.MainFragment;
import com.szinternet.crm.fragment.MineFragment;
import com.szinternet.crm.fragment.ProfitFragment;
import com.szinternet.crm.fragment.ProxyFragment;
import com.szinternet.crm.fragment.RepaymentFragment;
import com.szinternet.crm.fragment.ShareFragment;
import com.szinternet.crm.fragment.SubordinateFragment;
import com.szinternet.crm.fragment.SummaryRepaymentFragment;
import com.szinternet.crm.fragment.WeeklyRankingFragment;
import com.szinternet.crm.ui.about.AboutActivity;
import com.szinternet.crm.ui.add_repayment_plan.AddRepaymentPlanActivity;
import com.szinternet.crm.ui.add_weixin.AddWeiXinActivity;
import com.szinternet.crm.ui.bankcard.BankCardActivity;
import com.szinternet.crm.ui.bindbankcode.BindBankCodeActivity;
import com.szinternet.crm.ui.bindcreditcard.BindCreditCardActivity;
import com.szinternet.crm.ui.bindidentity.BindIdentityActivity;
import com.szinternet.crm.ui.cash.CashActivity;
import com.szinternet.crm.ui.code.CodeGenerateActivity;
import com.szinternet.crm.ui.creditcard_collect.CreditCardCollectActivity;
import com.szinternet.crm.ui.creditcard_manger.CreditCardMangerActivity;
import com.szinternet.crm.ui.grade.MyGradeActivity;
import com.szinternet.crm.ui.grade_update.GradeUpdateActivity;
import com.szinternet.crm.ui.guide.NoviceGuideActivity;
import com.szinternet.crm.ui.insurance.InsuranceActivity;
import com.szinternet.crm.ui.main.MainActivity;
import com.szinternet.crm.ui.member_detail.MemberDetailActivity;
import com.szinternet.crm.ui.members.MembersActivity;
import com.szinternet.crm.ui.messagecenter.MsgCenterActivity;
import com.szinternet.crm.ui.myinfo.MyinfoActivity;
import com.szinternet.crm.ui.operationsguide.OperationGuideActivity;
import com.szinternet.crm.ui.pay.PayActivity;
import com.szinternet.crm.ui.quick_payment.QuickPaymentActivity;
import com.szinternet.crm.ui.rank.RankActivity;
import com.szinternet.crm.ui.rate.RateActivity;
import com.szinternet.crm.ui.repayment_plan_info.RepaymentPlanInfoActivity;
import com.szinternet.crm.ui.repayment_record.RepaymentRecordActivity;
import com.szinternet.crm.ui.setting.SettingActivity;
import com.szinternet.crm.ui.show_repayment_plan.ShowRepaymentPlanActivity;
import com.szinternet.crm.ui.summary_repayment.SummaryRepaymentActivity;
import com.szinternet.crm.ui.updatepwd.UpdatePwdActivity;
import com.szinternet.crm.ui.webview.WebviewActivity;

import dagger.Component;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 * 注解依赖
 */
@PerActivityScoped
@Component(dependencies = AppComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);

    void inject(MainFragment fragment);

    void inject(RepaymentFragment fragment);

    void inject(ProfitFragment fragment);

    void inject(MineFragment fragment);

    void inject(ShareFragment fragment);

    void inject(ProxyFragment fragment);

    void inject(SubordinateFragment fragment);

    void inject(CodeGenerateActivity activity);

    void inject(InsuranceActivity activity);

    void inject(UpdatePwdActivity activity);

    void inject(AboutActivity activity);

    void inject(SettingActivity activity);

    void inject(MyinfoActivity activity);

    void inject(BindIdentityActivity activity);

    void inject(BindBankCodeActivity activity);

    void inject(RateActivity activity);

    void inject(MyGradeActivity activity);

    void inject(NoviceGuideActivity activity);

    void inject(OperationGuideActivity activity);

    void inject(MsgCenterActivity activity);

    void inject(RankActivity activity);

    void inject(WeeklyRankingFragment fragment);

    void inject(DayRankingFragment fragment);

    void inject(AllRankingFragment fragment);

    void inject(MembersActivity activity);

    void inject(SummaryRepaymentFragment fragment);

    void inject(BindCreditCardActivity activity);

    void inject(CreditCardCollectActivity activity);

    void inject(CreditCardMangerActivity activity);

    void inject(RepaymentRecordActivity activity);

    void inject(RepaymentPlanInfoActivity activity);

    void inject(GradeUpdateActivity activity);

    void inject(PayActivity activity);

    void inject(BankCardActivity activity);

    void inject(CashActivity activity);

    void inject(SummaryRepaymentActivity activity);

    void inject(AddWeiXinActivity activity);

    void inject(WebviewActivity activity);

    void inject(AddRepaymentPlanActivity activity);

    void inject(ShowRepaymentPlanActivity activity);

    void inject(MemberDetailActivity activity);

    void inject(QuickPaymentActivity activity);
}