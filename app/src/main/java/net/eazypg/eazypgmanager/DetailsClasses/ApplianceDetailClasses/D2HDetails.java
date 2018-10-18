package net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses;

public class D2HDetails {

    public String id, roomNo, brand, timeSinceInstallation;

    public D2HDetails(String id, String roomNo, String brand, String timeSinceInstallation) {
        this.id=id;
        this.roomNo = roomNo;
        this.brand = brand;
        this.timeSinceInstallation = timeSinceInstallation;
    }

    public D2HDetails() {

        roomNo = "";
        brand = "";
        timeSinceInstallation = "";
    }
}
