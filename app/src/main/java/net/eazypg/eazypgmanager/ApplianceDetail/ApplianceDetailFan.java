package net.eazypg.eazypgmanager.ApplianceDetail;

public class ApplianceDetailFan {

    public String roomNo, brand, model, timeSinceInstallation, noOfBlades;

    public ApplianceDetailFan() {
    }

    public ApplianceDetailFan(String roomNo, String brand, String model, String timeSinceInstallation, String noOfBlades) {
        this.roomNo = roomNo;
        this.brand = brand;
        this.model = model;
        this.timeSinceInstallation = timeSinceInstallation;
        this.noOfBlades = noOfBlades;
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

    public String getNoOfBlades() {
        return noOfBlades;
    }

    public void setNoOfBlades(String noOfBlades) {
        this.noOfBlades = noOfBlades;
    }
}
