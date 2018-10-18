package net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses;

public class GeyserDetails {

    public String id, roomNo, brand, model, timeSinceInstallation, capacity, inputPower, starRating;

    public GeyserDetails(String id, String roomNo, String brand, String model, String timeSinceInstallation, String capacity, String inputPower, String starRating) {
        this.id=id;
        this.roomNo = roomNo;
        this.brand = brand;
        this.model = model;
        this.timeSinceInstallation = timeSinceInstallation;
        this.capacity = capacity;
        this.inputPower = inputPower;
        this.starRating = starRating;
    }

    public GeyserDetails() {

        roomNo = "";
        brand = "";
        model = "";
        timeSinceInstallation = "";
        capacity = "";
        inputPower = "";
        starRating = "";
    }
}
