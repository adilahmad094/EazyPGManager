package net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses;

public class InductionDetails {

    public String id, roomNo, brand, model, timeSinceInstallation, inputPower, controlType, noOfCooktops;

    public InductionDetails(String id, String roomNo, String brand, String model, String timeSinceInstallation, String inputPower, String controlType, String noOfCooktops) {
        this.id=id;
        this.roomNo = roomNo;
        this.brand = brand;
        this.model = model;
        this.timeSinceInstallation = timeSinceInstallation;
        this.inputPower = inputPower;
        this.controlType = controlType;
        this.noOfCooktops = noOfCooktops;
    }

    public InductionDetails() {

        roomNo = "";
        brand = "";
        model = "";
        timeSinceInstallation = "";
        inputPower = "";
        controlType = "";
        noOfCooktops = "";
    }
}
