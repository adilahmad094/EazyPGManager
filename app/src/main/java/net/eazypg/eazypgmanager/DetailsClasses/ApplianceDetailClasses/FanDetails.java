package net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses;

public class FanDetails {

    public String id, roomNo, brand, model, timeSinceInstallation, noOfBlades;

    public FanDetails(String id, String roomNo, String brand, String model, String timeSinceInstallation, String noOfBlades) {
        this.id=id;
        this.roomNo = roomNo;
        this.brand = brand;
        this.model = model;
        this.timeSinceInstallation = timeSinceInstallation;
        this.noOfBlades = noOfBlades;
    }

    public FanDetails() {

        roomNo = "";
        brand = "";
        model = "";
        timeSinceInstallation = "";
        noOfBlades = "";
    }

    public String getId() {
        return id;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getTimeSinceInstallation() {
        return timeSinceInstallation;
    }

    public String getNoOfBlades() {
        return noOfBlades;
    }
}
