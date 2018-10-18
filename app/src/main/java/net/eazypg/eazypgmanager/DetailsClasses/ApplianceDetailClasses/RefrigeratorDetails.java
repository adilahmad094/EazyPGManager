package net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses;

public class RefrigeratorDetails {

    public String id, roomNo, brand, model, timeSinceInstallation, capacity, typeOfRefrigerator, starRating;

    public RefrigeratorDetails(String id, String roomNo, String brand, String model, String timeSinceInstallation, String capacity, String typeOfRefrigerator, String starRating) {
        this.id = id;
        this.roomNo = roomNo;
        this.brand = brand;
        this.model = model;
        this.timeSinceInstallation = timeSinceInstallation;
        this.capacity = capacity;
        this.typeOfRefrigerator = typeOfRefrigerator;
        this.starRating = starRating;
    }

    public RefrigeratorDetails() {
        roomNo = "";
        brand = "";
        model = "";
        timeSinceInstallation = "";
        capacity = "";
        typeOfRefrigerator = "";
        starRating = "";
    }
}
