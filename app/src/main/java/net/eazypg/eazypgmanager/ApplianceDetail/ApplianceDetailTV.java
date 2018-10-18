package net.eazypg.eazypgmanager.ApplianceDetail;

public class ApplianceDetailTV {

    public String roomNo, brand, model, timeSinceInstallation, typeOfTV, screenSize, resolution;

    public ApplianceDetailTV(String roomNo, String brand, String model, String timeSinceInstallation, String typeOfTV, String screenSize, String resolution) {
        this.roomNo = roomNo;
        this.brand = brand;
        this.model = model;
        this.timeSinceInstallation = timeSinceInstallation;
        this.typeOfTV = typeOfTV;
        this.screenSize = screenSize;
        this.resolution = resolution;
    }

    public ApplianceDetailTV() {
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

    public String getTypeOfTV() {
        return typeOfTV;
    }

    public void setTypeOfTV(String typeOfTV) {
        this.typeOfTV = typeOfTV;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
}
