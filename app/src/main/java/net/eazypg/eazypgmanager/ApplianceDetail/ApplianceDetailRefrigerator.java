package net.eazypg.eazypgmanager.ApplianceDetail;

public class ApplianceDetailRefrigerator {

    public String roomNo, brand, model, timeSinceInstallation, capacity, typeOfRefrigerator, starRating;

    public ApplianceDetailRefrigerator(String roomNo, String brand, String model, String timeSinceInstallation, String capacity, String typeOfRefrigerator, String starRating) {
        this.roomNo = roomNo;
        this.brand = brand;
        this.model = model;
        this.timeSinceInstallation = timeSinceInstallation;
        this.capacity = capacity;
        this.typeOfRefrigerator = typeOfRefrigerator;
        this.starRating = starRating;
    }

    public ApplianceDetailRefrigerator() {
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

    public String getTypeOfRefrigerator() {
        return typeOfRefrigerator;
    }

    public void setTypeOfRefrigerator(String typeOfRefrigerator) {
        this.typeOfRefrigerator = typeOfRefrigerator;
    }

    public String getStarRating() {
        return starRating;
    }

    public void setStarRating(String starRating) {
        this.starRating = starRating;
    }
}
