package net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses;

public class LiftDetails {

    public String id, brand, model, timeSinceInstallation, capacity, doorType;

    public LiftDetails(String id, String brand, String model, String timeSinceInstallation, String capacity, String doorType) {

        this.id=id;
        this.brand = brand;
        this.model = model;
        this.timeSinceInstallation = timeSinceInstallation;
        this.capacity = capacity;
        this.doorType = doorType;

    }

    public LiftDetails() {

        brand = "";
        model = "";
        timeSinceInstallation = "";
        capacity = "";
        doorType = "";
    }
}
