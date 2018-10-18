package net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses;

public class RODetails {

    public String id, roomNo, brand, model, timeSinceInstallation, capacity;

    public RODetails(String id, String roomNo, String brand, String model, String timeSinceInstallation, String capacity) {
        this.id=id;
        this.roomNo = roomNo;
        this.brand = brand;
        this.model = model;
        this.timeSinceInstallation = timeSinceInstallation;
        this.capacity = capacity;
    }

    public RODetails() {

        roomNo = "";
        brand = "";
        model = "";
        timeSinceInstallation = "";
        capacity = "";
    }
}
