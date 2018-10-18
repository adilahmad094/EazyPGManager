package net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses;

public class HeaterDetails {

    public String id, roomNo, brand, model, timeSinceInstallation, inputPower, weight;

    public HeaterDetails(String id, String roomNo, String brand, String model, String timeSinceInstallation, String inputPower, String weight) {
        this.id=id;
        this.roomNo = roomNo;
        this.brand = brand;
        this.model = model;
        this.timeSinceInstallation = timeSinceInstallation;
        this.inputPower = inputPower;
        this.weight = weight;
    }

    public HeaterDetails() {

        roomNo = "";
        brand = "";
        model = "";
        timeSinceInstallation = "";
        inputPower = "";
        weight = "";

    }
}
