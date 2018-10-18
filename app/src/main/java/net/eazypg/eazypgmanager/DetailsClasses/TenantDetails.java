package net.eazypg.eazypgmanager.DetailsClasses;

public class TenantDetails {

    public String id, name, phone, room, dateOfJoining, rentAmount, pgId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(String dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public String getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(String rentAmount) {
        this.rentAmount = rentAmount;
    }

    public String getPgId() {
        return pgId;
    }

    public void setPgId(String pgId) {
        this.pgId = pgId;
    }

    public TenantDetails(String name, String phone, String room, String dateOfJoining, String rentAmount, String pgId, String id) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.room = room;
        this.dateOfJoining = dateOfJoining;
        this.rentAmount = rentAmount;
        this.pgId = pgId;
    }

    public TenantDetails(String id, String name, String phone, String dateOfJoining, String rentAmount, String pgId) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.dateOfJoining = dateOfJoining;
        this.rentAmount = rentAmount;
        this.pgId = pgId;
    }

    public TenantDetails() {

    }
}
