package com.solux.greenish.Alarm.Domain;

public class Alarm {

    // 전체 알림 설정
    private boolean allNotificationsEnabled;

    // 개별 설정: 햅틱 모드 및 미리보기
    private boolean hapticModeEnabled;
    private boolean previewEnabled;

    // 전체 물주기 알림 설정
    private boolean wateringNotificationsEnabled;
    private boolean overallPlantWateringEnabled;
    private boolean wateringHistoryEnabled;

    // 생성자
    public Alarm() {
        this.allNotificationsEnabled = false;
        this.hapticModeEnabled = false;
        this.previewEnabled = false;
        this.wateringNotificationsEnabled = false;
        this.overallPlantWateringEnabled = false;
        this.wateringHistoryEnabled = false;
    }

    // Getter 및 Setter
    public boolean isAllNotificationsEnabled() {
        return allNotificationsEnabled;
    }

    public boolean isHapticModeEnabled() {
        return hapticModeEnabled;
    }

    public boolean isPreviewEnabled() {
        return previewEnabled;
    }

    public boolean isWateringNotificationsEnabled() {
        return wateringNotificationsEnabled;
    }

    public boolean isOverallPlantWateringEnabled() {
        return overallPlantWateringEnabled;
    }

    public boolean isWateringHistoryEnabled() {
        return wateringHistoryEnabled;
    }
}