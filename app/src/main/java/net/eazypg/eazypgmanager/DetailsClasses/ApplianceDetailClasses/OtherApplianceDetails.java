package net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses;

public class OtherApplianceDetails {

    public String id, roomNo, applianceName, brand;

    public OtherApplianceDetails(String id, String roomNo, String applianceName, String brand) {
        this.id=id;
        this.roomNo = roomNo;
        this.applianceName = applianceName;
        this.brand = brand;
    }

    public OtherApplianceDetails() {
        roomNo = "";
        applianceName = "";
        brand = "";
    }
}
