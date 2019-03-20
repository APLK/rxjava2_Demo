package com.szinternet.crm.ui.show_repayment_plan;

import android.content.Context;

import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.api.HttpObserverInterface;
import com.szinternet.crm.api.HttpSubscriber;
import com.szinternet.crm.api.NetworkApi;
import com.szinternet.crm.api.RxSchedulers;
import com.szinternet.crm.base.BaseBean;
import com.szinternet.crm.base.RxPresenter;
import com.szinternet.crm.databean.MoneyInfoBean;
import com.szinternet.crm.databean.RepaymentPlanBean;
import com.szinternet.crm.utils.FormatUtils;
import com.szinternet.crm.utils.LogUtils;
import com.szinternet.crm.utils.RandomMoneyUtil;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

import static com.szinternet.crm.api.RxSchedulers.IO_TRANSFORMER;
import static com.szinternet.crm.utils.RandomMoneyUtil.geneDifRanom;
import static com.szinternet.crm.utils.RandomMoneyUtil.geneRanom;


/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class ShowPlanPresenter extends RxPresenter<ShowPlanContract.View> implements ShowPlanContract.Presenter<ShowPlanContract.View> {

    private Context mContext;
    private NetworkApi netApi;


    @Inject
    public ShowPlanPresenter(Context mContext, NetworkApi netApi) {
        this.netApi = netApi;
        this.mContext = mContext;
    }

    @Override
    public void addPay(String id, String s_time, String e_time, String pay_money, String pay_fee, String pay_list) {
        mView.showLoadDialog();
        Map<String, RequestBody> params = new HashMap<>();
        params.put("token", netApi.convertToRequestBody(CreditCardApplication.getsInstance().getToken()));
        params.put("id", netApi.convertToRequestBody(id));
        params.put("s_time", netApi.convertToRequestBody(s_time));
        params.put("e_time", netApi.convertToRequestBody(e_time));
        params.put("pay_money", netApi.convertToRequestBody(pay_money));
        params.put("pay_fee", netApi.convertToRequestBody(pay_fee));
        params.put("pay_list", netApi.convertToRequestBody(pay_list));
        netApi.addPay(params)
                .compose(RxSchedulers.<BaseBean<BaseBean>>applySchedulers(IO_TRANSFORMER))
                .subscribe(new
                        HttpSubscriber<BaseBean>(mView, mActivity, new HttpObserverInterface<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean<BaseBean> httpResult) {
                        mView.onSuccess();
                    }

                    @Override
                    public void onError(String msg, int code) {
                        mView.onError(msg);
                    }
                }));
    }

    /**
     * 创建还款计划
     */
    @Override
    public void createRepaymentPlan(final float repaymentMoney, final float serverchange, final int count, final String startTime, final String endTime) {
        mView.showLoadDialog("正在生成还款计划...");
        Observable.timer(2, TimeUnit.SECONDS).flatMap(new Function<Long, ObservableSource<List<Date>>>() {
            @Override
            public ObservableSource<List<Date>> apply(Long aLong) throws Exception {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dBegin = null;
                List<Date> listDate = new ArrayList<>();
                try {
                    dBegin = sdf.parse(startTime);
                    Date dEnd = sdf.parse(endTime);
                    listDate = FormatUtils.getDatesBetweenTwoDate(dBegin, dEnd);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return Observable.just(listDate);
            }

        }).flatMap(new Function<List<Date>, ObservableSource<List<RepaymentPlanBean.DataEntity>>>() {
            @Override
            public ObservableSource<List<RepaymentPlanBean.DataEntity>> apply(List<Date> listDate) throws Exception {
                LogUtils.i("1", "repaymentMoney=" + repaymentMoney + ",serverchange=" + serverchange);
                List<RepaymentPlanBean.DataEntity> list = new ArrayList<>();
                MoneyInfoBean moneyInfoBean = RandomMoneyUtil.spiltIntRedPackets(repaymentMoney, serverchange, count);
                if (moneyInfoBean == null) {
                    return Observable.just(list);
                }
                List<Integer> repaymentList = moneyInfoBean.getRepaymentList();
                List<Float> spendList = moneyInfoBean.getSpendlist();
                int sum = 0;
                StringBuilder s = new StringBuilder();
                StringBuilder s1 = new StringBuilder();
                for (int i = 0; i < repaymentList.size(); i++) {
                    sum += repaymentList.get(i);
                    s.append(repaymentList.get(i)).append(",");
                }
                BigDecimal bigDecimal = new BigDecimal("0");
                for (int i = 0; i < spendList.size(); i++) {
                    bigDecimal = bigDecimal.add(new BigDecimal(spendList.get(i) + ""));
                    s.append(spendList.get(i)).append(",");
                }
                LogUtils.i("1", "repaymentMoney1=" + sum + ",bigDecimal=" + bigDecimal + ",sise+" + repaymentList.size() + ",size2=" + spendList.size());
                LogUtils.i("1", "repaymentMoney2=" + s + ",bigDecimal=" + s1);

                if (repaymentList == null || repaymentList.size() != count) {
                    return Observable.just(list);
                }

                if (spendList == null || spendList.size() != count * 2) {
                    return Observable.just(list);
                }
                int indexSpend = 1;
                int indexRepayment = 1;
                LogUtils.i("1", "listDate=" + FormatUtils.date2String(listDate.get(listDate.size() - 1), "yyyy-MM-dd HH:mm:ss"));
                LogUtils.i("1", "listDate1=" + listDate.size());
                if (listDate.size() < repaymentList.size()) {//还款时间天数小于还款笔数
                    //剩下的未还笔数需要依次添加到还款天数中
                    int count = repaymentList.size() - listDate.size();
                    for (int i = 0; i < listDate.size(); i++) {
                        //消费
                        list.add(new RepaymentPlanBean.DataEntity(2, indexSpend, spendList.get(indexSpend - 1) + "",
                                FormatUtils.date2String(geneRanom(listDate.get(i)), "yyyy-MM-dd HH:mm:ss")));
                        indexSpend++;
                        list.add(new RepaymentPlanBean.DataEntity(2, indexSpend, spendList.get(indexSpend - 1) + "",
                                FormatUtils.date2String(geneRanom(listDate.get(i)), "yyyy-MM-dd HH:mm:ss")));
                        indexSpend++;
                        list.add(new RepaymentPlanBean.DataEntity(1, indexRepayment, repaymentList.get(indexRepayment - 1) + "",
                                FormatUtils.date2String(geneRanom(listDate.get(i)), "yyyy-MM-dd HH:mm:ss")));
                        indexRepayment++;
                        //生成的时间不能有重复的
                        if (count > 0) {
                            listDate.get(i).setHours(10);
                            listDate.get(i).setMinutes(5);
                            listDate.get(i).setSeconds(46);
                            list.add(new RepaymentPlanBean.DataEntity(2, indexSpend, spendList.get(indexSpend - 1) + "",
                                    FormatUtils.date2String(geneDifRanom(listDate.get(i),
                                            FormatUtils.string2Date(list.get(indexSpend - 2).create_time, "yyyy-MM-dd HH:mm:ss").getTime(),
                                            FormatUtils.string2Date(list.get(indexSpend - 3).create_time, "yyyy-MM-dd HH:mm:ss").getTime()), "yyyy-MM-dd HH:mm:ss")));
                            indexSpend++;
                            list.add(new RepaymentPlanBean.DataEntity(2, indexSpend, spendList.get(indexSpend - 1) + "",
                                    FormatUtils.date2String(geneDifRanom(listDate.get(i),
                                            FormatUtils.string2Date(list.get(indexSpend - 2).create_time, "yyyy-MM-dd HH:mm:ss").getTime(),
                                            FormatUtils.string2Date(list.get(indexSpend - 3).create_time, "yyyy-MM-dd HH:mm:ss").getTime()), "yyyy-MM-dd HH:mm:ss")));
                            indexSpend++;
                            list.add(new RepaymentPlanBean.DataEntity(1, indexRepayment, repaymentList.get(indexRepayment - 1) + "",
                                    FormatUtils.date2String(geneDifRanom(listDate.get(i),
                                            FormatUtils.string2Date(list.get(indexRepayment - 2).create_time, "yyyy-MM-dd HH:mm:ss").getTime(), 0), "yyyy-MM-dd HH:mm:ss")));
                            indexRepayment++;
                            count--;
                        }
                    }
                } else {
                    List<Date> randomDateList = RandomMoneyUtil.createRandomList(listDate, repaymentList.size());
                    for (int i = 0; i < randomDateList.size(); i++) {
                        LogUtils.i("1", "randomDateList00=" + FormatUtils.date2String(randomDateList.get(i), "yyyy-MM-dd HH:mm:ss"));
                    }
                    Collections.sort(randomDateList, new Comparator<Date>() {
                        public int compare(Date arg0, Date arg1) {
                            return arg0.compareTo(arg1);
                        }
                    });
                    for (int i = 0; i < repaymentList.size(); i++) {
                        //消费
                        list.add(new RepaymentPlanBean.DataEntity(2, indexSpend, spendList.get(indexSpend - 1) + "",
                                FormatUtils.date2String(geneRanom(randomDateList.get(i)), "yyyy-MM-dd HH:mm:ss")));
                        indexSpend++;
                        list.add(new RepaymentPlanBean.DataEntity(2, indexSpend, spendList.get(indexSpend - 1) + "",
                                FormatUtils.date2String(geneRanom(randomDateList.get(i)), "yyyy-MM-dd HH:mm:ss")));
                        indexSpend++;
                        //还款
                        list.add(new RepaymentPlanBean.DataEntity(1, i + 1, repaymentList.get(i) + "",
                                FormatUtils.date2String(geneRanom(randomDateList.get(i)), "yyyy-MM-dd HH:mm:ss")));
                    }
                }
                return Observable.just(list);
            }

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<RepaymentPlanBean.DataEntity>>() {

            @Override
            public void accept(List<RepaymentPlanBean.DataEntity> dataEntities) throws Exception {
                mView.onPlanSuccess(dataEntities);
            }

        });


    }
}
