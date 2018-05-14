package com.jzg.framework.core.vo;

/**
 * 返回状态枚举
 * Ok:正常返回
 * Exception：内部异常，并返回异常信息
 * Failure：调用失败，返回失败信息
 * InValid：非法调用，sign验证失败
 * NoLogin：未登录
 * NoAuth：无权限
 */
public enum RetStatus {
    Ok(200), Failure(202), InValid(204), Exception(500), NoLogin(700), NoAuth(701);;

    private final int value;

    private RetStatus(int value) {
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
    public static RetStatus findByValue(int value) {
        switch (value) {
            case 200:
                return Ok;
            case 202:
                return Failure;
            case 204:
                return InValid;
            case 500:
                return Exception;
            case 700:
                return NoLogin;
            case 701:
                return NoAuth;
            default:
                return null;
        }
    }
}