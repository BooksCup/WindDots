package com.wd.winddots.utils;

import com.wd.winddots.entity.GoodsSpec;
import com.wd.winddots.entity.StockApplicationInRecord;
import com.wd.winddots.entity.StockApplicationOutRecord;

import java.util.ArrayList;
import java.util.List;

public class StockUtil {
    public static List<GoodsSpec> getGoodsSpecListFromStockRecordList(List<StockApplicationInRecord> stockApplicationInRecordList) {
        List<GoodsSpec> goodsSpecList = new ArrayList<>();
        if (null != stockApplicationInRecordList) {
            for (StockApplicationInRecord stockApplicationInRecord : stockApplicationInRecordList) {
                GoodsSpec goodsSpec = new GoodsSpec();
                goodsSpec.setId(stockApplicationInRecord.getGoodsSpecId());
                goodsSpec.setX(stockApplicationInRecord.getX());
                goodsSpec.setY(stockApplicationInRecord.getY());
                goodsSpec.setApplyNum(stockApplicationInRecord.getApplyNumber());
                goodsSpec.setNum(stockApplicationInRecord.getCount());
                goodsSpecList.add(goodsSpec);
            }
        }
        return goodsSpecList;
    }

    public static List<GoodsSpec> getGoodsSpecListFromStockOutRecordList(List<StockApplicationOutRecord> stockApplicationOutRecordList) {
        List<GoodsSpec> goodsSpecList = new ArrayList<>();
        if (null != stockApplicationOutRecordList) {
            for (StockApplicationOutRecord stockApplicationOutRecord : stockApplicationOutRecordList) {
                GoodsSpec goodsSpec = new GoodsSpec();
                goodsSpec.setId(stockApplicationOutRecord.getGoodsSpecId());
                goodsSpec.setX(stockApplicationOutRecord.getX());
                goodsSpec.setY(stockApplicationOutRecord.getY());
                goodsSpec.setApplyNum(stockApplicationOutRecord.getApplyNumber());
                goodsSpec.setNum(stockApplicationOutRecord.getCount());
                goodsSpecList.add(goodsSpec);
            }
        }
        return goodsSpecList;
    }
}
