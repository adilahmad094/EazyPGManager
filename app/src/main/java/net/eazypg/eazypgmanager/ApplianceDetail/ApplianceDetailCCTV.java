package net.eazypg.eazypgmanager.ApplianceDetail;

public class ApplianceDetailCCTV {

    public String roomNo, brand, model, timeSinceInstallation, nightVision, noOfChannels, recordingResolution;

    public ApplianceDetailCCTV(String roomNo, String brand, String model, String timeSinceInstallation, String nightVision, String noOfChannels, String recordingResolution) {
        this.roomNo = roomNo;
        this.brand = brand;
        this.model = model;
        this.timeSinceInstallation = timeSinceInstallation;
        this.nightVision = nightVision;
        this.noOfChannels = noOfChannels;
        this.recordingResolution = recordingResolution;
    }

    public ApplianceDetailCCTV() {
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTimeSinceInstallation() {
        return timeSinceInstallation;
    }

    public void setTimeSinceInstallation(String timeSinceInstallation) {
        this.timeSinceInstallation = timeSinceInstallation;
    }

    public String getNightVision() {
        return nightVision;
    }

    public void setNightVision(String nightVision) {
        this.nightVision = nightVision;
    }

    public String getNoOfChannels() {
        return noOfChannels;
    }

    public void setNoOfChannels(String noOfChannels) {
        this.noOfChannels = noOfChannels;
    }

    public String getRecordingResolution() {
        return recordingResolution;
    }

    public void setRecordingResolution(String recordingResolution) {
        this.recordingResolution = recordingResolution;
    }
}
