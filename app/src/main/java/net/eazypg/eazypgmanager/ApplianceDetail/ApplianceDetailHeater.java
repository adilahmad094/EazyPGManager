package net.eazypg.eazypgmanager.ApplianceDetail;

public class ApplianceDetailHeater {

    public String roomNo, brand, model, timeSinceInstallation, inputPower, weight;

    public ApplianceDetailHeater(String roomNo, String brand, String model, String timeSinceInstallation, String inputPower, String weight) {
        this.roomNo = roomNo;
        this.brand = brand;
        this.model = model;
        this.timeSinceInstallation = timeSinceInstallation;
        this.inputPower = inputPower;
        this.weight = weight;
    }

    public ApplianceDetailHeater() {
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

    public String getInputPower() {
        return inputPower;
    }

    public void setInputPower(String inputPower) {
        this.inputPower = inputPower;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
