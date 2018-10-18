package net.eazypg.eazypgmanager.DetailsClasses;

import java.util.Date;

public class ComplaintDetails implements Comparable<ComplaintDetails> {

    public String firstLevel, secondLevel, thirdLevel, description, imageName, status, uploadId, availabilityTime, complaintId, tenantId;
    public String name, roomNo;
    public Date date;

    public ComplaintDetails(){

    }

    public ComplaintDetails(String uploadId, String secondLevel, String thirdLevel, String description, String imageName, String status, String availabilityTime, String complaintId, Date date, String tenantId) {
        this.uploadId = uploadId;
        this.secondLevel = secondLevel;
        this.thirdLevel = thirdLevel;
        this.description = description;
        this.imageName = imageName;
        this.status = status;
        this.availabilityTime = availabilityTime;
        this.complaintId = complaintId;
        this.date = date;
        this.tenantId = tenantId;
    }

    public ComplaintDetails(String name, String roomNo, String uploadId, String firstLevel, String secondLevel, String thirdLevel, String description, String imageName, String status, String availabilityTime, String complaintId, Date date, String tenantId) {

        this.name = name;
        this.roomNo = roomNo;
        this.uploadId = uploadId;
        this.firstLevel = firstLevel;
        this.secondLevel = secondLevel;
        this.thirdLevel = thirdLevel;
        this.description = description;
        this.imageName = imageName;
        this.status = status;
        this.availabilityTime = availabilityTime;
        this.complaintId = complaintId;
        this.date = date;
        this.tenantId = tenantId;
    }

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date = date;
    }

    @Override
    public int compareTo(ComplaintDetails o){
        return getDate().compareTo(o.getDate());
    }
}