package com.szinternet.crm.utils;

import com.szinternet.crm.databean.MoneyInfoBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomMoneyUtil {
    //还款最小值
    private static float MINVALUE = 0.00f;
    //还款最大值
    private static float MAXVALUE = 0.00f;
    //还款比例因子
    private static float scale = 0.008f;
    //消费最小值
    private static float SPENDMINVALUE = 0.00f;
    //消费最大值
    private static float SPENDMAXVALUE = 0.00f;
    //消费比例因子
    private static float spendScale = 0.06f;

    /**
     * 判断还款是否合情理
     *
     * @param money
     * @param count
     * @return
     */
    public static boolean isRight(float money, int count) {
        float avg = money / count;
        if (avg < MINVALUE) {
            return false;
        } else if (avg > MAXVALUE) {
            return false;
        }
        return true;
    }

    /**
     * 判断消费是否合情理
     *
     * @param money
     * @param count
     * @return
     */
    public static boolean isSpendRight(float money, int count) {
        float avg = money / count;
        if (avg < SPENDMINVALUE) {
            return false;
        } else if (avg > SPENDMAXVALUE) {
            return false;
        }
        return true;
    }

    /**
     * 还款核心算法
     *
     * @param money
     * @param minS
     * @param maxS
     * @param count
     * @return
     */
    public static float randomRedPacket(float money, float minS, float maxS, int count) {
        //当人数剩余一个时，把当前剩余全部返回
        if (count == 1) {
            return money;
        }
        //如果当前最小还款等于最大红包，之间返回当前红包
        if (minS == maxS) {
            return minS;
        }
        float max = maxS > money ? money : maxS;
        //随机产生一个红包
        float one = (float) (Math.random() * (max - minS) + minS);
        float balance = money - one;
        //判断此次分配后，后续是否合理
        if (isRight(balance, count - 1)) {
            return one;
        } else {
            //重新分配
            float avg = balance / (count - 1);
            //如果本次还款过大，导致下次不够分，走这一条
            if (avg < MINVALUE) {
                return randomRedPacket(money, minS, one, count);
            } else {
                return randomRedPacket(money, one, maxS, count);
            }
        }
    }

    /**
     * 消费核心算法
     *
     * @param money
     * @param minS
     * @param maxS
     * @param count
     * @return
     */
    public static float randomSpendPacket(float money, float minS, float maxS, int count) {
        //当人数剩余一个时，把当前剩余全部返回
        if (count == 1) {
            return money;
        }
        //如果当前最小消费等于最大消费，之间返回当前消费
        if (minS == maxS) {
            return minS;
        }
        float max = maxS > money ? money : maxS;
        //随机产生一个消费
        float one = (float) (Math.random() * (max - minS) + minS);
        float balance = money - one;
        //判断此次分配后，后续是否合理
        if (isSpendRight(balance, count - 1)) {
            return one;
        } else {
            //重新分配
            float avg = balance / (count - 1);
            //如果本次消费过大，导致下次不够分，走这一条
            if (avg < SPENDMINVALUE) {
                return randomSpendPacket(money, minS, one, count);
            } else {
                return randomSpendPacket(money, one, maxS, count);
            }
        }
    }

    /**
     * 消费小数点
     *
     * @param money
     * @param count @return
     */
    public static List<Float> spiltFloatRedPackets(float money, int count) {
        SPENDMAXVALUE = (float) (money / count + money * spendScale);
        SPENDMINVALUE = (float) (money / count - money * spendScale);
        if (SPENDMAXVALUE <= 0 || SPENDMINVALUE <= 0) {
            return null;
        }
        //首先判断消费是否合情理
        if (!isSpendRight(money, count)) {
            return null;
        }
        List<Float> list = new ArrayList<Float>();
        float max = SPENDMAXVALUE;
        for (int i = 0; i < count; i++) {
            float value = randomSpendPacket(money, SPENDMINVALUE, max, count - i);
            BigDecimal b2 = new BigDecimal(value);
            value = b2.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
            list.add(value);
            money -= value;
        }
        return list;
    }

    /**
     * 还款整数
     *
     * @param money
     * @param count
     * @return
     */
    public static MoneyInfoBean spiltIntRedPackets(float money, float serverchange, int count) {
        MAXVALUE = (float) (money / count + money * scale);
        MINVALUE = (float) (money / count - money * scale);
        //平均每笔的手续费用
        float avrServer = serverchange / (count * 2);
        BigDecimal b1 = new BigDecimal(avrServer);
        avrServer = b1.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        List<Integer> list = new ArrayList<Integer>();
        List<Float> floatList = new ArrayList<Float>();
        if (MAXVALUE <= 0 || MINVALUE <= 0) {
            return null;
        }
        //首先判断还款是否合情理
        if (!isRight(money, count)) {
            return null;
        }
        for (int i = 0; i < count; i++) {
            int value = (int) randomRedPacket(money, MINVALUE, MAXVALUE, count - i);
            list.add(value);
            List<Float> twoList = spiltFloatRedPackets(value, 2);
            for (int j = 0; j < twoList.size(); j++) {
                float value1 = avrServer;
                if (i == count - 1 && j == twoList.size() - 1) {
                    value1 = new BigDecimal(serverchange)
                            .subtract(new BigDecimal(value1 * 21)).floatValue();
                }
                value1 = new BigDecimal(twoList.get(j))
                        .add(new BigDecimal(value1)).floatValue();
                BigDecimal b2 = new BigDecimal(value1);
                value1 = b2.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                floatList.add(value1);
            }
            money -= value;
        }
        return new MoneyInfoBean(list, floatList);
    }

    public static Date geneRanom(Date date) {
        Date date1 = new Date();
        //随机产生一个大于该时间在4小时内取值的时间
        date1.setTime(date.getTime() + (new Random().nextInt(1000 * 3600 * 4) + 5 * 1000 * 60));
        LogUtils.i("1", "time=" + FormatUtils.date2String(date, "yyyy-MM-dd HH:mm:ss") + ",date1=" +
                FormatUtils.date2String(date1, "yyyy-MM-dd HH:mm:ss"));
        if (date1.after(date)) {
            date.setHours(date1.getHours());
            date.setMinutes(date1.getMinutes());
            date.setSeconds(date1.getSeconds());
            return date1;
        } else {
            return geneRanom(date);
        }
    }

    public static Date geneDifRanom(Date date, long time1, long time2) {
        Date date1 = geneRanom(date);
        if (date1.getTime() == time1 || date1.getTime() == time2) {
            return geneDifRanom(date, time1, time2);
        }
        date.setHours(date1.getHours());
        date.setMinutes(date1.getMinutes());
        date.setSeconds(date1.getSeconds());
        return date1;
    }

    /**
     * 从list中随机抽取元素
     *
     * @param list
     * @return void
     * @throws
     * @Title: createRandomList
     */
    public static List createRandomList(List list, int n) {
        Map map = new HashMap();
        List listNew = new ArrayList();
        if (list.size() <= n) {
            return list;
        } else {
            while (map.size() < n) {
                int random = (int) (Math.random() * list.size());
                if (!map.containsKey(random)) {
                    map.put(random, "");
                    listNew.add(list.get(random));
                }
            }
            return listNew;
        }
    }

    public static void main(String[] args) {
        //        RandomMoneyUtil dd = new RandomMoneyUtil();
        //        List<Float> list = dd.spiltFloatRedPackets(12.00f, 11.00f, 22);
        //        List<Integer> list2 = dd.spiltIntRedPackets(12.00f, 11.00f, 11);
        //        int sum = 0;
        //        for (int j = 0; j < list2.size(); j++) {
        //            sum += list2.get(j);
        //        }
        //        System.out.println(list2);
        //        System.out.println(sum);
        //        System.out.println(list);
        //
        //        BigDecimal bigDecimal = new BigDecimal("0");
        //        for (int i = 0; i < list.size(); i++) {
        //            bigDecimal = bigDecimal.add(new BigDecimal(list.get(i) + ""));
        //        }
        //        System.out.println(bigDecimal.floatValue());
    }

}
