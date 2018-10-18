package net.eazypg.eazypgmanager.ApplianceDetail;

public class ApplianceDetailWashingMachine {

    public String roomNo, brand, model, timeSinceInstallation, capacity, inputPower, starRating, typeOfMachine;

    public ApplianceDetailWashingMachine(String roomNo, String brand, String model, String timeSinceInstallation, String capacity, String inputPower, String starRating, String typeOfMachine) {
        this.roomNo = roomNo;
        this.brand = brand;
        this.model = model;
        this.timeSinceInstallation = timeSinceInstallation;
        this.capacity = capacity;
        this.inputPower = inputPower;
        this.starRating = starRating;
        this.typeOfMachine = typeOfMachine;
    }

    public ApplianceDetailWashingMachine() {
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

    public String getInputPower() {
        return inputPower;
    }

    public void setInputPower(String inputPower) {
        this.inputPower = inputPower;
    }

    public String getStarRating() {
        return starRating;
    }

    public void setStarRating(String starRating) {
        this.starRating = starRating;
    }

    public String getTypeOfMachine() {
        return typeOfMachine;
    }

    public void setTypeOfMachine(String typeOfMachine) {
        this.typeOfMachine = typeOfMachine;
    }
}
