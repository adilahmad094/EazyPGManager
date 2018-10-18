package net.eazypg.eazypgmanager.Activities;

public class FineDetails {
    String fineId, amount;
    boolean paidOrUnpaid;

    public FineDetails(String fineId, String amount, boolean paidOrUnpaid) {
        this.fineId = fineId;
        this.amount = amount;
        this.paidOrUnpaid = paidOrUnpaid;
    }

    public String getFineId() {
        return fineId;
    }

    public void setFineId(String fineId) {
        this.fineId = fineId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public boolean isPaidOrUnpaid() {
        return paidOrUnpaid;
    }

    public void setPaidOrUnpaid(boolean paidOrUnpaid) {
        this.paidOrUnpaid = paidOrUnpaid;
    }
}
