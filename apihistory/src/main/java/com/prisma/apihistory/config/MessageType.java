package com.prisma.apihistory.config;

public enum MessageType {
    NORMAL_ALERT,
    HIGH_ALERT,
    LOW_ALERT;

    public static String getType(String value) {
        return MessageType.valueOf(value).name();
    }
}
