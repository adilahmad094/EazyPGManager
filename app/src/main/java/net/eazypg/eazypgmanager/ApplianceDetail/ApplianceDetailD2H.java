package net.eazypg.eazypgmanager.ApplianceDetail;

public class ApplianceDetailD2H {

    public String roomNo, brand, timeSinceInstallation;

    public String getRoomNo() {
        return roomNo;
    }

    public String getBrand() {
        return brand;
    }

    public String getTimeSinceInstallation() {
        return timeSinceInstallation;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setTimeSinceInstallation(String timeSinceInstallation) {
        this.timeSinceInstallation = timeSinceInstallation;
    }

    public ApplianceDetailD2H(String roomNo, String brand, String timeSinceInstallation) {
        this.roomNo = roomNo;
        this.brand = brand;
        this.timeSinceInstallation = timeSinceInstallation;
    }

    public ApplianceDetailD2H() {
    }
}
