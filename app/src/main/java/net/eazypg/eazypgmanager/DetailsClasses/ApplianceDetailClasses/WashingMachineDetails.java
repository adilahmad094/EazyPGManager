package net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses;

public class WashingMachineDetails {

    public String id, roomNo, brand, model, timeSinceInstallation, capacity, inputPower, starRating, typeOfMachine;

    public WashingMachineDetails(String id, String roomNo, String brand, String model, String timeSinceInstallation, String capacity, String inputPower, String starRating, String typeOfMachine) {
        this.id = id;
        this.roomNo = roomNo;
        this.brand = brand;
        this.model = model;
        this.timeSinceInstallation = timeSinceInstallation;
        this.capacity = capacity;
        this.inputPower = inputPower;
        this.starRating = starRating;
        this.typeOfMachine = typeOfMachine;
    }

    public WashingMachineDetails() {

        roomNo = "";
        brand = "";
        model = "";
        timeSinceInstallation = "";
        capacity = "";
        inputPower = "";
        starRating = "";
        typeOfMachine = "";
    }
}
