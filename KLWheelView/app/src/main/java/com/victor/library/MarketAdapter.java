package com.victor.library;



import com.victor.library.wheelview.IWheelviewAdapter;

import java.util.List;


/**
 * Created by Victor on 2017/8/7.
 */

public class MarketAdapter implements IWheelviewAdapter {

    private List<MarketBean> mList;


    public MarketAdapter(List<MarketBean> list) {
        mList = list;
    }

    @Override
    public String getItemeTitle(int i) {
        if (mList != null) {
            return mList.get(i).getName();
        } else {
            return "";
        }
    }

    @Override
    public int getCount() {
        if (mList != null) {
            return mList.size();
        } else {
            return 0;
        }

    }

    @Override
    public MarketBean get(int index) {
        if (mList != null) {
            return mList.get(index);
        } else {
            return null;
        }
    }

    public static class MarketBean {
        private int price;
        private String name;
        private int number;

        public MarketBean(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

    }

}
