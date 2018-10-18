package net.eazypg.eazypgmanager.ApplianceDetail;

public class ApplianceDetailAC {

    public String roomNo, brand, model, capacity, lastServiceDate, starRating, typeOfRating;

    public ApplianceDetailAC() {

    }

    public ApplianceDetailAC(String roomNo, String brand, String model, String capacity, String lastServiceDate, String starRating, String typeOfRating) {
        this.roomNo = roomNo;
        this.brand = brand;
        this.model = model;
        this.capacity = capacity;
        this.lastServiceDate = lastServiceDate;
        this.starRating = starRating;
        this.typeOfRating = typeOfRating;
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

    public String getCapacity() {
        return capacity;
    }

    public String getLastServiceDate() {
        return lastServiceDate;
    }

    public String getStarRating() {
        return starRating;
    }

    public String getTypeOfRating() {
        return typeOfRating;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public void setLastServiceDate(String lastServiceDate) {
        this.lastServiceDate = lastServiceDate;
    }

    public void setStarRating(String starRating) {
        this.starRating = starRating;
    }

    public void setTypeOfRating(String typeOfRating) {
        this.typeOfRating = typeOfRating;
    }
}
