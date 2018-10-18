package net.eazypg.eazypgmanager.DetailsClasses;

public class PG {

    public String pgName, bio, location, ownername, pgContact, landmark, lastEntryTime, gender, maxOccupancy, staffCount, noOfRooms, noOfBathroom, rentDueDate, billDueDate, electricityUnitCost, email, city, pincode, gasUnitCOst, messType, messRate, personalContact, personalEmail, typeOfBills;
    public boolean electricityIsChecked, wifiIsChecked, gasIsChecked;

    public PG(String PGName, String bio, String location, String ownername, String PGContact, String landmark, String lastEntryTime, String gender, String maxOccupancy, String staffCount, String noOfRooms, String noOfBathroom, String rentDueDate, String billDueDate, String electricityUnitCost, String email, String city, String pincode, String gasUnitCOst, String messType, String messRate, String personalContact, String personalEmail, String typeOfBills, boolean electricityIsChecked, boolean wifiIsChecked, boolean gasIsChecked) {
        this.pgName = PGName;
        this.bio = bio;
        this.location = location;
        this.ownername = ownername;
        this.pgContact = PGContact;
        this.landmark = landmark;
        this.lastEntryTime = lastEntryTime;
        this.gender = gender;
        this.maxOccupancy = maxOccupancy;
        this.staffCount = staffCount;
        this.noOfRooms = noOfRooms;
        this.noOfBathroom = noOfBathroom;
        this.rentDueDate = rentDueDate;
        this.billDueDate = billDueDate;
        this.electricityUnitCost = electricityUnitCost;
        this.email = email;
        this.city = city;
        this.pincode = pincode;
        this.gasUnitCOst = gasUnitCOst;
        this.messType = messType;
        this.messRate = messRate;
        this.personalContact = personalContact;
        this.personalEmail = personalEmail;
        this.typeOfBills = typeOfBills;
        this.electricityIsChecked = electricityIsChecked;
        this.wifiIsChecked = wifiIsChecked;
        this.gasIsChecked = gasIsChecked;
    }

    public PG() {
        pgName = "";
        bio = "";
        location = "";
        ownername = "";
        pgContact = "";
        landmark = "";
        lastEntryTime = "";
        gender = "";
        maxOccupancy = "";
        staffCount = "";
        noOfRooms = "";
        noOfBathroom = "";
        rentDueDate = "";
        billDueDate = "";
        electricityUnitCost = "";
    }
}
