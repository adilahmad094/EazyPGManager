package net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses;

public class MicrowaveDetails {

    public String id, roomNo, brand, model, timeSinceInstallation, capacity, controlType;

    public MicrowaveDetails(String id, String roomNo, String brand, String model, String timeSinceInstallation, String capacity, String controlType) {
        this.id=id;
        this.roomNo = roomNo;
        this.brand = brand;
        this.model = model;
        this.timeSinceInstallation = timeSinceInstallation;
        this.capacity = capacity;
        this.controlType = controlType;
    }

    public MicrowaveDetails() {

        roomNo = "";
        brand = "";
        model = "";
        timeSinceInstallation = "";
        capacity = "";
        controlType = "";
    }
}
