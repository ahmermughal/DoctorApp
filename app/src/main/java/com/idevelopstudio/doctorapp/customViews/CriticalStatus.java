package com.idevelopstudio.doctorapp.customViews;

public enum CriticalStatus {
    LOW("Low"),
    MODERATE("Moderate"),
    HIGH("High");
    private String value;

    CriticalStatus(String s) {
        this.value = s;
    }

    public String getValue() {
        return value;
    }
}
