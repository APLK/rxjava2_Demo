package com.szinternet.crm.fragment;

/**
 * * 免责声明:本项目不可用于违法用途,否则后果自负,技术无罪
 */
public class FragmentFactory {

    private volatile FragmentFactory mInstance;

    public FragmentFactory() {
    }

    public FragmentFactory getInstance() {
        if (mInstance == null) {
            synchronized (FragmentFactory.class) {
                if (mInstance == null) {
                    mInstance = new FragmentFactory();
                }
            }
        }
        return mInstance;
    }

    private volatile MainFragment mMainFragment;

    public MainFragment getMainFragment() {
        if (mMainFragment == null) {
            synchronized (FragmentFactory.class) {
                if (mMainFragment == null) {
                    mMainFragment = new MainFragment();
                }
            }
        }
        return mMainFragment;
    }

    private volatile ShareFragment mShareFragment;

    public ShareFragment getShareFragment() {
        if (mShareFragment == null) {
            synchronized (FragmentFactory.class) {
                if (mShareFragment == null) {
                    mShareFragment = new ShareFragment();
                }
            }
        }
        return mShareFragment;
    }

    private volatile MineFragment mMineFragment;

    public MineFragment getMineFragment() {
        if (mMineFragment == null) {
            synchronized (FragmentFactory.class) {
                if (mMineFragment == null) {
                    mMineFragment = new MineFragment();
                }
            }
        }
        return mMineFragment;
    }

    private volatile ProfitFragment mProfitFragment;

    public ProfitFragment getProfitFragment() {
        if (mProfitFragment == null) {
            synchronized (FragmentFactory.class) {
                if (mProfitFragment == null) {
                    mProfitFragment = new ProfitFragment();
                }
            }
        }
        return mProfitFragment;
    }

    private volatile RepaymentFragment mRepaymentFragment;

    public RepaymentFragment getRepaymentFragment() {
        if (mRepaymentFragment == null) {
            synchronized (FragmentFactory.class) {
                if (mRepaymentFragment == null) {
                    mRepaymentFragment = new RepaymentFragment();
                }
            }
        }
        return mRepaymentFragment;
    }

    private volatile ProxyFragment mProxyFragment;

    public ProxyFragment getProxyFragment() {
        if (mProxyFragment == null) {
            synchronized (FragmentFactory.class) {
                if (mProxyFragment == null) {
                    mProxyFragment = new ProxyFragment();
                }
            }
        }
        return mProxyFragment;
    }

    private volatile SubordinateFragment mSubordinateFragment;

    public SubordinateFragment getSubordinateFragment() {
        if (mSubordinateFragment == null) {
            synchronized (FragmentFactory.class) {
                if (mSubordinateFragment == null) {
                    mSubordinateFragment = new SubordinateFragment();
                }
            }
        }
        return mSubordinateFragment;
    }

    private volatile WeeklyRankingFragment mWeeklyRankingFragment;

    public WeeklyRankingFragment getWeeklyRankingFragment() {
        if (mWeeklyRankingFragment == null) {
            synchronized (FragmentFactory.class) {
                if (mWeeklyRankingFragment == null) {
                    mWeeklyRankingFragment = new WeeklyRankingFragment();
                }
            }
        }
        return mWeeklyRankingFragment;
    }

    private volatile DayRankingFragment mDayRankingFragment;

    public DayRankingFragment getDayRankingFragment() {
        if (mDayRankingFragment == null) {
            synchronized (FragmentFactory.class) {
                if (mDayRankingFragment == null) {
                    mDayRankingFragment = new DayRankingFragment();
                }
            }
        }
        return mDayRankingFragment;
    }

    private volatile AllRankingFragment mAllRankingFragment;

    public AllRankingFragment getAllRankingFragment() {
        if (mAllRankingFragment == null) {
            synchronized (FragmentFactory.class) {
                if (mAllRankingFragment == null) {
                    mAllRankingFragment = new AllRankingFragment();
                }
            }
        }
        return mAllRankingFragment;
    }

    /*private SummaryRepaymentFragment mSummaryRepaymentFragment;
    public SummaryRepaymentFragment getSummaryRepaymentFragment() {
        if (mSummaryRepaymentFragment == null) {
            synchronized (FragmentFactory.class) {
                if (mSummaryRepaymentFragment == null) {
                    mSummaryRepaymentFragment = new SummaryRepaymentFragment();
                }
            }
        }
        return mSummaryRepaymentFragment;
    }*/
}
