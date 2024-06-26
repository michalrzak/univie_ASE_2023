package com.ase.notification.business.notificationsendtime;

public enum ENotificationConstants {
    DAY_SECONDS(60 * 60 * 24);

    private final long value;

    ENotificationConstants(long value) {
        this.value = value;
    }

    public long get() {
        return value;
    }
}
