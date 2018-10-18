package net.eazypg.eazypgmanager.ApplianceDetail;

public class ApplianceDetailLift {

    public String brand, model, timeSinceInstallation, capacity, doorType;

    public ApplianceDetailLift(String brand, String model, String timeSinceInstallation, String capacity, String doorType) {
        this.brand = brand;
        this.model = model;
        this.timeSinceInstallation = timeSinceInstallation;
        this.capacity = capacity;
        this.doorType = doorType;

    }

    public ApplianceDetailLift() {
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

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getDoorType() {
        return doorType;
    }

    public void setDoorType(String doorType) {
        this.doorType = doorType;
    }
}
