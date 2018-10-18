package net.eazypg.eazypgmanager.ApplianceDetail;

public class ApplianceDetailInduction {

    public String roomNo, brand, model, timeSinceInstallation, inputPower, controlType, noOfCooktops;

    public ApplianceDetailInduction(String roomNo, String brand, String model, String timeSinceInstallation, String inputPower, String controlType, String noOfCooktops) {
        this.roomNo = roomNo;
        this.brand = brand;
        this.model = model;
        this.timeSinceInstallation = timeSinceInstallation;
        this.inputPower = inputPower;
        this.controlType = controlType;
        this.noOfCooktops = noOfCooktops;
    }

    public ApplianceDetailInduction() {
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

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    public String getNoOfCooktops() {
        return noOfCooktops;
    }

    public void setNoOfCooktops(String noOfCooktops) {
        this.noOfCooktops = noOfCooktops;
    }
}
