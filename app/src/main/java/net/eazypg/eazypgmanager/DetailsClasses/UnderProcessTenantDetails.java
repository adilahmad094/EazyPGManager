package net.eazypg.eazypgmanager.DetailsClasses;

public class UnderProcessTenantDetails {

    public String name, phone, room, dateOfJoining, rentAmount;
    boolean flag;

    public UnderProcessTenantDetails(String name, String phone, String room, String dateOfJoining, String rentAmount, boolean flag) {
        this.name = name;
        this.phone = phone;
        this.room = room;
        this.dateOfJoining = dateOfJoining;
        this.rentAmount = rentAmount;
        this.flag = flag;
    }

    public UnderProcessTenantDetails() {

    }

}
