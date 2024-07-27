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

    // 전체 알림 설정
    public void setAllNotificationsEnabled(boolean enabled) {
        this.allNotificationsEnabled = enabled;

        if (enabled) {
            // 전체 알림이 켜지면 햅틱 모드와 미리보기도 켬
            this.hapticModeEnabled = true;
            this.previewEnabled = true;
        } else {
            // 전체 알림이 꺼지면 햅틱 모드와 미리보기 설정도 꺼짐
            this.hapticModeEnabled = false;
            this.previewEnabled = false;
        }
    }

    // 햅틱 모드 설정
    public void setHapticModeEnabled(boolean enabled) {
        this.hapticModeEnabled = enabled;
        // 햅틱 모드와 미리보기가 모두 켜져 있어야 전체 알림이 켜져야 함
        if (enabled && this.previewEnabled) {
            setAllNotificationsEnabled(true);
        } else if (!enabled || !this.previewEnabled) {
            // 햅틱 모드가 꺼지거나 미리보기가 꺼진 경우 전체 알림을 꺼야 하는지 검사
            if (!this.hapticModeEnabled && !this.previewEnabled) {
                setAllNotificationsEnabled(false);
            }
        }
    }

    // 미리보기 설정
    public void setPreviewEnabled(boolean enabled) {
        this.previewEnabled = enabled;
        // 미리보기가 켜지고 햅틱 모드도 켜져 있어야 전체 알림이 켜져야 함
        if (enabled && this.hapticModeEnabled) {
            setAllNotificationsEnabled(true);
        } else if (!enabled || !this.hapticModeEnabled) {
            // 미리보기가 꺼지거나 햅틱 모드가 꺼진 경우 전체 알림을 꺼야 하는지 검사
            if (!this.hapticModeEnabled && !this.previewEnabled) {
                setAllNotificationsEnabled(false);
            }
        }
    }

    // 전체 물주기 알림 설정
    public void setWateringNotificationsEnabled(boolean enabled) {
        this.wateringNotificationsEnabled = enabled;

        if (enabled) {
            // 전체 물주기 알림이 켜지면 전체 식물 물주기 및 물주기 기록도 켬
            this.overallPlantWateringEnabled = true;
            this.wateringHistoryEnabled = true;
        } else {
            // 전체 물주기 알림이 꺼지면 전체 식물 물주기 및 물주기 기록도 꺼짐
            this.overallPlantWateringEnabled = false;
            this.wateringHistoryEnabled = false;
        }
    }

    // 전체 식물 물주기 알림 설정
    public void setOverallPlantWateringEnabled(boolean enabled) {
        this.overallPlantWateringEnabled = enabled;
        // 전체 식물 물주기 알림이 켜지면 물주기 알림도 켬
        if (enabled && !wateringNotificationsEnabled) {
            setWateringNotificationsEnabled(true);
        } else if (!enabled && !wateringHistoryEnabled) {
            // 전체 식물 물주기 알림이 꺼지고 물주기 기록 알림도 꺼졌을 때 전체 물주기 알림을 꺼야 함
            if (!this.wateringHistoryEnabled) {
                setWateringNotificationsEnabled(false);
            }
        }
    }

    // 물주기 기록 알림 설정
    public void setWateringHistoryEnabled(boolean enabled) {
        this.wateringHistoryEnabled = enabled;
        // 물주기 기록이 켜지면 물주기 알림도 켬
        if (enabled && !wateringNotificationsEnabled) {
            setWateringNotificationsEnabled(true);
        } else if (!enabled && !overallPlantWateringEnabled) {
            // 물주기 기록 알림이 꺼지고 전체 식물 물주기 알림도 꺼졌을 때 전체 물주기 알림을 꺼야 함
            if (!this.overallPlantWateringEnabled) {
                setWateringNotificationsEnabled(false);
            }
        }
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