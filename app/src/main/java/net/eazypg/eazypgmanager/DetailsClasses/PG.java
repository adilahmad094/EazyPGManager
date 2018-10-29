package net.eazypg.eazypgmanager.DetailsClasses;

public class PG {

    public String pgName, ownername, pgContact, landmark, lastEntryTime, maxOccupancy, staffCount, noOfRooms, noOfBathroom, rentDueDate, billDueDate, electricityUnitCost, email, city, pincode, gasUnitCOst, messType, messRate, personalContact, personalEmail, typeOfBills, tenantsPreferred;
    public String emergencyStayRate, securityDeposit, lockingPeriod, addressLine1, addressLine2, state, pgAvailableFor, lastLateCheckIn;

    public boolean electricityIsChecked, wifiIsChecked, gasIsChecked;

    public PG(String PGName, String ownername, String PGContact, String landmark, String lastEntryTime, String maxOccupancy, String staffCount, String noOfRooms, String noOfBathroom, String rentDueDate, String billDueDate, String electricityUnitCost, String email, String city, String pincode, String gasUnitCOst, String messType, String messRate, String personalContact, String personalEmail, String typeOfBills, boolean electricityIsChecked, boolean wifiIsChecked, boolean gasIsChecked,String emergencyStayRate, String tenantsPreferred, String securityDeposit, String lockingPeriod, String addressLine1, String addressLine2, String state, String pgAvailableFor, String lastLateCheckIn ){
        this.pgName = PGName;
        this.ownername = ownername;
        this.pgContact = PGContact;
        this.landmark = landmark;
        this.lastEntryTime = lastEntryTime;
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
        this.securityDeposit =securityDeposit;
        this.lockingPeriod = lockingPeriod;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;

        this.tenantsPreferred = tenantsPreferred;
        this.emergencyStayRate = emergencyStayRate;
        this.state = state;
        this.pgAvailableFor = pgAvailableFor;
        this.lastLateCheckIn = lastLateCheckIn;
    }

    public PG() {
        pgName = "";
        ownername = "";
        pgContact = "";
        landmark = "";
        lastEntryTime = "";
        maxOccupancy = "";
        staffCount = "";
        noOfRooms = "";
        noOfBathroom = "";
        rentDueDate = "";
        billDueDate = "";
        electricityUnitCost = "";

    }
}
