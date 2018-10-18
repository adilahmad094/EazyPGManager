package net.eazypg.eazypgmanager.ApplianceDetail;

public class ApplianceDetailRouter {

    public String roomNo, brand, model, timeSinceInstallation, noOfAntenna, wirelessSpeed;

    public String getRoomNo() {
        return roomNo;
    }

    public String getBrand() {
        return brand;
    }

    public String getTimeSinceInstallation() {
        return timeSinceInstallation;
    }

    public ApplianceDetailRouter(String roomNo, String brand, String model, String timeSinceInstallation, String noOfAntenna, String wirelessSpeed) {
        this.roomNo = roomNo;
        this.brand = brand;
        this.model = model;
        this.timeSinceInstallation = timeSinceInstallation;
        this.noOfAntenna = noOfAntenna;
        this.wirelessSpeed = wirelessSpeed;
    }

    public ApplianceDetailRouter() {
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
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

    public void setTimeSinceInstallation(String timeSinceInstallation) {
        this.timeSinceInstallation = timeSinceInstallation;
    }

    public String getNoOfAntenna() {
        return noOfAntenna;
    }

    public void setNoOfAntenna(String noOfAntenna) {
        this.noOfAntenna = noOfAntenna;
    }

    public String getWirelessSpeed() {
        return wirelessSpeed;
    }

    public void setWirelessSpeed(String wirelessSpeed) {
        this.wirelessSpeed = wirelessSpeed;
    }
}
