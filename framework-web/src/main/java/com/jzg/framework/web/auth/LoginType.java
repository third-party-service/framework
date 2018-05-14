package com.jzg.framework.web.auth;

/**
 * @description: 登陆方式枚举
 * @author: JZG
 * @date: 2016/12/1 18:07
 */
public enum LoginType {
    PhoneLogin(1), NameLogin(2);

    private final int value;

    private LoginType(int value) {
        this.value = value;
    }

    /**
     * Get the integer value of this enum value
     */
    public int getValue() {
        return value;
    }

    /**
     * Find a the enum type by its integer value
     *
     * @return null if the value is not found.
     */
    public static LoginType findByValue(int value) {
        switch (value) {
            case 1:
                return PhoneLogin;
            case 2:
                return NameLogin;
            default:
                return null;
        }
    }
}
