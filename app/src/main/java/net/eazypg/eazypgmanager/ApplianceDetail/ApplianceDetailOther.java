package net.eazypg.eazypgmanager.ApplianceDetail;

public class ApplianceDetailOther {

    public String roomNo, applianceName, brand;

    public ApplianceDetailOther(String roomNo, String applianceName, String brand) {
        this.roomNo = roomNo;
        this.applianceName = applianceName;
        this.brand = brand;
    }

    public ApplianceDetailOther() {
    }

    public String getRoomNo() {
        return roomNo;
    }

    public String getApplianceName() {
        return applianceName;
    }

    public String getBrand() {
        return brand;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public void setApplianceName(String applianceName) {
        this.applianceName = applianceName;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
