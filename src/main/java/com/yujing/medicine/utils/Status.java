package com.yujing.medicine.utils;

import java.io.Serializable;

/**
 * 状态枚举类
 */
public enum Status implements Serializable {
    ALL(0, "未启用"),
    ENABLED(1, "已启用"),
    DISABLED(2, "已禁用");

    private final int code;
    private final String description;

    Status(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    // 可以添加一个静态方法，根据code来获取对应的Status枚举
    public static Status getStatusByCode(int code) {
        for (Status status : Status.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + code + "]");
    }

//    public static void main(String[] args) {
//        Status currentStatus = Status.ENABLED;
//        System.out.println("当前状态: " + currentStatus.getDescription()); // 输出: 当前状态: 已启用
//
//// 根据code获取Status
//        Status statusByCode = Status.getStatusByCode(1);
//        System.out.println("通过code获取的状态: " + statusByCode.getDescription()); // 输出: 通过code获取的状态: 已启用
//    }
}
