package net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses;

public class TVDetails {

    public String id, roomNo, brand, model, timeSinceInstallation, typeOfTV, screenSize, resolution;

    public TVDetails(String id, String roomNo, String brand, String model, String timeSinceInstallation, String typeOfTV, String screenSize, String resolution) {
        this.id = id;
        this.roomNo = roomNo;
        this.brand = brand;
        this.model = model;
        this.timeSinceInstallation = timeSinceInstallation;
        this.typeOfTV = typeOfTV;
        this.screenSize = screenSize;
        this.resolution = resolution;
    }

    public TVDetails() {

        roomNo = "";
        brand = "";
        model = "";
        timeSinceInstallation = "";
        typeOfTV = "";
        screenSize = "";
        resolution = "";
    }
}
