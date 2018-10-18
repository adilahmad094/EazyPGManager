package net.eazypg.eazypgmanager.ApplianceDetail;

public class ApplianceDetailDishwasher {

    public String roomNo, brand, model, timeSinceInstallation, capacity, typeOfDishwasher;

    public ApplianceDetailDishwasher(String roomNo, String brand, String model, String timeSinceInstallation, String capacity, String typeOfDishwasher) {
        this.roomNo = roomNo;
        this.brand = brand;
        this.model = model;
        this.timeSinceInstallation = timeSinceInstallation;
        this.capacity = capacity;
        this.typeOfDishwasher = typeOfDishwasher;
    }

    public ApplianceDetailDishwasher() {
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

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getTypeOfDishwasher() {
        return typeOfDishwasher;
    }

    public void setTypeOfDishwasher(String typeOfDishwasher) {
        this.typeOfDishwasher = typeOfDishwasher;
    }
}
