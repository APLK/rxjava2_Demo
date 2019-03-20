package com.szinternet.crm.component;

import com.szinternet.crm.fragment.ProfitRecordFragment;
import com.szinternet.crm.ui.billingrecord.BillingRecordActivity;
import com.szinternet.crm.ui.card_manger.CardMangerActivity;
import com.szinternet.crm.ui.cash_record.CashRecordActivity;
import com.szinternet.crm.ui.divide_profits_record.DivideProfitsRecordActivity;
import com.szinternet.crm.ui.msgdetail.MsgDetailActivity;
import com.szinternet.crm.ui.spend_record_list.SpendRecordListActivity;
import com.szinternet.crm.ui.trade_log.TradeRecordActivity;
import com.szinternet.crm.ui.xitongnews.XitongNewsActivity;

import dagger.Component;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
@PerActivityScoped
@Component(dependencies = AppComponent.class)
public interface BillingRecordComponent {
    void inject(BillingRecordActivity activity);

    void inject(XitongNewsActivity activity);

    void inject(MsgDetailActivity activity);

    void inject(SpendRecordListActivity activity);

    void inject(CashRecordActivity activity);

    void inject(DivideProfitsRecordActivity activity);

    void inject(CardMangerActivity fragment);

    void inject(ProfitRecordFragment fragment);

    void inject(TradeRecordActivity fragment);
}