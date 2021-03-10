package com.wd.winddots.utils;

import com.wd.winddots.entity.GoodsSpec;
import com.wd.winddots.entity.StockApplicationInRecord;

import java.util.ArrayList;
import java.util.List;

public class StockUtil {
    public static List<GoodsSpec> getGoodsSpecListFromStockRecordList(List<StockApplicationInRecord> stockApplicationInRecordList) {
        List<GoodsSpec> goodsSpecList = new ArrayList<>();
        for (StockApplicationInRecord stockApplicationInRecord : stockApplicationInRecordList) {
            GoodsSpec goodsSpec = new GoodsSpec();
            goodsSpec.setX(stockApplicationInRecord.getX());
            goodsSpec.setY(stockApplicationInRecord.getY());
            goodsSpec.setApplyNum(stockApplicationInRecord.getApplyNumber());
            goodsSpecList.add(goodsSpec);
        }
        return goodsSpecList;
    }
}
