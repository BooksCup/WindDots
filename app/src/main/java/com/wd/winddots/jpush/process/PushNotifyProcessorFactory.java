package com.wd.winddots.jpush.process;

import com.wd.winddots.enums.PushServiceTypeEnum;

public class PushNotifyProcessorFactory {

    public static PushNotifyProcessor createProcessor(String serviceType) {
        PushNotifyProcessor pushNotifyProcessor = null;
        if (PushServiceTypeEnum.ADD_STOCK_IN_APPLY_OFFICE_SUPPLIES.getCode().equals(serviceType)) {
            pushNotifyProcessor = new AddOfficeSuppliesStockInProcessor();
        }
        return pushNotifyProcessor;
    }
}
