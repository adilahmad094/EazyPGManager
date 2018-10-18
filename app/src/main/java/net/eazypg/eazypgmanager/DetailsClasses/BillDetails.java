package net.eazypg.eazypgmanager.DetailsClasses;

public class BillDetails {
    public String billId, category, amount, datePaid, forDate;
    public boolean paidOrUnpaid;

    public BillDetails() {
    }

    public BillDetails(String billId, String category, String amount, boolean paidOrUnpaid, String datePaid, String forDate) {
        this.billId = billId;
        this.category = category;
        this.amount = amount;
        this.paidOrUnpaid = paidOrUnpaid;
        this.datePaid = datePaid;
        this.forDate = forDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public boolean isPaidOrUnpaid() {
        return paidOrUnpaid;
    }

    public void setPaidOrUnpaid(boolean paidOrUnpaid) {
        this.paidOrUnpaid = paidOrUnpaid;
    }

    public String getDatePaid() {
        return datePaid;
    }

    public void setDatePaid(String datePaid) {
        this.datePaid = datePaid;
    }

    public String getForDate() {
        return forDate;
    }

    public void setForDate(String forDate) {
        this.forDate = forDate;
    }
}
