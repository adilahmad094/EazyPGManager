package net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses;

public class IronDetails {

    public String id, roomNo, brand, model, timeSinceInstallation, inputPower;

    public IronDetails(String id, String roomNo, String brand, String model, String timeSinceInstallation, String inputPower) {
        this.id = id;
        this.roomNo = roomNo;
        this.brand = brand;
        this.model = model;
        this.timeSinceInstallation = timeSinceInstallation;
        this.inputPower = inputPower;
    }

    public IronDetails() {

        roomNo = "";
        brand = "";
        model = "";
        timeSinceInstallation = "";
        inputPower = "";
    }
}
