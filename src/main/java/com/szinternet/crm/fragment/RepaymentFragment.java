package com.szinternet.crm.fragment;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szinternet.crm.CreditCardApplication;
import com.szinternet.crm.R;
import com.szinternet.crm.base.BaseMainFragment;
import com.szinternet.crm.component.AppComponent;
import com.szinternet.crm.component.DaggerMainComponent;
import com.szinternet.crm.databean.MyRepaymentCardBean;
import com.szinternet.crm.databean.SpendRecordBean;
import com.szinternet.crm.dialog.AlertDialog;
import com.szinternet.crm.fragment.contract.RepaymentCardContract;
import com.szinternet.crm.fragment.presenter.RepaymentCardPresenter;
import com.szinternet.crm.ui.add_repayment_plan.AddRepaymentPlanActivity;
import com.szinternet.crm.ui.bindcreditcard.BindCreditCardActivity;
import com.szinternet.crm.ui.bindidentity.BindIdentityActivity;
import com.szinternet.crm.ui.creditcard_manger.CreditCardMangerActivity;
import com.szinternet.crm.ui.repayment_plan_info.RepaymentPlanInfoActivity;
import com.szinternet.crm.ui.summary_repayment.SummaryRepaymentActivity;
import com.szinternet.crm.utils.CommonUtil;
import com.szinternet.crm.utils.EventHelper;

import java.util.List;

import butterknife.BindView;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class RepaymentFragment extends BaseMainFragment<RepaymentCardPresenter> implements RepaymentCardContract.View {

    @BindView(R.id.toolbar)
    LinearLayout mToolbar;
    @BindView(R.id.add_card)
    TextView mAddCard;
    @BindView(R.id.more_plan)
    TextView mMorePlan;
    @BindView(R.id.card_view)
    LinearLayout mCardView;
    @BindView(R.id.plan_view)
    LinearLayout mPlanView;
    @BindView(R.id.no_plan)
    TextView mNoPlan;
    @BindView(R.id.empty_card)
    TextView mEmptyCard;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_repayment;
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
    }

    @Override
    public void configViews() {
        setToolbar(mToolbar);
        initTitleBar(false, R.string.repayment);
        EventHelper.click(this, mMorePlan, mAddCard);
    }

    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.more_plan:
                /*new AlertDialog(getActivity()).builder().setTitle("提示")
                        .setMsg("您尚未添加计划,是否前往新增计划?\n")
                        .setPositiveButton("是", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //                                addPlanView(null)
                                //                                start2Activity(AddRepaymentPlanActivity.class);
                            }
                        }).setNegativeButton("否", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        start2Activity(SummaryRepaymentActivity.class);
                    }
                }).show();*/
                start2Activity(SummaryRepaymentActivity.class);
                break;
            case R.id.add_card:
                addCard();
                break;
            default:
                break;
        }
    }

    private void addPlanView(final SpendRecordBean.DataEntity dataEntity) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_my_plan, null);
        view.findViewById(R.id.repayment_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RepaymentPlanInfoActivity.class);
                intent.putExtra("id", dataEntity.ids);
                startActivity(intent);
            }
        });
        ((TextView) view.findViewById(R.id.tv_name)).setText(dataEntity.bank_name +
                dataEntity.nub.substring(dataEntity.nub.length() - 4, dataEntity.nub.length()));
        if (!TextUtils.isEmpty(dataEntity.type)) {
            switch (Integer.parseInt(dataEntity.type)) {
                case 0:
                    ((TextView) view.findViewById(R.id.tv_state)).setText("还款中");
                    ((TextView) view.findViewById(R.id.tv_state)).setSelected(true);
                    break;
                case 1:
                    ((TextView) view.findViewById(R.id.tv_state)).setText("已完成");
                    ((TextView) view.findViewById(R.id.tv_state)).setSelected(false);
                    break;
                case 2:
                    ((TextView) view.findViewById(R.id.tv_state)).setText("失败");
                    ((TextView) view.findViewById(R.id.tv_state)).setSelected(false);
                    break;
            }
        }
        ((TextView) view.findViewById(R.id.tv_time)).setText(String.format(getString(R.string.plan_time),
                CommonUtil.userNameReplaceWithStar(dataEntity.e_time)));
        ((TextView) view.findViewById(R.id.tv_money)).setText(String.format(getString(R.string.plan_money),
                dataEntity.pay_money));
        ((TextView) view.findViewById(R.id.tv_repayment_day)).setText(String.format(getString(R.string.plan_count),
                CommonUtil.userNameReplaceWithStar(dataEntity.pay_mub)));
        ((TextView) view.findViewById(R.id.tv_charge)).setText(String.format(getString(R.string.plan_server),
                CommonUtil.userNameReplaceWithStar(dataEntity.pay_fee)));
        mPlanView.addView(view, 0);
    }

    private void addCard() {
        if (CreditCardApplication.getsInstance().account != null && CreditCardApplication.getsInstance().account.real.equalsIgnoreCase("have")) {
            startActivityForResult(new Intent(getActivity(), BindCreditCardActivity.class), 100);
        } else {
            showBindDialog();
        }
    }

    private void showBindDialog() {
        new AlertDialog(getActivity()).builder().setTitle("提示")
                .setMsg("您尚未认证实名制,是否前往绑定实名?\n")
                .setPositiveButton("是", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        start2Activity(BindIdentityActivity.class);
                    }
                }).setNegativeButton("否", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();
    }

    private void addCardView(final MyRepaymentCardBean.DataEntity dataEntity) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_my_card, null);
        ((TextView) view.findViewById(R.id.tv_bank_name)).setText(dataEntity.bank_name +
                dataEntity.nub.substring(dataEntity.nub.length() - 4, dataEntity.nub.length()));
        ((TextView) view.findViewById(R.id.name)).setText(String.format(getString(R.string.card_admin),
                CommonUtil.userNameReplaceWithStar(dataEntity.user)));
        ((TextView) view.findViewById(R.id.icon_state)).setText(dataEntity.status == 0 ? "待还款" : "还款中");
        ((TextView) view.findViewById(R.id.icon_state)).setSelected(dataEntity.status == 0 ? false : true);
        ((TextView) view.findViewById(R.id.repayment_state)).setText(dataEntity.status == 0 ? "新增\n还款" : "正在\n还款");
        ((TextView) view.findViewById(R.id.repayment_state)).setSelected(dataEntity.status == 0 ? false : true);
        ((TextView) view.findViewById(R.id.tv_limit)).setText(String.format(getString(R.string.money_limit),
                CommonUtil.userNameReplaceWithStar(dataEntity.limit_money)));
        ((TextView) view.findViewById(R.id.tv_repayment_day)).setText(String.format(getString(R.string.repayment_day),
                CommonUtil.userNameReplaceWithStar(dataEntity.bill_day)));
        ((TextView) view.findViewById(R.id.tv_last_repayment)).setText(String.format(getString(R.string.last_repayment),
                CommonUtil.userNameReplaceWithStar(dataEntity.end_time)));
        mCardView.addView(view, 0);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreditCardMangerActivity.class);
                intent.putExtra("data", dataEntity);
                startActivityForResult(intent, 101);
            }
        });
        ((TextView) view.findViewById(R.id.repayment_state)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataEntity.status == 0) {//待还款
                    go2AddRepaymentPlan(dataEntity);
                } else {
                    Intent intent = new Intent(getActivity(), CreditCardMangerActivity.class);
                    intent.putExtra("data", dataEntity);
                    startActivityForResult(intent, 101);
                }
            }
        });
    }

    private void go2AddRepaymentPlan(MyRepaymentCardBean.DataEntity dataEntity) {
        Intent intent = new Intent(getActivity(), AddRepaymentPlanActivity.class);
        intent.putExtra("data", dataEntity);
        startActivity(intent);
    }

    @Override
    protected void lazyLoadData() {
        mPresenter.loadCardList();
        mPresenter.loadPlanRecord(2);
    }


    @Override
    public void onError() {
        mCardView.removeAllViews();
        addButton();
    }

    @Override
    public void onSuccess(List<MyRepaymentCardBean.DataEntity> list) {
        mCardView.removeAllViews();
        if (list != null && !list.isEmpty()) {
            mEmptyCard.setVisibility(View.GONE);
            for (int i = 0; i < list.size(); i++) {
                addCardView(list.get(i));
            }
        } else {
            mEmptyCard.setVisibility(View.VISIBLE);
        }
        addButton();
    }

    @Override
    public void onPlanSuccess(List<SpendRecordBean.DataEntity> list) {
        mPlanView.removeAllViews();
        if (list != null && !list.isEmpty()) {
            mNoPlan.setVisibility(View.GONE);
            for (int i = 0; i < list.size(); i++) {
                addPlanView(list.get(i));
            }
        } else {
            mNoPlan.setVisibility(View.VISIBLE);
        }
    }

    private void addButton() {
        TextView textView = new TextView(getContext());
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.toolbar));
        textView.setBackgroundResource(R.drawable.shape_add_bg);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(20, 30, 20, 0);
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(18);
        textView.setText("+添加信用卡");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCard();
            }
        });
        mCardView.addView(textView);
    }

    @Override
    public void start2Activity(Class c) {
        start2Activity(new Intent(getActivity(), c));
    }

    @Override
    public void showLoadDialog() {
        super.showDialog();
    }

    @Override
    public void showLoadDialog(String loadingText) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {//添加或者解绑信用卡
            mPresenter.loadCardList();
        }
    }

}
