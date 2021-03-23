package com.wd.winddots.enums;

/**
 * 货币
 *
 * @author zhou
 */
public enum CurrencyEnum {

    DEFAULT("-1", "CNY", "¥"),
    USD("1", "USD", "$"),
    EUR("2", "EUR", "€"),
    HKD("3", "HKD", "HK$"),
    CAD("4", "CAD", "C$"),
    JPY("6", "JPY", "J¥"),
    CNY("9", "CNY", "¥"),
    ;

    private String code;
    private String name;
    private String symbol;

    CurrencyEnum(String code, String name, String symbol) {
        this.code = code;
        this.name = name;
        this.symbol = symbol;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public static String getSymbol(String code) {
        CurrencyEnum[] currencyEnums = values();
        for (CurrencyEnum currencyEnum : currencyEnums) {
            if (currencyEnum.getCode().equals(code)) {
                return currencyEnum.getSymbol();
            }
        }
        return null;
    }

}
