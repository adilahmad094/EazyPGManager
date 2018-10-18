package net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses;

public class RouterDetails {

    public String id, roomNo, brand, model, timeSinceInstallation, noOfAntenna, wirelessSpeed;

    public RouterDetails(String id, String roomNo, String brand, String model, String timeSinceInstallation, String noOfAntenna, String wirelessSpeed) {
        this.id = id;
        this.roomNo = roomNo;
        this.brand = brand;
        this.model = model;
        this.timeSinceInstallation = timeSinceInstallation;
        this.noOfAntenna = noOfAntenna;
        this.wirelessSpeed = wirelessSpeed;
    }

    public RouterDetails() {
        roomNo = "";
        brand = "";
        model = "";
        timeSinceInstallation = "";
        noOfAntenna = "";
        wirelessSpeed = "";
    }
}
