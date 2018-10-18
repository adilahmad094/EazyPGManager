package net.eazypg.eazypgmanager.DetailsClasses;

public class StaffDetails {

    public String staffId, salary, contact, name, jobDesc, dateOfJoining;

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public String getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(String dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public StaffDetails(String staffId, String salary, String contact, String name, String jobDesc, String dateOfJoining) {
        this.salary = salary;
        this.contact = contact;
        this.name = name;
        this.jobDesc = jobDesc;
        this.dateOfJoining = dateOfJoining;
        this.staffId = staffId;
    }

    public StaffDetails() {
    
    }
}
