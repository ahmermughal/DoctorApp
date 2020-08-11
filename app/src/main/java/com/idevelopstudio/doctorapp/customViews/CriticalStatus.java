package com.idevelopstudio.doctorapp.customViews;

public enum CriticalStatus {
    LOW("1"),
    MODERATE("2"),
    HIGH("3");
    private String value;

    CriticalStatus(String s) {
        this.value = s;
    }

    public String getValue() {
        return value;
    }
}
