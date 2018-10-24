package net.eazypg.eazypgmanager.DetailsClasses;

public class PaymentDetails {

    public String payId, category, description, date, paidTo, amount;

    public PaymentDetails (String payId, String category, String description, String date, String paidTo, String amount) {

        this.payId = payId;
        this.category = category;
        this.description = description;
        this.date = date;
        this.paidTo = paidTo;
        this.amount = amount;

    }

    public PaymentDetails() {
    }
}
