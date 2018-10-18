package net.eazypg.eazypgmanager.DetailsClasses;

public class RoomApplianceDetails {
    public String id, type, brand, timeSinceInstallation;

    public RoomApplianceDetails() {
    }

    public RoomApplianceDetails(String type, String brand) {
        this.type = type;
        this.brand = brand;
    }

    public RoomApplianceDetails(String type, String brand, String timeSinceInstallation) {
        this.type = type;
        this.brand = brand;
        this.timeSinceInstallation = timeSinceInstallation;
    }

    public RoomApplianceDetails(String id, String type, String brand, String timeSinceInstallation) {
        this.id = id;
        this.type = type;
        this.brand = brand;
        this.timeSinceInstallation = timeSinceInstallation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getTimeSinceInstallation() {
        return timeSinceInstallation;
    }

    public void setTimeSinceInstallation(String timeSinceInstallation) {
        this.timeSinceInstallation = timeSinceInstallation;
    }
}
