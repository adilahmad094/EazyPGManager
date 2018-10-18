package net.eazypg.eazypgmanager.DetailsClasses;

public class LateCheckInDetails {

    public String lateCheckInId, tenantId, tenantName, tenantPhone, returnTime, reason;

    public LateCheckInDetails() {
    }

    public LateCheckInDetails(String lateCheckInId, String tenantId, String tenantName, String tenantPhone, String returnTime, String reason) {
        this.lateCheckInId = lateCheckInId;
        this.tenantId = tenantId;
        this.tenantName = tenantName;
        this.tenantPhone = tenantPhone;
        this.returnTime = returnTime;
        this.reason = reason;
    }

    public String getLateCheckInId() {
        return lateCheckInId;
    }

    public void setLateCheckInId(String lateCheckInId) {
        this.lateCheckInId = lateCheckInId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantPhone() {
        return tenantPhone;
    }

    public void setTenantPhone(String tenantPhone) {
        this.tenantPhone = tenantPhone;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
