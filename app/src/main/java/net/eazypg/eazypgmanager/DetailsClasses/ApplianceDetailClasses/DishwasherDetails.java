package net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses;

public class DishwasherDetails {

    public String id, roomNo, brand, model, timeSinceInstallation, capacity, typeOfDishwasher;

    public DishwasherDetails(String id, String roomNo, String brand, String model, String timeSinceInstallation, String capacity, String typeOfDishwasher) {
        this.id=id;
        this.roomNo = roomNo;
        this.brand = brand;
        this.model = model;
        this.timeSinceInstallation = timeSinceInstallation;
        this.capacity = capacity;
        this.typeOfDishwasher = typeOfDishwasher;
    }

    public DishwasherDetails() {

        roomNo = "";
        brand = "";
        model = "";
        timeSinceInstallation = "";
        capacity = "";
        typeOfDishwasher = "";
    }
}
