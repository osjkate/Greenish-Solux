package com.solux.greenish.Alarm.Domain;

public class Alarm {

    // 전체 알림 설정
    private boolean all;

    // 개별 설정
    private boolean hapticMode;
    private boolean preview;
    private boolean allPlantWatering;

    // 기본 생성자
    public Alarm() {
    }

    // 전체 필드를 초기화하는 생성자
    public Alarm(boolean all, boolean hapticMode, boolean preview, boolean allPlantWatering) {
        this.all = all;
        this.hapticMode = hapticMode;
        this.preview = preview;
        this.allPlantWatering = allPlantWatering;
    }

    // Getter 및 Setter 메서드

    public boolean isAll() {
        return all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }

    public boolean isHapticMode() {
        return hapticMode;
    }

    public void setHapticMode(boolean hapticMode) {
        this.hapticMode = hapticMode;
    }

    public boolean isPreview() {
        return preview;
    }

    public void setPreview(boolean preview) {
        this.preview = preview;
    }

    public boolean isAllPlantWatering() {
        return allPlantWatering;
    }

    public void setAllPlantWatering(boolean allPlantWatering) {
        this.allPlantWatering = allPlantWatering;
    }
}